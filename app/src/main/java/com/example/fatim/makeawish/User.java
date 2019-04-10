package com.example.fatim.makeawish;

import java.util.Date;

public class User {

    private String username;
    private String email;
    private String password;
    private String DOB;

    public User(){}

//    public User(String username){
//        this.username=username;
//    }

    public User(String username,String password, String DOB){
        this.username=username;
        this.password=password;
        this.DOB=DOB;
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
    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getDOB() {
        return DOB;
    }
}
