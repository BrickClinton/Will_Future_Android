package com.example.wbw_first.Entities;

import java.io.Serializable;

public class EActivity implements Serializable {

    private int idactivity;
    private int iduser;
    private int idarea;
    private String namearea;
    private double price;
    private int numberbox;
    private String dateregister;

    public EActivity() {
    }

    public EActivity(int idactivity, int iduser, int idarea, String namearea, double price, int numberbox, String dateregister) {
        this.idactivity = idactivity;
        this.iduser = iduser;
        this.idarea = idarea;
        this.namearea = namearea;
        this.price = price;
        this.numberbox = numberbox;
        this.dateregister = dateregister;
    }

    public String getNamearea() {
        return namearea;
    }

    public void setNamearea(String namearea) {
        this.namearea = namearea;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getIdactivity() {
        return idactivity;
    }

    public void setIdactivity(int idactivity) {
        this.idactivity = idactivity;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public int getIdarea() {
        return idarea;
    }

    public void setIdarea(int idarea) {
        this.idarea = idarea;
    }

    public int getNumberbox() {
        return numberbox;
    }

    public void setNumberbox(int numberbox) {
        this.numberbox = numberbox;
    }

    public String getDateregister() {
        return dateregister;
    }

    public void setDateregister(String dateregister) {
        this.dateregister = dateregister;
    }

    @Override
    public String toString() {
        return "EActivity{" +
                "idactivity=" + idactivity +
                ", iduser=" + iduser +
                ", idarea=" + idarea +
                ", namearea='" + namearea + '\'' +
                ", price=" + price +
                ", numberbox=" + numberbox +
                ", dateregister='" + dateregister + '\'' +
                '}';
    }
}
