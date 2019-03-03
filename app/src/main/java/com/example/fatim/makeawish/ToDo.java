package com.example.fatim.makeawish;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ToDo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);

        Button to_do_cancel_button = findViewById(R.id.to_do_cancel_button);
        Button to_do_accept_button = findViewById(R.id.to_do_accept_button);
        Button to_do_decline_button = findViewById(R.id.to_do_decline_button);
        Button to_do_buy_button = findViewById(R.id.to_do_buy_button);

        to_do_buy_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ToDo.this, ToDo.class));
            }
        });

        to_do_decline_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ToDo.this, ToDo.class));
            }
        });

        to_do_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ToDo.this, ToDo.class));
            }
        });

        to_do_accept_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ToDo.this, ToDo.class));
            }
        });
    }
}
