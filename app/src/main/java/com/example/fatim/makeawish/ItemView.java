package com.example.fatim.makeawish;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ItemView extends AppCompatActivity {
    TextView textViewItemName;
    TextView textViewPrice;
    TextView textViewQuantity;


    TextView remaining_price;
    EditText editTextPitch;
    Button btnPitch;
    Button btnBuy;
    SharedPreferences sharedPreferences;
    public DatabaseReference mDatabase;
    Item items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view2);
///////////
        textViewItemName = (TextView) findViewById(R.id.textViewItemName);
        textViewPrice = (TextView) findViewById(R.id.textViewPrice);
        textViewQuantity = (TextView) findViewById(R.id.textViewQuantity);
        remaining_price = (TextView) findViewById(R.id.remaining_price);

        editTextPitch = (EditText) findViewById(R.id.editTextPitch);
        btnPitch = (Button) findViewById(R.id.btnPitch);
        btnBuy = (Button) findViewById(R.id.btnBuy);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //final String item_name=sharedPreferences.getString("selected_item","").trim();
        final String ListType=sharedPreferences.getString("listType","").trim();
        final String searched_username=sharedPreferences.getString("","").trim();
        final String private_name=sharedPreferences.getString("selected_private_list","").trim();

        Long item_pos = sharedPreferences.getLong("item_pos", 0);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final String listType = sharedPreferences.getString("listType", "").trim();

        if(listType.equals("Public")){
        mDatabase.child("Users").child(searched_username).child("Lists").child(ListType).child("Public").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Get Post object and use the values to update the UI
                if (dataSnapshot.exists()) {
                    items = dataSnapshot.getValue(Item.class);
                  //  textViewItemName.setText(item_clicked);
                    sharedPreferences.getString("clicked_item","").trim();
                    final String item_clicked = sharedPreferences.getString("clicked_item", "").trim();
                    final String item_price = sharedPreferences.getString("price", "").trim();
                    final String item_remaining_price = sharedPreferences.getString("remaining_price", "").trim();
                    final int item_quantity = sharedPreferences.getInt("quantity", 1);

                    textViewItemName.setText(item_clicked);
                    textViewPrice.setText("Item Price : " + items.getPrice() + "");
                    textViewQuantity.setText("Item Quantity : " + items.getQuantity() + "");
                    remaining_price.setText("Remaining price : " + items.getRemaining_price() + "");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(null, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });}
        else{
            mDatabase.child("Users").child(searched_username).child("Lists").child(ListType).child("Private").child(private_name).addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    // Get Post object and use the values to update the UI
                    if (dataSnapshot.exists()) {
                        items = dataSnapshot.getValue(Item.class);
                        //  textViewItemName.setText(item_clicked);
                        sharedPreferences.getString("clicked_item","").trim();
                        final String item_clicked = sharedPreferences.getString("clicked_item", "").trim();
                        final String item_price = sharedPreferences.getString("price", "").trim();
                        final String item_remaining_price = sharedPreferences.getString("remaining_price", "").trim();
                        final int item_quantity = sharedPreferences.getInt("quantity", 1);
                        textViewItemName.setText(item_clicked);
                        textViewPrice.setText("Item Price : " + item_price);
                        textViewQuantity.setText("Item Quantity : " + item_quantity);
                        remaining_price.setText("Remaining price : " +item_remaining_price);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    Log.w(null, "loadPost:onCancelled", databaseError.toException());
                    // ...
                }
            });
        }

        btnPitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Double.parseDouble(editTextPitch.getText().toString())>items.getRemaining_price()){
                    Toast.makeText(ItemView.this,"Pitch in amount should be less than or equal to the remaining price", Toast.LENGTH_SHORT).show();
                }else{

                    SharedPreferences.Editor e =sharedPreferences.edit();
                    e.putFloat("credit_amount",Float.parseFloat(editTextPitch.getText().toString()));
                    e.commit();
                    startActivity(new Intent(ItemView.this,Transaction.class));
                }

            }
        });
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(items.getRemaining_price()!=items.getPrice())
                {
                    Toast.makeText(ItemView.this,"Cannot buy a product that has been pitched in by anyone", Toast.LENGTH_SHORT).show();
                }
                else {
                    SharedPreferences.Editor e =sharedPreferences.edit();
                    e.putFloat("credit_amount",(float)(items.getPrice()));
                    e.commit();
                    startActivity(new Intent(ItemView.this, Buying.class));
                }
            }
        });
    }
}



