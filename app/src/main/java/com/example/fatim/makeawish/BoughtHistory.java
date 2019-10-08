package com.example.fatim.makeawish;

import java.util.Date;

public class BoughtHistory
{
    public String gifted_user;
    public String item_name;
    public double item_price;
    public String date_bought;

    public BoughtHistory(){}

    public BoughtHistory(String gifted_user, String item_name, double item_price, String date_bought) {
        this.gifted_user = gifted_user;
        this.item_name = item_name;
        this.item_price = item_price;
        this.date_bought = date_bought;
    }

    public String getGifted_user() {
        return gifted_user;
    }

    public void setGifted_user(String gifted_user) {
        this.gifted_user = gifted_user;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public double getItem_price() {
        return item_price;
    }

    public void setItem_price(double item_price) {
        this.item_price = item_price;
    }

    public String getDate_bought() {
        return date_bought;
    }

    public void setDate_bought(String date_bought) {
        this.date_bought = date_bought;
    }
}
