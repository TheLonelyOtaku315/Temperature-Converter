/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfxsample;

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
//import view.SceneHistory.fxml;

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
//        stage.setFullScreen(true);
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
}
