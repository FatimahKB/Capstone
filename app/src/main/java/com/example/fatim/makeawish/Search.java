package com.example.fatim.makeawish;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class Search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //Navigation bar
        BottomNavigationView bottom = findViewById(R.id.navigationView);

        bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.navigation_history:
                        startActivity(new Intent(Search.this,History.class));break;
                    case R.id.navigation_todo:
                        startActivity(new Intent(Search.this,ToDo.class));break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(Search.this,Profile.class));break;

                    case R.id.navigation_search:
                        startActivity(new Intent(Search.this,Search.class));break;
                    case R.id.navigation_settings:
                        startActivity(new Intent(Search.this,Settings.class));break;
                    default:



                }
                return false;
            }
        });

    }
}
