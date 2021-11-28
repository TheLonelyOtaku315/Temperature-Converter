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
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;
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
                    String replaceAll = line.replaceAll("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "");
                    old = old + replaceAll;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(old);
            System.out.println("***************************");

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
                    String replaceAll = line.replaceAll("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "");
                    update = update + replaceAll;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(update);
            System.out.println("***************************");

            //Write the xml entirely
            String all = old + update;
            try (FileWriter writer = new FileWriter("history.xml");
                    BufferedWriter bw = new BufferedWriter(writer)) {

                bw.write(all);

            } catch (IOException e) {
                System.err.format("IOException: %s%n", e);
            }

            System.out.println("Done creating XML File");

        } catch (ParserConfigurationException | TransformerException pce) {
        }
    }

    public String read() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {

            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new File(xml.XMLFilePath));

            System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
            System.out.println("------");

            NodeList list = doc.getElementsByTagName("Converstion");

            for (int temp = 0; temp < list.getLength(); temp++) {

                Node node = list.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;

                    String Date = element.getElementsByTagName("Name").item(0).getTextContent();
                    String Information = element.getElementsByTagName("Bounty").item(0).getTextContent();
                    String InformationEnter = element.getElementsByTagName("Path").item(0).getTextContent();
                    String InformationGiven = element.getElementsByTagName("Path").item(0).getTextContent();

                    String answer = "Current Element :" + node.getNodeName()
                            + "\nDate : " + Date
                            + "\nInformation : " + Information
                            + "\nInformation Enter : " + InformationEnter
                            + "\nInformation Given : " + InformationGiven;
                    return answer;
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
        }
        return null;
    }

    public void updateView() {
        xmlView.print(read());
    }
}
