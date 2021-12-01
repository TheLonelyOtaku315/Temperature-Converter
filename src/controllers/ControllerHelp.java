/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Tonny
 */
public class ControllerHelp implements Initializable {

    private Scene scene;

    private Stage stage;

    private int nextBtnClick;

    private int previousBtnClick;
    @FXML
    private BorderPane help;
    @FXML
    private ImageView img;

    @FXML
    public void switchToSetting() throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/SceneSetting.fxml"));

        String css = help.getStylesheets().toString().replaceAll("[^a-zA-Z0-9/:.]", "");
        root.getStylesheets().clear();
        root.getStylesheets().add(css);

        stage = (Stage) help.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    public void switchToConverter() throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/SceneConverter.fxml"));

        String css = help.getStylesheets().toString().replaceAll("[^a-zA-Z0-9/:.]", "");
        root.getStylesheets().clear();
        root.getStylesheets().add(css);

        stage = (Stage) help.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToHistory() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/SceneHistory.fxml"));

        String css = help.getStylesheets().toString().replaceAll("[^a-zA-Z0-9/:.]", "");
        root.getStylesheets().clear();
        root.getStylesheets().add(css);

        stage = (Stage) help.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void nextBtnHandler() {
        FadeTransition fader1 = createFader2(img);
        fader1.play();
        
        String url = img.getImage().getUrl();
        String imgID = url.replaceAll("[^0-9]", "");

        int imgNewID = Integer.valueOf(imgID) + 1;

        Image image = new Image("img/" + imgNewID + ".png");
        img.setImage(image);

        FadeTransition fader2 = createFader1(img);
        fader2.play();
    }

    @FXML
    private void previousBtnHandler() {
        FadeTransition fader1 = createFader2(img);
        fader1.play();
        
        String url = img.getImage().getUrl();
        String imgID = url.replaceAll("[^0-9]", "");

        int imgNewID = Integer.valueOf(imgID) - 1;

        Image image = new Image("img/" + imgNewID + ".png");
        img.setImage(image);

        FadeTransition fader2 = createFader1(img);
        fader2.play();
    }

    private FadeTransition createFader1(Node node) {
        FadeTransition fade = new FadeTransition(Duration.seconds(5), node);
        fade.setFromValue(0);
        fade.setToValue(1);

        return fade;
    }

    private FadeTransition createFader2(Node node) {
        FadeTransition fade = new FadeTransition(Duration.seconds(5), node);
        fade.setFromValue(1);
        fade.setToValue(0);

        return fade;
    }
}
