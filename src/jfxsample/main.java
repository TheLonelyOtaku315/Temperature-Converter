/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfxsample;

import java.io.File;
import view.introScene;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

/**
 *
 * @author
 *
 */
public class main extends Application {

    public static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        Button btn = new Button("Start");
        btn.setFont(Font.font("Verdana", 18));
        btn.setOnAction(event -> {
            try {
                btnHandler(stage);
            } catch (IOException ex) {
                Logger.getLogger(introScene.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        introScene intro = new introScene(btn);

        stage.setScene(intro.getScene());
        stage.show();
////        try {
//        Parent root = FXMLLoader.load(getClass().getResource("/view/SceneConverter.fxml"));
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//
////        } catch (IOException e) {
////            System.err.println("Error");
////            
////        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void btnHandler(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/SceneConverter.fxml"));
        Scene scene1 = new Scene(root);
        stage.setScene(scene1);
        stage.show();
    }

    private static Document convertXMLFileToXMLDocument(String filePath) {
        //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        //API to obtain DOM Document instance
        DocumentBuilder builder = null;
        try {
            //Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();

            //Parse the content to Document object
            Document xmlDocument = builder.parse(new File(filePath));

            return xmlDocument;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
