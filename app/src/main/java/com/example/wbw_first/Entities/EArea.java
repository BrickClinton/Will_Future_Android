package com.example.wbw_first.Entities;

import java.io.Serializable;

public class EArea implements Serializable {

    private int idarea;
    private String namearea;
    private double price;
    private String dateregister;

    public EArea() {
    }

    public EArea(int idarea, String namearea, double price, String dateregister) {
        this.idarea = idarea;
        this.namearea = namearea;
        this.price = price;
        this.dateregister = dateregister;
    }

    public int getIdarea() {
        return idarea;
    }

    public void setIdarea(int idarea) {
        this.idarea = idarea;
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

    public String getDateregister() {
        return dateregister;
    }

    public void setDateregister(String dateregister) {
        this.dateregister = dateregister;
    }

    @Override
    public String toString() {
        return namearea;
    }
}
