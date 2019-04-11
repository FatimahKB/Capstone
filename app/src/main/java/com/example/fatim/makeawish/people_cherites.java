package com.example.fatim.makeawish;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class people_cherites extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_cherites);
        Button btn_people_cherites_people_button =(Button) findViewById(R.id.people_cherites_people_button);
        Button btn_people_cherites_cherities_button =(Button) findViewById(R.id.people_cherites_cherities_button);
        Button btn_people_cherites_items_button =(Button) findViewById(R.id.people_cherites_items_button);

        btn_people_cherites_people_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(people_cherites.this,people_cherites.class));

            }
        });

        btn_people_cherites_cherities_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(people_cherites.this,people_cherites.class));

            }
        });

        btn_people_cherites_items_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(people_cherites.this,people_cherites.class));

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
                        startActivity(new Intent(people_cherites.this,History.class));break;
                    case R.id.navigation_todo:
                        startActivity(new Intent(people_cherites.this,ToDo.class));break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(people_cherites.this,Profile.class));break;

                    case R.id.navigation_search:
                        startActivity(new Intent(people_cherites.this,Search.class));break;
                    case R.id.navigation_settings:
                        startActivity(new Intent(people_cherites.this,Settings.class));break;
                    default:



                }
                return false;
            }
        });


    }
}
