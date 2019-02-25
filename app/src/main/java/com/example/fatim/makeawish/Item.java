package com.example.fatim.makeawish;

import android.widget.ImageView;

public class Item {
    String name;
    int quantity;
    double price;
    double remaining_price;
    String link;
    ImageView img;

    public Item(){

    }

    public Item(String name, int quantity, double price, double remaining_price){
        this.name=name;
        this.quantity=quantity;
        this.price=price;
        this.remaining_price=remaining_price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;

    }

    public void setRemaining_price(double price) {
        this.remaining_price = price;
    }

    public double getRemaining_price() {
        return this.remaining_price;

    }

    public void setPrice(double price) {
        this.price = price;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setImage(ImageView img) {
        this.img=img;
    }

    public ImageView getImage() {
        return this.img;
    }

    public String getLink() {
        return this.link;
    }
    public void setLink(String link) {
        this.link=link;
    }
}
