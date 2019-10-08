package com.example.fatim.makeawish;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class user_wishlist extends AppCompatActivity {
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_wishlist);
        add=(Button)findViewById(R.id.userwishlist_add_button);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(user_wishlist.this,AddingItem.class));            }
        });
        //Navigation bar
        BottomNavigationView bottom = findViewById(R.id.navigationView);

        bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.navigation_history:
                        startActivity(new Intent(user_wishlist.this,History.class));break;
                    case R.id.navigation_todo:
                        startActivity(new Intent(user_wishlist.this,ToDo.class));break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(user_wishlist.this,Profile.class));break;

                    case R.id.navigation_search:
                        startActivity(new Intent(user_wishlist.this,Search.class));break;
                    case R.id.navigation_settings:
                        startActivity(new Intent(user_wishlist.this,Signout.class));break;
                    default:



                }
                return false;
            }
        });

    }
}
