/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import javafx.scene.layout.BorderPane;

/**
 *
 * @author Tonny
 */
public class darkLightMode {

    public void setDarkMode(BorderPane parent) {
        parent.getStylesheets().clear();
        parent.getStylesheets().add("styles/light.css");
    }

    public void setLightMode(BorderPane parent) {
        parent.getStylesheets().clear();
        parent.getStylesheets().add("styles/dark.css");
    }

}
