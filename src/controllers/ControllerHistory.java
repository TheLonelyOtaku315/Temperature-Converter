/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileWriter;
import java.util.Collections;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import model.Convertion;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Tonny
 */
public class ControllerHistory implements Initializable {

    private Stage stage;
    private Scene scene;

    @FXML
    TextField searchtext;
    @FXML
    private BorderPane history;
    @FXML
    private TableView<Convertion> table;
    @FXML
    private TableColumn date;
    @FXML
    private TableColumn info;
    @FXML
    private TableColumn enter;
    @FXML
    private TableColumn given;
    @FXML
    private TableColumn<Convertion, Convertion> delete;

    private static final File xmlFile = new File("history.xml");

    public static ObservableList<Convertion> list = FXCollections.observableArrayList();
    public static ObservableList<Convertion> historyList = FXCollections.observableArrayList();

    public static int historyListSize = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        list = XMLHandlerControllers.read();
        Collections.reverse(list);
        historyList.clear();
//        System.out.println(historyListSize);
        if (historyListSize == 0) {
            addInfoTable(list, table, date, info, enter, given, delete);
            search(list, searchtext, table);
        } else {
            for (int i = 0; i < historyListSize; i++) {
//                System.out.println(list.size() - i);
                historyList.add(list.get(i));
//                System.out.println(list.get(i));

            }
            addInfoTable(historyList, table, date, info, enter, given, delete);
            search(historyList, searchtext, table);
            historyListSize = historyList.size();

        }

    }

    public static void setHistoryListSize(int historyListSize) {
        ControllerHistory.historyListSize = historyListSize;
    }

    @FXML
    public void switchToSetting(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SceneSetting.fxml"));
        Parent root = loader.load();

        ControllerSetting set = (ControllerSetting) loader.getController();
        String css = history.getStylesheets().toString().replaceAll("[^a-zA-Z0-9/:.]", "");

        root.getStylesheets().clear();
        root.getStylesheets().add(css);

        set.exemple.getStylesheets().clear();
        set.exemple.getStylesheets().add(css);

        if (css.toLowerCase().contains("light")) {
            css = "Light Mode";
        } else if (css.toLowerCase().contains("dark")) {
            css = "Dark Mode";
        } else {
            css = "";
        }
        set.modeSwitch.setValue(css);

        stage = (Stage) history.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void switchToConverter(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/SceneConverter.fxml"));

        String css = history.getStylesheets().toString().replaceAll("[^a-zA-Z0-9/:.]", "");
        root.getStylesheets().clear();
        root.getStylesheets().add(css);

        stage = (Stage) history.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToHelp(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/SceneHelp.fxml"));
        String css = history.getStylesheets().toString().replaceAll("[^a-zA-Z0-9/:.]", "");
        root.getStylesheets().clear();
        root.getStylesheets().add(css);

        stage = (Stage) history.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void addInfoTable(ObservableList data, TableView table, TableColumn date, TableColumn info, TableColumn enter, TableColumn given, TableColumn<Convertion, Convertion> delete) {
        date.setCellValueFactory(
                new PropertyValueFactory<Convertion, String>("date"));

        info.setCellValueFactory(
                new PropertyValueFactory<Convertion, String>("information"));

        enter.setCellValueFactory(
                new PropertyValueFactory<Convertion, String>("informationEnter"));
        enter.setCellFactory(TooltippedTableCell.forTableColumn());

        given.setCellValueFactory(
                new PropertyValueFactory<Convertion, String>("informationGiven"));
        given.setCellFactory(TooltippedTableCell.forTableColumn());

        delete.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        delete.setCellFactory(param -> {
            return new TableCell<Convertion, Convertion>() {
                private final Button deleteButton = new Button("Delete");

                @Override
                protected void updateItem(Convertion conv, boolean empty) {
                    super.updateItem(conv, empty);

                    if (conv == null) {
                        setGraphic(null);
                        return;
                    }

                    setGraphic(deleteButton);
                    deleteButton.setOnAction(event -> {
                        data.remove(conv);
                        try {
                            deleteBtnHandler(conv);
                        } catch (ParserConfigurationException | SAXException | IOException | TransformerException ex) {
                            Logger.getLogger(ControllerHistory.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                }
            };
        });

        table.setItems(data);
    }

    private void deleteBtnHandler(Convertion conv) throws ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        Document doc = db.parse(xmlFile);
        NodeList nodes = doc.getElementsByTagName("Converstion");

        for (int i = 0; i < nodes.getLength(); i++) {
            Element person = (Element) nodes.item(i);
            Element name = (Element) person.getElementsByTagName("Date").item(0);
            String pName = name.getTextContent();
            if (pName.equals(conv.getDate())) {
                person.getParentNode().removeChild(person);
            }
        }
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        DOMSource domSource = new DOMSource(doc);
        StreamResult streamResult = new StreamResult(xmlFile);
        transformer.transform(domSource, streamResult);
    }

    @FXML
    private void clearHistory(ActionEvent event) {
        list.clear();
        historyList.clear();

        try (FileWriter writer = new FileWriter("history.xml");
                BufferedWriter bw = new BufferedWriter(writer)) {

            bw.write("");

        } catch (IOException e) {
//            System.err.format("IOException: %s%n", e);
        }

//        System.out.println("XML File Changed");
    }

    private static void search(ObservableList list, TextField searchtext, TableView table) {

        FilteredList<Convertion> filteredData = new FilteredList<>(list, b -> true);

        searchtext.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredData.setPredicate((Convertion employee) -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(employee.getDate()).contains(lowerCaseFilter)) {
                    return true;
                } else if (-1 != employee.getInformation().toLowerCase().indexOf(lowerCaseFilter)) {
                    return true;
                } else if (employee.getInformationGiven().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else {
                    return employee.getInformationEnter().toLowerCase().contains(lowerCaseFilter);
                }
            });
        });

        SortedList<Convertion> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(table.comparatorProperty());

        table.setItems(sortedData);
    }

}
