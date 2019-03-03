package com.example.fatim.makeawish;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class History extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Button btn_history_recived_button =(Button) findViewById(R.id.history_recived_button);

        btn_history_recived_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //NOTE: shared pref for received and gifted
                startActivity(new Intent(History.this,History.class));

            }
        });

        Button btn_history_purchased_button =(Button) findViewById(R.id.history_purchased_button);

        btn_history_purchased_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //NOTE: shared pref for received and gifted
                startActivity(new Intent(History.this,History.class));

            }
        });
    }
}
