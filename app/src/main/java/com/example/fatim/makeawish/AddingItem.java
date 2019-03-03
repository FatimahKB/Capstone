package com.example.fatim.makeawish;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddingItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_item);
        Button btn_addingItem_search_button =(Button) findViewById(R.id.addingItem_search_button);
        Button btn_addingItem_add_button =(Button) findViewById(R.id.addingItem_add_button);

        btn_addingItem_search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddingItem.this,Search.class));
            }
        });

        btn_addingItem_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //NOTE: navigate to public/private correctly
                startActivity(new Intent(AddingItem.this,user_wishlist.class));

            }
        });
    }
}
