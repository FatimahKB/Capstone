package com.example.fatim.makeawish;

import android.widget.ImageView;

public class Gift {
        private String giftname;
        private double price;
        private String username;

        public Gift(){}
        public Gift(String giftname,double price, String username){
            this.giftname=giftname;
            this.price=price;
            this.username=username;
        }


        public void setGifttame(String giftname) {
            this.giftname = giftname;
        }
        public void setUserame(String username) {
        this.username = username;
    }
        public void setPrice(double price) {
        this.price = price;
    }

        public String getUsername() {
            return username;
        }
        public String getGiftname() {
        return giftname;
    }
        public double getPrice() { return price; }

}
