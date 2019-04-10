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

public class item_view extends AppCompatActivity {
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
        setContentView(R.layout.activity_item_view);
        ////////////////////////////
        textViewItemName = (TextView) findViewById(R.id.textViewItemName);
        textViewPrice = (TextView) findViewById(R.id.textViewPrice);
        textViewQuantity = (TextView) findViewById(R.id.textViewQuantity);
        remaining_price = (TextView) findViewById(R.id.remaining_price);

        editTextPitch = (EditText) findViewById(R.id.editTextPitch);
        btnPitch = (Button) findViewById(R.id.btnPitch);
        btnBuy = (Button) findViewById(R.id.btnBuy);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //final String item_name=sharedPreferences.getString("selected_item","").trim();
        String user_name = sharedPreferences.getString("searched_user", "").trim();
        Long item_pos = sharedPreferences.getLong("item_pos", 0);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Users").child(user_name).child("Public").child("item" + item_pos).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                if (dataSnapshot.exists()) {
                    items = dataSnapshot.getValue(Item.class);
                    textViewItemName.setText("Item Name : " + items.getName());
                    textViewPrice.setText("Item Price : " + items.getPrice() + "");
                    textViewQuantity.setText("Item Quantity : " + items.getQuantity() + "");
                    remaining_price.setText("Remaining price : " + items.getRemaining_price() + "");
                }else{
                    startActivity(new Intent(item_view.this,SearchResultDisplay.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(null, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });

        btnPitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Double.parseDouble(editTextPitch.getText().toString())>items.getRemaining_price()){
                    Toast.makeText(item_view.this,"Pitch in amount should be less than or equal to the remaining price", Toast.LENGTH_SHORT).show();
                }else{

                    SharedPreferences.Editor e =sharedPreferences.edit();
                    e.putFloat("credit_amount",Float.parseFloat(editTextPitch.getText().toString()));
                    e.commit();
                    startActivity(new Intent(item_view.this,Transaction.class));
                }

            }
        });
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(items.getRemaining_price()!=items.getPrice())
                {
                    Toast.makeText(item_view.this,"Cannot buy a product that has been pitched in by anyone", Toast.LENGTH_SHORT).show();
                }
                else {
                    SharedPreferences.Editor e =sharedPreferences.edit();
                    e.putFloat("credit_amount",(float)(items.getPrice()));
                    e.commit();
                    startActivity(new Intent(item_view.this, Buying.class));
                }
            }
        });
    }
}
