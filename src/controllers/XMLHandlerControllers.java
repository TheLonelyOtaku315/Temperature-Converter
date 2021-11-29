/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Scanner;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import model.Convertion;
import model.XMLHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import view.XMLHandlerView;

/**
 *
 * @author Tonny
 */
public class XMLHandlerControllers {

    /**
     *
     * @param convertion2
     */
    private final XMLHandler xml;
    private final XMLHandlerView xmlView;

    public XMLHandlerControllers(XMLHandler xml, XMLHandlerView xmlView) {
        this.xml = xml;
        this.xmlView = xmlView;
    }
    static File xmlFile = new File("history.xml");

    public static void write(Convertion convertion) {
        try {

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            // root element
            Element root = document.createElement("Converstion");
            document.appendChild(root);

            // Date element
            Element firstName = document.createElement("Date");
            String timeStamp = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss").format(Calendar.getInstance().getTime());
            firstName.appendChild(document.createTextNode(timeStamp));
            root.appendChild(firstName);

            // Information element
            Element info1 = document.createElement("Information");
            info1.appendChild(document.createTextNode(convertion.getInformation()));
            root.appendChild(info1);

            // Information enter element
            Element enter1 = document.createElement("InformationEnter");
            enter1.appendChild(document.createTextNode(convertion.getInformationEnter()));
            root.appendChild(enter1);

            // Information given element
            Element given1 = document.createElement("InformationGiven");
            given1.appendChild(document.createTextNode(convertion.getInformationGiven()));
            root.appendChild(given1);

            // read history.xml before chnaging data
            String old = "";
            try (BufferedReader reader = new BufferedReader(new FileReader(new File("history.xml")))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    String replaceAll = line.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "");
                    String replaceAll2 = replaceAll.replace("<data>", "");
                    String replaceAll3 = replaceAll2.replace("</data>", "");
                    old = old + replaceAll3;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            // change history.xml data
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(xmlFile);
            transformer.transform(domSource, streamResult);

            // read history.xml new data
            String update = "";
            try (BufferedReader reader = new BufferedReader(new FileReader(new File("history.xml")))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    String replaceAll = line.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "");
                    String replaceAll3 = replaceAll.replace("<data>", "");
                    String replaceAll4 = replaceAll3.replace("</data>", "");
                    update = update + replaceAll4;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            //Write the xml entirely
            String all = "<data>" + old + update + "</data>";
            try (FileWriter writer = new FileWriter("history.xml");
                    BufferedWriter bw = new BufferedWriter(writer)) {

                bw.write(all);

            } catch (IOException e) {
                System.err.format("IOException: %s%n", e);
            }

            System.out.println("XML File Changed");

        } catch (ParserConfigurationException | TransformerException pce) {
        }
    }

    public static ObservableList read(TableView table, TableColumn date1, TableColumn info1, TableColumn enter1, TableColumn given1, TableColumn<Convertion, Convertion> delete) {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        ObservableList<Convertion> data = FXCollections.observableArrayList();

        try {

            // optional, but recommended
            // process XML securely, avoid attacks like XML External Entities (XXE)
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new File("history.xml"));

            doc.getDocumentElement().normalize();

            System.err.println("Root Element :" + doc.getDocumentElement().getNodeName());
            System.out.println("------");

            // get <Converstion>
            NodeList list = doc.getElementsByTagName("Converstion");

            for (int temp = 0; temp < list.getLength(); temp++) {

                Node node = list.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;

                    // get Converstion attribute
                    String id = element.getAttribute("id");

                    // get text
                    String date = element.getElementsByTagName("Date").item(0).getTextContent();
                    String info = element.getElementsByTagName("Information").item(0).getTextContent();
                    String enter = element.getElementsByTagName("InformationEnter").item(0).getTextContent();
                    String given = element.getElementsByTagName("InformationGiven").item(0).getTextContent();

                    System.out.println("Current Element :" + node.getNodeName());
                    System.out.println("Id : " + id);
                    System.out.println("Date : " + date);
                    System.out.println("Information : " + info);
                    System.out.println("Information Enter : " + enter);
                    System.out.println("Information Given" + given);
                    System.out.println();

                    Convertion convertion = new Convertion(date, info, enter, given);
                    data.add(convertion);

                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
        }
        return data;

    }

}
