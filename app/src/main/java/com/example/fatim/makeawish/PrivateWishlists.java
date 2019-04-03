package com.example.fatim.makeawish;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class PrivateWishlists extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_wishlists);

        Button btn_imageButton =(Button) findViewById(R.id.add_private);
        btn_imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PrivateWishlists.this,AddingItem.class));

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
                        startActivity(new Intent(PrivateWishlists.this,History.class));break;
                    case R.id.navigation_todo:
                        startActivity(new Intent(PrivateWishlists.this,ToDo.class));break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(PrivateWishlists.this,Profile.class));break;

                    case R.id.navigation_search:
                        startActivity(new Intent(PrivateWishlists.this,Search.class));break;
                    case R.id.navigation_settings:
                        startActivity(new Intent(PrivateWishlists.this,Settings.class));break;
                    default:



                }
                return false;
            }
        });

    }
}
