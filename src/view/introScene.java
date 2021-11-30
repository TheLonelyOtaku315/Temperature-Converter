/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 *
 * @author Tonny
 */
public class introScene {

    private Scene scene;

    public introScene(Button btn) {

        BorderPane bp = new BorderPane();

        VBox vb = new VBox();
//Title
        Label Title = new Label("Temperature Converter (About)");
        Title.setPadding(new Insets(15, 15, 15,15)); //top, right, bottom, left
        Title.setStyle("primary-color");
//MenuBar
        MenuBar mb = new MenuBar();
        Menu Converter = new Menu("Converter");
        Menu Setting = new Menu("Setting");
        Menu History = new Menu("History");
        Menu About = new Menu("About");

        MenuItem Converter1 = new MenuItem("Converter");
        MenuItem Setting1 = new MenuItem("Setting");
        MenuItem History1 = new MenuItem("History");
        MenuItem About1 = new MenuItem("About");

        Converter.getItems().add(Converter1);
        Setting.getItems().add(Setting1);
        History.getItems().add(History1);
        About.getItems().add(About1);

        mb.getMenus().addAll(Converter, Setting, History, About);

        vb.getChildren().addAll(Title, mb);

        bp.setTop(vb);

        GridPane grid = new GridPane();
        scene = new Scene(bp, 1000, 700);

        Text txt = new Text();
        txt.setText("Wecome to the main page\n"
                + "\nthis is were we will explain how to use our program.\n"
                + "\nIn the temperature converter:\n"
                + "\nThis is where you will do all the convertion.There will be box to enter the temperature you want and by clicking on convert, it will convert the temperature. To do the conversion we will use the temperature conversion formula to do so. The program will convert the temperature to °C, °F and K. When the user enters the temperature in °C, the program will convert the temperature to the other measure, °F and K. When the user enters the temperature in °F, the program will convert the temperature to the other measure, °C and K. When the user enters the temperature in K, the program will convert the temperature to the other measure, °F and °C. It will also have a visual aspect in that there will be thermometers with a bar that rises to the appropriate level of each converted temperature. You would input an integer and 2 new integers will output along with the change in each thermometer. Pretty easy to execute. The math is quite simple so that won’t be too much of an issue. Hardest part will be the graphical representation of each temperature and making it update every time there is a new input. \n"
                + "\nIn the setting:\n"
                + "\nThis where you will be able to controle differen thing. You will be able to change between dark and light mode, language, illustration and number of decimal. \n");
        txt.setFont(Font.font("Verdana", 14));
        txt.setTextAlignment(TextAlignment.CENTER);
        txt.setWrappingWidth(scene.getWidth() / 2);

        Image img = new Image("img/thermometer.png");
        ImageView imgV = new ImageView(img);
        imgV.setFitHeight(500);
        imgV.setFitWidth(150);

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(20);

        grid.add(txt, 0, 0);
        GridPane.setHalignment(txt, HPos.CENTER);

        grid.add(btn, 0, 1);
        GridPane.setHalignment(btn, HPos.CENTER);

        grid.add(imgV, 1, 0);
        GridPane.setHalignment(btn, HPos.CENTER);
        bp.setCenter(grid);

    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
//
//    private void switchToSetting(Stage stage) throws IOException {
//        Parent root = FXMLLoader.load(getClass().getResource("setting.fxml"));
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }
}
