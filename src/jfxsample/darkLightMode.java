/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfxsample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Tonny
 */
public class darkLightMode {

    public void setDarkMode(BorderPane parent, String number) {
        parent.getStylesheets().clear();
        parent.getStylesheets().add("styles/light" + number + ".css");
        Image image = new Image("img/moon.png");
    }

    public void setLightMode(BorderPane parent, String number) {
        parent.getStylesheets().clear();
        parent.getStylesheets().add("styles/dark" + number + ".css");
        Image image = new Image("img/sun.png");
    }

}
