/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Tonny
 */
public class Convertion {

    private String information;
    private String informationEnter;
    private String InformationGiven;

    public Convertion(String information, String informationEnter, String InformationGiven) {
        this.information = information;
        this.informationEnter = informationEnter;
        this.InformationGiven = InformationGiven;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getInformationEnter() {
        return informationEnter;
    }

    public void setInformationEnter(String informationEnter) {
        this.informationEnter = informationEnter;
    }

    public String getInformationGiven() {
        return InformationGiven;
    }

    public void setInformationGiven(String InformationGiven) {
        this.InformationGiven = InformationGiven;
    }

    @Override
    public String toString() {
        return "Convertion{" + "information=" + information + ", informationEnter=" + informationEnter + ", InformationGiven=" + InformationGiven + '}';
    }

}
