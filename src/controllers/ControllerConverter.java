/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import static jfxsample.converter.CelsiusToFahrenheit;
import static jfxsample.converter.CelsiusToKelvin;
import static jfxsample.converter.FahrenheitToCelsius;
import static jfxsample.converter.FahrenheitToKelvin;
import static jfxsample.converter.KelvinToCelsius;
import static jfxsample.converter.KelvinToFahrenheit;
import model.Convertion;

/**
 * FXML Controller class
 *
 * @author Zeiad
 */
public class ControllerConverter implements Initializable {

    private Stage stage;
    private Scene scene;
    private static String decimalRestriction;
    private static String illustrationRestriction;

    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private BorderPane converter;
    @FXML
    private TextField C;
    @FXML
    private TextField F;
    @FXML
    private TextField K;

//    private BarChart temperatureChart;
    private static ObservableList<XYChart.Series<String, Number>> dataV = FXCollections.<XYChart.Series<String, Number>>observableArrayList();
    private static ObservableList<XYChart.Series<Number, String>> dataH = FXCollections.<XYChart.Series<Number, String>>observableArrayList();
    @FXML
    private Label errorMessage;
    @FXML
    private Label temperarureType;
    @FXML
    private VBox illustration;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        decimalRestriction = ControllerSetting.decimalSet;

        FadeTransition fader = new FadeTransition(Duration.seconds(0.1), errorMessage);
        fader.setToValue(0);
        fader.play();

        comboBoxOption();

        C.setOnMouseClicked(event -> clear(C, F, K));
        F.setOnMouseClicked(event -> clear(C, F, K));
        K.setOnMouseClicked(event -> clear(C, F, K));

        illustrationRestriction = ControllerSetting.illustrationSet;
        if ("Horizontal".equals(illustrationRestriction)) {
            CategoryAxis Y = new CategoryAxis();
            NumberAxis X = new NumberAxis();
            BarChart< Number, String> temperatureChart;
            temperatureChart = new BarChart<>(X, Y);
            temperatureChart.setData(dataH);
            System.out.println("H work");
            illustration.getChildren().add(temperatureChart);
        } else {
            CategoryAxis X = new CategoryAxis();
            NumberAxis Y = new NumberAxis();
            BarChart<String, Number> temperatureChart;
            temperatureChart = new BarChart<>(X, Y);
            temperatureChart.setData(dataV);
            System.out.println("V work");
            illustration.getChildren().add(temperatureChart);
        }
    }

    @FXML
    public void switchToSetting(ActionEvent event) throws IOException {
        dataV.clear();
        dataH.clear();

        Parent root = FXMLLoader.load(getClass().getResource("/view/SceneSetting.fxml"));
        String css = converter.getStylesheets().toString().replaceAll("[^a-zA-Z0-9/:.]", "");
        root.getStylesheets().clear();
        root.getStylesheets().add(css);

        stage = (Stage) converter.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToHistory(ActionEvent event) throws IOException {
        dataV.clear();
        dataH.clear();

        Parent root = FXMLLoader.load(getClass().getResource("/view/SceneHistory.fxml"));
        String css = converter.getStylesheets().toString().replaceAll("[^a-zA-Z0-9/:.]", "");
        root.getStylesheets().clear();
        root.getStylesheets().add(css);

        stage = (Stage) converter.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private static void switchBetweenConverter(TextField C, TextField F, TextField K, Label errorMessage, Label temperarureType) {
        String info = "User Temperature";
        temperarureType.setText(info);

        if (!C.getText().equals("")) {
            dataV.clear();
            dataH.clear();

            decimalRestriction = ControllerSetting.decimalSet;

            if (decimalRestriction != null) {
                DecimalFormat df = new DecimalFormat(decimalRestriction);

                String FTemperature = df.format(CelsiusToFahrenheit(Double.parseDouble(C.getText())));
                String KTemperature = df.format(CelsiusToKelvin(Double.parseDouble(C.getText())));

                F.setText(FTemperature);
                K.setText(KTemperature);
                if (0 > Double.valueOf(KTemperature)) {
                    temperarureType.setText("Error");
                    clear(C, F, K);
                    FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.1), errorMessage);
                    fadeTransition.setFromValue(1.0);
                    fadeTransition.setToValue(0.0);
                    fadeTransition.setCycleCount(25);
                    fadeTransition.play();
                }
            } else {

                String FTemperature = Double.toString(CelsiusToFahrenheit(Double.parseDouble(C.getText())));
                String KTemperature = Double.toString(CelsiusToKelvin(Double.parseDouble(C.getText())));

                F.setText(FTemperature);
                K.setText(KTemperature);
                if (0 > Double.valueOf(KTemperature)) {
                    temperarureType.setText("Error");
                    clear(C, F, K);
                    FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.1), errorMessage);
                    fadeTransition.setFromValue(1.0);
                    fadeTransition.setToValue(0.0);
                    fadeTransition.setCycleCount(25);
                    fadeTransition.play();
                }
            }
            Convertion convertion = new Convertion(info, " °C: " + C.getText(), " °F: " + F.getText() + "\n  K: " + K.getText());
            XMLHandlerControllers.write(convertion);
            addData(C, F, K);

        } else if (!F.getText().equals("")) {
            dataV.clear();
            dataH.clear();

            decimalRestriction = ControllerSetting.decimalSet;

            if (decimalRestriction != null) {
                DecimalFormat df = new DecimalFormat(decimalRestriction);

                String CTemperature = df.format(FahrenheitToCelsius(Double.parseDouble(F.getText())));
                String KTemperature = df.format(FahrenheitToKelvin(Double.parseDouble(F.getText())));

                C.setText(CTemperature);
                K.setText(KTemperature);
                if (0 > Double.valueOf(KTemperature)) {
                    temperarureType.setText("Error");
                    clear(C, F, K);
                    FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.1), errorMessage);
                    fadeTransition.setFromValue(1.0);
                    fadeTransition.setToValue(0.0);
                    fadeTransition.setCycleCount(25);
                    fadeTransition.play();
                }
            } else {
                String CTemperature = Double.toString(FahrenheitToCelsius(Double.parseDouble(F.getText())));
                String KTemperature = Double.toString(FahrenheitToKelvin(Double.parseDouble(F.getText())));

                C.setText(CTemperature);
                K.setText(KTemperature);
                if (0 > Double.valueOf(KTemperature)) {
                    temperarureType.setText("Error");
                    clear(C, F, K);
                    FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.1), errorMessage);
                    fadeTransition.setFromValue(1.0);
                    fadeTransition.setToValue(0.0);
                    fadeTransition.setCycleCount(25);
                    fadeTransition.play();
                }

            }
            Convertion convertion = new Convertion(info, " °F: " + F.getText(), " °C: " + C.getText() + "\n  K: " + K.getText());
            XMLHandlerControllers.write(convertion);

            addData(C, F, K);

        } else if (!K.getText().equals("")) {
            dataV.clear();
            dataH.clear();

            decimalRestriction = ControllerSetting.decimalSet;

            if (decimalRestriction != null) {
                DecimalFormat df = new DecimalFormat(decimalRestriction);

                String CTemperature = df.format(KelvinToCelsius(Double.parseDouble(K.getText())));
                String FTemperature = df.format(KelvinToFahrenheit(Double.parseDouble(K.getText())));

                F.setText(FTemperature);
                C.setText(CTemperature);
                if (0 > Double.valueOf(K.getText())) {
                    temperarureType.setText("Error");
                    clear(C, F, K);
                    FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.1), errorMessage);
                    fadeTransition.setFromValue(1.0);
                    fadeTransition.setToValue(0.0);
                    fadeTransition.setCycleCount(25);
                    fadeTransition.play();
                }
            } else {

                String CTemperature = Double.toString(KelvinToCelsius(Double.parseDouble(K.getText())));
                String FTemperature = Double.toString(KelvinToFahrenheit(Double.parseDouble(K.getText())));

                F.setText(FTemperature);
                C.setText(CTemperature);
                if (0 > Double.valueOf(K.getText())) {
                    temperarureType.setText("Error");
                    clear(C, F, K);
                    FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.1), errorMessage);
                    fadeTransition.setFromValue(1.0);
                    fadeTransition.setToValue(0.0);
                    fadeTransition.setCycleCount(25);
                    fadeTransition.play();
                }

            }
            Convertion convertion = new Convertion(info, "  K: " + K.getText(), " °C: " + C.getText() + "\n °F: " + F.getText());
            XMLHandlerControllers.write(convertion);
            addData(C, F, K);

        }
    }

    @FXML
    private void switchBetweenConverter(ActionEvent event) {
        switchBetweenConverter(C, F, K, errorMessage, temperarureType);
    }

    private void comboBoxOption() {
        ArrayList<String> observableArrayList = new ArrayList<>();
        observableArrayList.add("Boiling Point of Water");
        observableArrayList.add("Melting Point of Ice");
        observableArrayList.add("Absolute Zero");
        observableArrayList.add("Room Temperature");
        observableArrayList.add("Body Temperature");

        comboBox.getItems().removeAll(comboBox.getItems());
        comboBox.getItems().addAll(observableArrayList);

        comboBox.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> selected, String oldBox, String newBox) -> {
            if (newBox != null) {
                switch (newBox) {
                    case "Boiling Point of Water":
                        String info = "Boiling Point of Water";
                        temperarureType.setText(info);
                        dataV.clear();
                        dataH.clear();
                        C.setText("100");

                        if (decimalRestriction != null) {

                            DecimalFormat df = new DecimalFormat(decimalRestriction);

                            String CTemperature = df.format(Double.parseDouble(C.getText()));
                            String FTemperature = df.format(CelsiusToFahrenheit(Double.parseDouble(C.getText())));
                            String KTemperature = df.format(CelsiusToKelvin(Double.parseDouble(C.getText())));

                            C.setText(CTemperature);
                            F.setText(FTemperature);
                            K.setText(KTemperature);

                        } else {
                            K.setText("373.15");
                            F.setText("212");

                        }
                        Convertion convertion = new Convertion(info, "", " °C: " + C.getText() + "\n °F: " + F.getText() + "\n  K: " + K.getText());
                        XMLHandlerControllers.write(convertion);
                        addData(C, F, K);
                        comboBox.setValue("...");

                        break;
                    case "Melting Point of Ice":
                        String info1 = "Boiling Point of Water";
                        temperarureType.setText(info1);
                        dataV.clear();
                        dataH.clear();
                        C.setText("0");

                        if (decimalRestriction != null) {

                            DecimalFormat df = new DecimalFormat(decimalRestriction);

                            String CTemperature = df.format(Double.parseDouble(C.getText()));
                            String FTemperature = df.format(CelsiusToFahrenheit(Double.parseDouble(C.getText())));
                            String KTemperature = df.format(CelsiusToKelvin(Double.parseDouble(C.getText())));

                            C.setText(CTemperature);
                            F.setText(FTemperature);
                            K.setText(KTemperature);

                        } else {
                            K.setText("273.15");
                            F.setText("32");

                        }
                        Convertion convertion1 = new Convertion(info1, "", " °C: " + C.getText() + "\n °F: " + F.getText() + "\n  K: " + K.getText());
                        XMLHandlerControllers.write(convertion1);
                        addData(C, F, K);
                        comboBox.setValue("...");

                        break;
                    case "Absolute Zero":
                        String info2 = "Boiling Point of Water";
                        temperarureType.setText(info2);
                        dataV.clear();
                        dataH.clear();
                        C.setText("-273.15");

                        if (decimalRestriction != null) {

                            DecimalFormat df = new DecimalFormat(decimalRestriction);

                            String CTemperature = df.format(Double.parseDouble(C.getText()));
                            String FTemperature = df.format(CelsiusToFahrenheit(Double.parseDouble(C.getText())));
                            String KTemperature = df.format(CelsiusToKelvin(Double.parseDouble(C.getText())));

                            C.setText(CTemperature);
                            F.setText(FTemperature);
                            K.setText(KTemperature);

                        } else {
                            K.setText("0");
                            F.setText("-459.67");

                        }
                        Convertion convertion2 = new Convertion(info2, "", " °C: " + C.getText() + "\n °F: " + F.getText() + "\n  K: " + K.getText());
                        XMLHandlerControllers.write(convertion2);
                        addData(C, F, K);
                        comboBox.setValue("...");

                        break;
                    case "Room Temperature":
                        String info3 = "Boiling Point of Water";
                        temperarureType.setText(info3);
                        dataV.clear();
                        dataH.clear();
                        C.setText("22");

                        if (decimalRestriction != null) {

                            DecimalFormat df = new DecimalFormat(decimalRestriction);

                            String CTemperature = df.format(Double.parseDouble(C.getText()));
                            String FTemperature = df.format(CelsiusToFahrenheit(Double.parseDouble(C.getText())));
                            String KTemperature = df.format(CelsiusToKelvin(Double.parseDouble(C.getText())));

                            C.setText(CTemperature);
                            F.setText(FTemperature);
                            K.setText(KTemperature);

                        } else {
                            K.setText("295.15");
                            F.setText("71.6");

                        }
                        Convertion convertion3 = new Convertion(info3, "", " °C: " + C.getText() + "\n °F: " + F.getText() + "\n  K: " + K.getText());
                        XMLHandlerControllers.write(convertion3);
                        addData(C, F, K);
                        comboBox.setValue("...");
                        break;
                    case "Body Temperature":
                        String info4 = "Boiling Point of Water";
                        temperarureType.setText(info4);
                        dataV.clear();
                        dataH.clear();
                        C.setText("37");

                        if (decimalRestriction != null) {

                            DecimalFormat df = new DecimalFormat(decimalRestriction);

                            String CTemperature = df.format(Double.parseDouble(C.getText()));
                            String FTemperature = df.format(CelsiusToFahrenheit(Double.parseDouble(C.getText())));
                            String KTemperature = df.format(CelsiusToKelvin(Double.parseDouble(C.getText())));

                            C.setText(CTemperature);
                            F.setText(FTemperature);
                            K.setText(KTemperature);

                        } else {
                            K.setText("295.15");
                            F.setText("71.6");

                        }
                        Convertion convertion4 = new Convertion(info4, "", " °C: " + C.getText() + "\n °F: " + F.getText() + "\n  K: " + K.getText());
                        XMLHandlerControllers.write(convertion4);
                        addData(C, F, K);
                        comboBox.setValue("...");
                        break;
                }
            }
        });

        comboBox.setOnMouseClicked(event -> clear(C, F, K));

    }

    @FXML
    private void clear(ActionEvent event) {
        dataV.clear();
        dataH.clear();

        C.setText("");
        F.setText("");
        K.setText("");
    }

    private static void clear(TextField C, TextField F, TextField K) {
        dataV.clear();
        dataH.clear();

        C.setText("");
        F.setText("");
        K.setText("");
    }

    private static void addData(TextField C, TextField F, TextField K) {
        dataV.clear();
        dataH.clear();
        illustrationRestriction = ControllerSetting.illustrationSet;
        XYChart.Series<String, Number> VTemperatureC = new XYChart.Series<>();
        VTemperatureC.setName("°C");
        VTemperatureC.getData().add(new XYChart.Data<>("", Double.parseDouble(C.getText())));

        XYChart.Series<String, Number> VTemperatureF = new XYChart.Series<>();
        VTemperatureF.setName("°F");
        VTemperatureF.getData().add(new XYChart.Data<>("", Double.parseDouble(F.getText())));

        XYChart.Series<String, Number> VTemperatureK = new XYChart.Series<>();
        VTemperatureK.setName(" K");
        VTemperatureK.getData().add(new XYChart.Data<>("", Double.parseDouble(K.getText())));

        dataV.addAll(VTemperatureC, VTemperatureF, VTemperatureK);

        XYChart.Series< Number, String> HTemperatureC = new XYChart.Series<>();
        HTemperatureC.setName("°C");
        HTemperatureC.getData().add(new XYChart.Data<>(Double.parseDouble(C.getText()), ""));

        XYChart.Series<Number, String> HTemperatureF = new XYChart.Series<>();
        HTemperatureF.setName("°F");
        HTemperatureF.getData().add(new XYChart.Data<>(Double.parseDouble(F.getText()), ""));

        XYChart.Series< Number, String> HTemperatureK = new XYChart.Series<>();
        HTemperatureK.setName(" K");
        HTemperatureK.getData().add(new XYChart.Data<>(Double.parseDouble(K.getText()), ""));

        dataH.addAll(HTemperatureC, HTemperatureF, HTemperatureK);

    }
}
