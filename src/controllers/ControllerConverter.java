/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
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

                XYChart.Series<String, Number> TemperatureC = new XYChart.Series<>();
                TemperatureC.setName("°C");
                TemperatureC.getData().addAll(new XYChart.Data<>("", Double.parseDouble(C.getText())));

                XYChart.Series<String, Number> TemperatureF = new XYChart.Series<>();
                TemperatureF.setName("°F");
                TemperatureF.getData().addAll(new XYChart.Data<>("", Double.parseDouble(F.getText())));

                XYChart.Series<String, Number> TemperatureK = new XYChart.Series<>();
                TemperatureK.setName(" K");
                TemperatureK.getData().addAll(new XYChart.Data<>("", Double.parseDouble(K.getText())));

                data.addAll(TemperatureC, TemperatureF, TemperatureK);
            } else {

                String FTemperature = Double.toString(CelsiusToFahrenheit(Double.parseDouble(C.getText())));
                String KTemperature = Double.toString(CelsiusToKelvin(Double.parseDouble(C.getText())));

                F.setText(FTemperature);
                K.setText(KTemperature);

                XYChart.Series<String, Number> TemperatureC = new XYChart.Series<>();
                TemperatureC.setName("°C");
                TemperatureC.getData().addAll(new XYChart.Data<>("°C", Double.parseDouble(C.getText())));

                XYChart.Series<String, Number> TemperatureF = new XYChart.Series<>();
                TemperatureF.setName("°F");
                TemperatureF.getData().addAll(new XYChart.Data<>("°F", Double.parseDouble(F.getText())));

                XYChart.Series<String, Number> TemperatureK = new XYChart.Series<>();
                TemperatureK.setName(" K");
                TemperatureK.getData().addAll(new XYChart.Data<>(" K", Double.parseDouble(K.getText())));

                data.addAll(TemperatureC, TemperatureF, TemperatureK);

            }

        } else if (!F.getText().equals("")) {
            data.clear();

            decimalRestriction = ControllerSetting.decimalWant;

            if (decimalRestriction != null) {
                DecimalFormat df = new DecimalFormat(decimalRestriction);

                String CTemperature = df.format(FahrenheitToCelsius(Double.parseDouble(F.getText())));
                String KTemperature = df.format(FahrenheitToKelvin(Double.parseDouble(F.getText())));

                C.setText(CTemperature);
                K.setText(KTemperature);

                XYChart.Series<String, Number> TemperatureC = new XYChart.Series<>();
                TemperatureC.setName("°C");
                TemperatureC.getData().addAll(new XYChart.Data<>("", Double.parseDouble(C.getText())));

                XYChart.Series<String, Number> TemperatureF = new XYChart.Series<>();
                TemperatureF.setName("°F");
                TemperatureF.getData().addAll(new XYChart.Data<>("", Double.parseDouble(F.getText())));

                XYChart.Series<String, Number> TemperatureK = new XYChart.Series<>();
                TemperatureK.setName(" K");
                TemperatureK.getData().addAll(new XYChart.Data<>("", Double.parseDouble(K.getText())));

                data.addAll(TemperatureC, TemperatureF, TemperatureK);
            } else {
                String CTemperature = Double.toString(FahrenheitToCelsius(Double.parseDouble(F.getText())));
                String KTemperature = Double.toString(FahrenheitToKelvin(Double.parseDouble(F.getText())));

                C.setText(CTemperature);
                K.setText(KTemperature);

                XYChart.Series<String, Number> TemperatureC = new XYChart.Series<>();
                TemperatureC.setName("°C");
                TemperatureC.getData().addAll(new XYChart.Data<>("", Double.parseDouble(C.getText())));

                XYChart.Series<String, Number> TemperatureF = new XYChart.Series<>();
                TemperatureF.setName("°F");
                TemperatureF.getData().addAll(new XYChart.Data<>("", Double.parseDouble(F.getText())));

                XYChart.Series<String, Number> TemperatureK = new XYChart.Series<>();
                TemperatureK.setName(" K");
                TemperatureK.getData().addAll(new XYChart.Data<>("", Double.parseDouble(K.getText())));

                data.addAll(TemperatureC, TemperatureF, TemperatureK);
            }
        } else if (!K.getText().equals("")) {
            data.clear();

            decimalRestriction = ControllerSetting.decimalWant;

            if (decimalRestriction != null) {
                DecimalFormat df = new DecimalFormat(decimalRestriction);

                String CTemperature = df.format(KelvinToCelsius(Double.parseDouble(K.getText())));
                String FTemperature = df.format(KelvinToFahrenheit(Double.parseDouble(K.getText())));

                F.setText(FTemperature);
                C.setText(CTemperature);

                XYChart.Series<String, Number> TemperatureC = new XYChart.Series<>();
                TemperatureC.setName("°C");
                TemperatureC.getData().addAll(new XYChart.Data<>("", Double.parseDouble(C.getText())));

                XYChart.Series<String, Number> TemperatureF = new XYChart.Series<>();
                TemperatureF.setName("°F");
                TemperatureF.getData().addAll(new XYChart.Data<>("", Double.parseDouble(F.getText())));

                XYChart.Series<String, Number> TemperatureK = new XYChart.Series<>();
                TemperatureK.setName(" K");
                TemperatureK.getData().addAll(new XYChart.Data<>("", Double.parseDouble(K.getText())));

                data.addAll(TemperatureC, TemperatureF, TemperatureK);
            } else {

                String CTemperature = Double.toString(KelvinToCelsius(Double.parseDouble(K.getText())));
                String FTemperature = Double.toString(KelvinToFahrenheit(Double.parseDouble(K.getText())));

                F.setText(FTemperature);
                C.setText(CTemperature);

                XYChart.Series<String, Number> TemperatureC = new XYChart.Series<>();
                TemperatureC.setName("°C");
                TemperatureC.getData().addAll(new XYChart.Data<>("", Double.parseDouble(C.getText())));

                XYChart.Series<String, Number> TemperatureF = new XYChart.Series<>();
                TemperatureF.setName("°F");
                TemperatureF.getData().addAll(new XYChart.Data<>("", Double.parseDouble(F.getText())));

                XYChart.Series<String, Number> TemperatureK = new XYChart.Series<>();
                TemperatureK.setName(" K");
                TemperatureK.getData().addAll(new XYChart.Data<>("", Double.parseDouble(K.getText())));

                data.addAll(TemperatureC, TemperatureF, TemperatureK);
            }
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

}
