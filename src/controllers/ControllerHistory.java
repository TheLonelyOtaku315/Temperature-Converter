/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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
    private TableColumn delete;

    private static File xmlFile = new File("history.xml");
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println(XMLHandlerControllers.read());
        
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

}
