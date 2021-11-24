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
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import static jfxsample.converter.CelsiusToFahrenheit;
import static jfxsample.converter.CelsiusToKelvin;
import static jfxsample.converter.FahrenheitToCelsius;
import static jfxsample.converter.FahrenheitToKelvin;
import static jfxsample.converter.KelvinToCelsius;
import static jfxsample.converter.KelvinToFahrenheit;

/**
 * FXML Controller class
 *
 * @author Zeiad
 */
public class ControllerConverter implements Initializable {

    private Stage stage;
    private Scene scene;
    private static String decimalRestriction;

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
    @FXML
    private BarChart<String, Number> temperatureChart;

    private static ObservableList<XYChart.Series<String, Number>> data
            = FXCollections.<XYChart.Series<String, Number>>observableArrayList();
    @FXML
    private NumberAxis y;
    @FXML
    private CategoryAxis x;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ArrayList<String> observableArrayList = new ArrayList<>();
        observableArrayList.add("Boiling Point of Water");
        observableArrayList.add("Melting Point of Ice");
        observableArrayList.add("Absolute Zero");
        observableArrayList.add("Room Temperature");

        if ("".equals(C.getText()) && "".equals(F.getText()) && "".equals(K.getText())) {
            comboBox.setValue("...");

        }

        comboBox.getItems().removeAll(comboBox.getItems());
        comboBox.getItems().addAll(observableArrayList);

        comboBox.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> selected, String oldBox, String newBox) -> {
            if (newBox != null) {
                switch (newBox) {
                    case "Boiling Point of Water":
                        data.clear();

                        C.setText("100");
                        K.setText("373.2");
                        F.setText("212");
                        addData(C, F, K);
                        comboBox.setValue("...");

                        break;
                    case "Melting Point of Ice":
                        data.clear();

                        C.setText("0");
                        K.setText("273.15");
                        F.setText("32");
                        addData(C, F, K);
                        comboBox.setValue("...");

                        break;
                    case "Absolute Zero":
                        data.clear();

                        C.setText("-273.15");
                        K.setText("0");
                        F.setText("-459.7");
                        addData(C, F, K);
                        comboBox.setValue("...");

                        break;
                    case "Room Temperature":
                        data.clear();
                        C.setText("22");
                        K.setText("295.15");
                        F.setText("68");
                        addData(C, F, K);
                        comboBox.setValue("...");

                        break;
                }
            }
        });

        C.setOnMouseClicked(event -> clear(C, F, K));
        F.setOnMouseClicked(event -> clear(C, F, K));
        K.setOnMouseClicked(event -> clear(C, F, K));

        temperatureChart.setData(data);
    }

    @FXML
    public void switchToSetting(ActionEvent event) throws IOException {
        data.clear();

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
        data.clear();

        Parent root = FXMLLoader.load(getClass().getResource("/view/SceneHistory.fxml"));
        String css = converter.getStylesheets().toString().replaceAll("[^a-zA-Z0-9/:.]", "");
        root.getStylesheets().clear();
        root.getStylesheets().add(css);

        stage = (Stage) converter.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private static void switchBetweenConverter(TextField C, TextField F, TextField K) {
        if (!C.getText().equals("")) {
            data.clear();

            decimalRestriction = ControllerSetting.decimalWant;

            if (decimalRestriction != null) {
                DecimalFormat df = new DecimalFormat(decimalRestriction);

                String FTemperature = df.format(CelsiusToFahrenheit(Double.parseDouble(C.getText())));
                String KTemperature = df.format(CelsiusToKelvin(Double.parseDouble(C.getText())));

                F.setText(FTemperature);
                K.setText(KTemperature);
            } else {

                String FTemperature = Double.toString(CelsiusToFahrenheit(Double.parseDouble(C.getText())));
                String KTemperature = Double.toString(CelsiusToKelvin(Double.parseDouble(C.getText())));

                F.setText(FTemperature);
                K.setText(KTemperature);

            }
            addData(C, F, K);

        } else if (!F.getText().equals("")) {
            data.clear();

            decimalRestriction = ControllerSetting.decimalWant;

            if (decimalRestriction != null) {
                DecimalFormat df = new DecimalFormat(decimalRestriction);

                String CTemperature = df.format(FahrenheitToCelsius(Double.parseDouble(F.getText())));
                String KTemperature = df.format(FahrenheitToKelvin(Double.parseDouble(F.getText())));

                C.setText(CTemperature);
                K.setText(KTemperature);

            } else {
                String CTemperature = Double.toString(FahrenheitToCelsius(Double.parseDouble(F.getText())));
                String KTemperature = Double.toString(FahrenheitToKelvin(Double.parseDouble(F.getText())));

                C.setText(CTemperature);
                K.setText(KTemperature);

            }
            addData(C, F, K);

        } else if (!K.getText().equals("")) {
            data.clear();

            decimalRestriction = ControllerSetting.decimalWant;

            if (decimalRestriction != null) {
                DecimalFormat df = new DecimalFormat(decimalRestriction);

                String CTemperature = df.format(KelvinToCelsius(Double.parseDouble(K.getText())));
                String FTemperature = df.format(KelvinToFahrenheit(Double.parseDouble(K.getText())));

                F.setText(FTemperature);
                C.setText(CTemperature);

            } else {

                String CTemperature = Double.toString(KelvinToCelsius(Double.parseDouble(K.getText())));
                String FTemperature = Double.toString(KelvinToFahrenheit(Double.parseDouble(K.getText())));

                F.setText(FTemperature);
                C.setText(CTemperature);

            }
            addData(C, F, K);

        }
    }

    private static void clear(TextField C, TextField F, TextField K) {
        data.clear();

        C.setText("");
        F.setText("");
        K.setText("");
    }

    @FXML
    private void switchBetweenConverter(ActionEvent event) {
        switchBetweenConverter(C, F, K);
    }

    @FXML
    private void clear(ActionEvent event) {
        clear(C, F, K);

    }

    private static void addData(TextField C, TextField F, TextField K) {
        XYChart.Series<String, Number> TemperatureC3 = new XYChart.Series<>();
        TemperatureC3.setName("°C");
        TemperatureC3.getData().addAll(new XYChart.Data<>("", Double.parseDouble(C.getText())));

        XYChart.Series<String, Number> TemperatureF3 = new XYChart.Series<>();
        TemperatureF3.setName("°F");
        TemperatureF3.getData().addAll(new XYChart.Data<>("", Double.parseDouble(F.getText())));

        XYChart.Series<String, Number> TemperatureK3 = new XYChart.Series<>();
        TemperatureK3.setName(" K");
        TemperatureK3.getData().addAll(new XYChart.Data<>("", Double.parseDouble(K.getText())));

        data.addAll(TemperatureC3, TemperatureF3, TemperatureK3);
    }
}
