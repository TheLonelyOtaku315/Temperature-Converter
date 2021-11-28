/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import static controllers.XMLHandlerControllers.xmlFile;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
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
    private BorderPane history;
    @FXML
    private TableView<?> table;
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

    private static File xmlFile = new File("history.xml");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList list = (XMLHandlerControllers.read(table, given, info, enter, given, delete));
        addIntTable(list, table, date, info, enter, given, delete);

    }

    @FXML
    public void switchToSetting(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/SceneSetting.fxml"));

        String css = history.getStylesheets().toString().replaceAll("[^a-zA-Z0-9/:.]", "");
        root.getStylesheets().clear();
        root.getStylesheets().add(css);

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

    private static void addIntTable(ObservableList data, TableView table, TableColumn date, TableColumn info, TableColumn enter, TableColumn given, TableColumn<Convertion, Convertion> delete) {
        date.setCellValueFactory(
                new PropertyValueFactory<Convertion, String>("date"));

        info.setCellValueFactory(
                new PropertyValueFactory<Convertion, String>("information"));

        enter.setCellValueFactory(
                new PropertyValueFactory<Convertion, String>("informationEnter"));

        given.setCellValueFactory(
                new PropertyValueFactory<Convertion, String>("informationGiven"));

        delete.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        delete.setCellFactory(param -> new TableCell<Convertion, Convertion>() {
            private final Button deleteButton = new Button("Delete");

            @Override
            protected void updateItem(Convertion task, boolean empty) {
                super.updateItem(task, empty);

                if (task == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(deleteButton);
                deleteButton.setOnAction(event -> getTableView().getItems().remove(task)
                );
            }
        });

        table.setItems(data);
    }

    @FXML
    private static void clearHistory(ActionEvent event) {

        try (FileWriter writer = new FileWriter("history.xml");
                BufferedWriter bw = new BufferedWriter(writer)) {

            bw.write("");

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }

        System.out.println("Done creating XML File");

    }
}
