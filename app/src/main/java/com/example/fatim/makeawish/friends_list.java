package com.example.fatim.makeawish;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class friends_list extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);
        Button btn_friends_list_public_button=(Button) findViewById(R.id.friends_list_public_button);
        btn_friends_list_public_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //NOTE: SharedPreferences for public/private refreshed the view
                startActivity(new Intent(friends_list.this,friends_list.class));

            }
        });

        Button btn_friends_list_private_button=(Button) findViewById(R.id.friends_list_private_button);
        btn_friends_list_private_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //NOTE: SharedPreferences for public/private refreshed the view
                startActivity(new Intent(friends_list.this,friends_list.class));

            }
        });
    }
}
