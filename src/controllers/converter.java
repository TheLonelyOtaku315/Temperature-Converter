/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

/**
 *
 * @author Tonny
 */
public class converter {

    public static double CelsiusToFahrenheit(double C) {
        double F = (C * 1.8) + 32;
        return F;
    }

    public static double FahrenheitToCelsius(double F) {
        double C = (F - 32) * 0.5556;
        return C;
    }

    public static double CelsiusToKelvin(double C) {
        double K = C + 273.15;
        return K;
    }

    public static double KelvinToCelsius(double K) {
        double C = K - 273.15;
        return C;
    }

    public static double KelvinToFahrenheit(double K) {

        double F = (K - 273.15) * 9 / 5 + 32;
        return F;
    }

    public static double FahrenheitToKelvin(double F) {

        double K = (F - 32) * 5 / 9 + 273.15;
        return K;
    }

}
