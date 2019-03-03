package com.example.fatim.makeawish;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddingItem extends AppCompatActivity {
    EditText name;
    EditText price;
    EditText quantity;
    EditText link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_item);
        Button btn_addingItem_search_button =(Button) findViewById(R.id.addingItem_search_button);
        Button btn_addingItem_add_button =(Button) findViewById(R.id.addingItem_add_button);
        name=(EditText)findViewById(R.id.addingItem_name_editText);
        price=(EditText)findViewById(R.id.addingItem_price_editText);
        quantity=(EditText)findViewById(R.id.addingItem_quantity_editText);
        link=(EditText)findViewById(R.id.addingItem_link_editText);
        // To get an instance of the databse so we can add/remove etc..
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        if(valid()){
            Item item=new Item(name.getText().toString(), Integer.parseInt(quantity.getText().toString()), Double.parseDouble(price.getText().toString()),Double.parseDouble(price.getText().toString()));

        }



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
    public boolean valid() {
        if (name.getText().toString().isEmpty() || quantity.getText().toString().isEmpty() || price.getText().toString().isEmpty() ) {
            Toast.makeText(this, "Please fill all the required fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
           return true;
        }
    }


}
