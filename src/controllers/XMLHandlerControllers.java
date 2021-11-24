/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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

    private final XMLHandler xml;
    private final XMLHandlerView xmlView;

    public XMLHandlerControllers(XMLHandler xml, XMLHandlerView xmlView) {
        this.xml = xml;
        this.xmlView = xmlView;
    }

    public void write() {
        try {

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

//            // root element
//            Element root = document.createElement("Account");
//            document.appendChild(root);
//
//            // firstname element
//            Element firstName = document.createElement("Name");
//            firstName.appendChild(document.createTextNode(acc.getName()));
//            root.appendChild(firstName);
//
//            // Bounty element
//            Element Bounty = document.createElement("Bounty");
//            Bounty.appendChild(document.createTextNode(acc.getAmount().toString()));
//            root.appendChild(Bounty);
//
//            // path element
//            Element path = document.createElement("Path");
//            path.appendChild(document.createTextNode(xml.getXMLFilePath()));
//            root.appendChild(path);

            // create the xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xml.getXMLFilePath()));

            transformer.transform(domSource, streamResult);

            String answer = "XML File created:";
            System.out.println(answer);

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

            NodeList list = doc.getElementsByTagName("Account");

            for (int temp = 0; temp < list.getLength(); temp++) {

                Node node = list.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;

                    String Name = element.getElementsByTagName("Name").item(0).getTextContent();
                    String Bounty = element.getElementsByTagName("Bounty").item(0).getTextContent();
                    String Path = element.getElementsByTagName("Path").item(0).getTextContent();

                    String answer = "Current Element :" + node.getNodeName()
                            + "\nName : " + Name
                            + "\nBounty : " + Bounty
                            + "\nPath : " + Path;
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
