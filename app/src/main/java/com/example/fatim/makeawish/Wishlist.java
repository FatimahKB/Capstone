package com.example.fatim.makeawish;

import java.util.ArrayList;
import java.util.Date;

public class Wishlist {

     private String name;
     private ArrayList<Item> itemlist = new ArrayList<Item>();

    public Wishlist(){
    }

    public Wishlist(String name, ArrayList<Item> itemlist) {
        this.name=name;
        this.itemlist=itemlist;
    }
        public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Item> getItemLlist() {
        return itemlist;
    }

    public void setItemLlist(ArrayList<Item> itemLlist) {
        this.itemlist = itemLlist;
    }

    public void addItem (Item item){
        this.itemlist.add(item);
    }

    public void removeItem(Item item){
        this.itemlist.remove(item);
    }

    public int getSize(){
        return this.itemlist.size();
    }
}