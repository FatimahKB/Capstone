package com.example.fatim.makeawish;

import java.util.ArrayList;
import java.util.Date;

public class PrivateWishlist extends Wishlist {

    ArrayList<User> authorizedList = new ArrayList<User>();
    Date expDate;

    public PrivateWishlist(String name, Date exp){
        this.name=name;
        this.expDate= exp;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public ArrayList<User> getAuthorizedList() {
        return authorizedList;
    }

    public void setAuthorizedList(ArrayList<User> authorizedList) {
        this.authorizedList = authorizedList;
    }
}
