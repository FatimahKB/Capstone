package com.example.fatim.makeawish;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
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
        //Navigation bar
        BottomNavigationView bottom = findViewById(R.id.navigationView);

        bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.navigation_history:
                        startActivity(new Intent(friends_list.this,History.class));break;
                    case R.id.navigation_todo:
                        startActivity(new Intent(friends_list.this,ToDo.class));break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(friends_list.this,Profile.class));break;

                    case R.id.navigation_search:
                        startActivity(new Intent(friends_list.this,Search.class));break;
                    case R.id.navigation_settings:
                        startActivity(new Intent(friends_list.this,Settings.class));break;
                    default:



                }
                return false;
            }
        });

    }
}
