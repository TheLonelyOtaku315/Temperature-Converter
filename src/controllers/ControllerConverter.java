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

    private static String oldC = "";
    private static String oldF = "";
    private static String oldK = "";

    private static final ObservableList<XYChart.Series<String, Number>> dataV = FXCollections.<XYChart.Series<String, Number>>observableArrayList();
    private static final ObservableList<XYChart.Series<Number, String>> dataH = FXCollections.<XYChart.Series<Number, String>>observableArrayList();
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

        C.setOnMouseClicked(event -> C.clear());
        F.setOnMouseClicked(event -> F.clear());
        K.setOnMouseClicked(event -> K.clear());

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
    public void switchToHelp(ActionEvent event) throws IOException {
        dataV.clear();
        dataH.clear();

        Parent root = FXMLLoader.load(getClass().getResource("/view/SceneHelp.fxml"));
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

    private void switchBetweenConverter(TextField C, TextField F, TextField K, Label errorMessage, Label temperarureType) {
        String info = "User Temperature";
        temperarureType.setText(info);
        if (isNumeric(C.getText()) || isNumeric(F.getText()) || isNumeric(K.getText())) {

            if (!C.getText().equals("") && !C.getText().equals(oldC)) {
                dataV.clear();
                dataH.clear();

                decimalRestriction = ControllerSetting.decimalSet;
                String FTemperature;
                String KTemperature;
                if (decimalRestriction != null) {
                    DecimalFormat df = new DecimalFormat(decimalRestriction);

                    FTemperature = df.format(CelsiusToFahrenheit(Double.parseDouble(C.getText())));
                    KTemperature = df.format(CelsiusToKelvin(Double.parseDouble(C.getText())));

                } else {

                    FTemperature = Double.toString(CelsiusToFahrenheit(Double.parseDouble(C.getText())));
                    KTemperature = Double.toString(CelsiusToKelvin(Double.parseDouble(C.getText())));

                }
                F.setText(FTemperature);
                K.setText(KTemperature);

                oldC = C.getText();
                oldF = FTemperature;
                oldK = KTemperature;
                Convertion convertion = new Convertion(temperarureType.getText(), " °C: " + C.getText(), "  K: " + K.getText() + "\n °F: " + F.getText());
                XMLHandlerControllers.write(convertion);
                addData(C, F, K);
            } else if (!F.getText().equals("") && !F.getText().equals(oldF)) {
                dataV.clear();
                dataH.clear();

                decimalRestriction = ControllerSetting.decimalSet;
                String CTemperature;
                String KTemperature;
                if (decimalRestriction != null) {
                    DecimalFormat df = new DecimalFormat(decimalRestriction);

                    CTemperature = df.format(FahrenheitToCelsius(Double.parseDouble(F.getText())));
                    KTemperature = df.format(FahrenheitToKelvin(Double.parseDouble(F.getText())));

                } else {
                    CTemperature = Double.toString(FahrenheitToCelsius(Double.parseDouble(F.getText())));
                    KTemperature = Double.toString(FahrenheitToKelvin(Double.parseDouble(F.getText())));

                }
                C.setText(CTemperature);
                K.setText(KTemperature);

                oldC = CTemperature;
                oldF = F.getText();
                oldK = KTemperature;
                Convertion convertion = new Convertion(temperarureType.getText(), " °F: " + F.getText(), " °C: " + C.getText() + "\n  K: " + K.getText());
                XMLHandlerControllers.write(convertion);
                addData(C, F, K);

            } else if (!K.getText().equals("") && !K.getText().equals(oldK)) {
                dataV.clear();
                dataH.clear();

                decimalRestriction = ControllerSetting.decimalSet;
                String CTemperature;
                String FTemperature;
                if (decimalRestriction != null) {
                    DecimalFormat df = new DecimalFormat(decimalRestriction);

                    CTemperature = df.format(KelvinToCelsius(Double.parseDouble(K.getText())));
                    FTemperature = df.format(KelvinToFahrenheit(Double.parseDouble(K.getText())));

                } else {

                    CTemperature = Double.toString(KelvinToCelsius(Double.parseDouble(K.getText())));
                    FTemperature = Double.toString(KelvinToFahrenheit(Double.parseDouble(K.getText())));

                }
                F.setText(FTemperature);
                C.setText(CTemperature);

                oldC = CTemperature;
                oldF = FTemperature;
                oldK = K.getText();
                Convertion convertion = new Convertion(temperarureType.getText(), "  K: " + K.getText(), " °C: " + C.getText() + "\n °F: " + F.getText());
                XMLHandlerControllers.write(convertion);
                addData(C, F, K);
            }
        } else {
            errorMessage(temperarureType, C, F, K, errorMessage, "Can't convert Text Temperature");
        }
        if (0 > Double.valueOf(K.getText())) {
            errorMessage(temperarureType, C, F, K, errorMessage, "Thre Temperature can't be smaller then 0 K");
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

                        break;
                    case "Melting Point of Ice":
                        String info1 = "Melting Point of Ice";
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

                        break;
                    case "Absolute Zero":
                        String info2 = "Absolute Zero";
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

                        break;
                    case "Room Temperature":
                        String info3 = "Room Temperature";
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
                        break;
                    case "Body Temperature":
                        String info4 = "Body Temperature";
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
                        break;
                }
            }
        });

//        comboBox.setOnMouseClicked(event -> clear(C, F, K));
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

    private void addData(TextField C, TextField F, TextField K) {
        dataV.clear();
        dataH.clear();
        illustrationRestriction = ControllerSetting.illustrationSet;
        XYChart.Series<String, Number> VTemperatureC = new XYChart.Series<>();
        VTemperatureC.setName("°C");
        VTemperatureC.getData().add(new XYChart.Data<>("°C", Double.parseDouble("".equals(C.getText()) ? "0" : C.getText())));

        XYChart.Series<String, Number> VTemperatureF = new XYChart.Series<>();
        VTemperatureF.setName("°F");
        VTemperatureF.getData().add(new XYChart.Data<>("°F", Double.parseDouble("".equals(F.getText()) ? "0" : F.getText())));

        XYChart.Series<String, Number> VTemperatureK = new XYChart.Series<>();
        VTemperatureK.setName(" K");
        VTemperatureK.getData().add(new XYChart.Data<>(" K", Double.parseDouble("".equals(K.getText()) ? "0" : K.getText())));

        dataV.addAll(VTemperatureC, VTemperatureF, VTemperatureK);

        XYChart.Series< Number, String> HTemperatureC = new XYChart.Series<>();
        HTemperatureC.setName("°C");
        HTemperatureC.getData().add(new XYChart.Data<>(Double.parseDouble("".equals(C.getText()) ? "0" : C.getText()), "°C"));

        XYChart.Series<Number, String> HTemperatureF = new XYChart.Series<>();
        HTemperatureF.setName("°F");
        HTemperatureF.getData().add(new XYChart.Data<>(Double.parseDouble("".equals(F.getText()) ? "0" : F.getText()), "°F"));

        XYChart.Series< Number, String> HTemperatureK = new XYChart.Series<>();
        HTemperatureK.setName(" K");
        HTemperatureK.getData().add(new XYChart.Data<>(Double.parseDouble("".equals(K.getText()) ? "0" : K.getText()), " K"));

        dataH.addAll(HTemperatureC, HTemperatureF, HTemperatureK);

    }

    private boolean isNumeric(String string) {
        double intValue;

        System.out.println(String.format("Parsing string: \"%s\"", string));

        if (string == null || string.equals("")) {
            return false;
        }

        try {
            intValue = Double.valueOf(string);
            return true;
        } catch (NumberFormatException e) {
        }
        return false;
    }

    private void errorMessage(Label temperarureType, TextField C, TextField F, TextField K, Label errorMessage, String message) {
        temperarureType.setText("Error");
        clear(C, F, K);
        errorMessage.setText(message);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.1), errorMessage);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setCycleCount(25);
        fadeTransition.play();

    }
}
