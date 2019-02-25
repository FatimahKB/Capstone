package com.example.fatim.makeawish;

import java.util.ArrayList;
import java.util.Date;

public class Wishlist {

    String name;
    ArrayList<Item> itemLlist = new ArrayList<Item>();

    public Wishlist(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Item> getItemLlist() {
        return itemLlist;
    }

    public void setItemLlist(ArrayList<Item> itemLlist) {
        this.itemLlist = itemLlist;
    }

    public void addItem (Item item){
        this.itemLlist.add(item);
    }

    public void removeItem(Item item){
        this.itemLlist.remove(item);
    }

    public int getSize(){
        return this.itemLlist.size();
    }
}