package com.example.testejava.model;

import java.io.Serializable;

public class Car implements Serializable {

    private String _id;
    private String model;
    private String price;
    private String year;
    private String description;

    public Car(String model, String price, String year, String description) {
        this.model = model;
        this.price = price;
        this.year = year;
        this.description = description;
    }

    public Car(String model, String price, String year) {
        this.model = model;
        this.price = price;
        this.year = year;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
