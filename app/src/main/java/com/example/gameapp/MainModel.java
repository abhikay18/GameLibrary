package com.example.gameapp;

public class MainModel {
    String category, name, surl;
    String rating;

    MainModel() {

    }

    public MainModel(String category, String name, String surl, String rating) {
        this.category = category;
        this.name = name;
        this.surl = surl;
        this.rating = rating;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurl() {
        return surl;
    }

    public void setSurl(String surl) {
        this.surl = surl;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
