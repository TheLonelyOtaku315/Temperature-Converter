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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import jfxsample.darkLightMode;
import jfxsample.darkLightMode;
import jfxsample.darkLightMode;
import jfxsample.darkLightMode;

/**
 *
 * @author Tonny
 */
public class ControllerSetting implements Initializable {

    private Stage stage;
    private Scene scene;
     static String decimalSet;
    static String illustrationSet;

    @FXML
    private BorderPane setting;
    @FXML
    private ImageView img;
    @FXML
    public BorderPane exemple;
    @FXML
    private ImageView img1;
    @FXML
    private ComboBox<String> modeSwitch;
    @FXML
    private ComboBox<String> decimalSwitch;
    @FXML
    private ComboBox<String> illustrationSwitch;
    @FXML
    private ComboBox<String> languageSwitch;
    @FXML
    private Label languageSave;
    @FXML
    private Label modeSave;
    @FXML
    private Label decimalSave;
    @FXML
    private Label illustrationSave;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        FadeTransition fader1 = new FadeTransition(Duration.seconds(0.1), languageSave);
        fader1.setToValue(0);

        FadeTransition fader2 = new FadeTransition(Duration.seconds(0.1), modeSave);
        fader2.setToValue(0);

        FadeTransition fader3 = new FadeTransition(Duration.seconds(0.1), decimalSave);
        fader3.setToValue(0);

        FadeTransition fader4 = new FadeTransition(Duration.seconds(0.1), illustrationSave);
        fader4.setToValue(0);

        SequentialTransition blinkThenFade = new SequentialTransition(
                fader1,
                fader2,
                fader3,
                fader4
        );

        blinkThenFade.play();

        modeSwitch.getItems().removeAll(modeSwitch.getItems());
        modeSwitch.getItems().addAll("Light Mode", "Dark Mode");

        decimalSwitch.getItems().removeAll(decimalSwitch.getItems());
        decimalSwitch.getItems().addAll("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");

        illustrationSwitch.getItems().removeAll(illustrationSwitch.getItems());
        illustrationSwitch.getItems().addAll("Vertical Graph", "Horizontal Graph");

        languageSwitch.getItems().removeAll(languageSwitch.getItems());
        languageSwitch.getItems().addAll("English", "French"/*, "Spanish", "Mandarin"*/);
    }

    @FXML
    private void previewChangeMode(ActionEvent event) {
        changeMode(exemple, img1, "_1");

    }

    @FXML
    private void AllChangeMode(ActionEvent event) {
        changeMode(exemple, img1, "_1");
        changeMode(setting, img, "");
        FadeTransition fader1 = createFader1(modeSave);
        FadeTransition fader2 = createFader2(modeSave);

        SequentialTransition blinkThenFade = new SequentialTransition(
                fader1,
                fader2
        );

        blinkThenFade.play();

    }

    private void changeMode(BorderPane parent, ImageView img, String number) {
        darkLightMode dlm = new darkLightMode();
        if (!modeSwitch.getValue().equals("Light Mode")) {
            dlm.setLightMode(parent, img, number);
        } else if (!modeSwitch.getValue().equals("Dark Mode")) {
            dlm.setDarkMode(parent, img, number);
        }
    }

    @FXML
    private void switchToHistory(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/SceneHistory.fxml"));

        String css = setting.getStylesheets().toString().replaceAll("[^a-zA-Z0-9/:.]", "");
        root.getStylesheets().clear();
        root.getStylesheets().add(css);

        stage = (Stage) setting.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void switchToConverter(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/SceneConverter.fxml"));

        String css = setting.getStylesheets().toString().replaceAll("[^a-zA-Z0-9/:.]", "");
        root.getStylesheets().clear();
        root.getStylesheets().add(css);

        stage = (Stage) setting.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void getDecimalContraint() {
        System.out.println(decimalSwitch.getValue());

        if (decimalSwitch.getValue() != null) {
            decimalSet = switch (decimalSwitch.getValue()) {
                case "0" ->
                    "#";
                case "1" ->
                    "#.0";
                case "2" ->
                    "#.00";
                case "3" ->
                    "#.000";
                case "4" ->
                    "#.0000";
                case "5" ->
                    "#.00000";
                case "6" ->
                    "#.000000";
                case "7" ->
                    "#.0000000";
                case "8" ->
                    "#.00000000";
                case "9" ->
                    "#.000000000";
                default ->
                    "#.000000000000000000";
            };

            FadeTransition fader1 = createFader1(decimalSave);
            FadeTransition fader2 = createFader2(decimalSave);

            SequentialTransition blinkThenFade = new SequentialTransition(
                    fader1,
                    fader2
            );

            blinkThenFade.play();
        }
    }
    @FXML
    public void getGraphIllustration() {
        System.out.println(illustrationSwitch.getValue());

        if (illustrationSwitch.getValue() != null) {
            illustrationSet = switch (illustrationSwitch.getValue()) {
                case "Vertical Graph" ->
                    "Vertical";
                case "Horizontal Graph" ->
                    "Horizontal";
                default ->
                    "Vertical";
            };

            FadeTransition fader1 = createFader1(illustrationSave);
            FadeTransition fader2 = createFader2(illustrationSave);

            SequentialTransition blinkThenFade = new SequentialTransition(
                    fader1,
                    fader2
            );

            blinkThenFade.play();
        }
    }

    private FadeTransition createFader1(Node node) {
        FadeTransition fade = new FadeTransition(Duration.seconds(1), node);
        fade.setFromValue(0);
        fade.setToValue(1);

        return fade;
    }

    private FadeTransition createFader2(Node node) {
        FadeTransition fade = new FadeTransition(Duration.seconds(1), node);
        fade.setFromValue(1);
        fade.setToValue(0);

        return fade;
    }

}
