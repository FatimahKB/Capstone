package com.example.fatim.makeawish;

import java.util.Date;

public class User {

    String username;
    String email;
    String password;
    Date DOB;

    public User(){}

    public User(String email, String password, Date DOB){
        this.username=username;
        this.email= email;
        this.password= password;
        this.DOB = DOB;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public Date getDOB() {
        return DOB;
    }
}
