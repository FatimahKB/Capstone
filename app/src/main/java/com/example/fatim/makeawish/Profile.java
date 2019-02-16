package com.example.fatim.makeawish;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Profile extends AppCompatActivity {
    int x =5+2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }
    public void hello(){
        int y = x+5;
    }
    public void bye(){
        int y = x+5;
    }
}
