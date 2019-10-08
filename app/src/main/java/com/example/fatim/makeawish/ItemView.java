package com.example.fatim.makeawish;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ItemView extends AppCompatActivity {

    //controllers
    TextView textViewItemName;
    TextView textViewPrice;
    TextView textViewQuantity;
    ImageView img;
    String username [];
    TextView remaining_price;
    EditText editTextPitch;
    Button btnPitch;
    Button btnBuy;

//Database
    FirebaseStorage storage;
    SharedPreferences sharedPreferences;
    public DatabaseReference mDatabase;
    Item items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view2);

        //setting the controllers
        textViewItemName = (TextView) findViewById(R.id.textViewItemName);
        textViewPrice = (TextView) findViewById(R.id.textViewPrice);
        textViewQuantity = (TextView) findViewById(R.id.textViewQuantity);
        remaining_price = (TextView) findViewById(R.id.remaining_price);
        img=findViewById(R.id.item_details_image);
        editTextPitch = (EditText) findViewById(R.id.editTextPitch);
        btnPitch = (Button) findViewById(R.id.btnPitch);
        btnBuy = (Button) findViewById(R.id.btnBuy);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String ListType=sharedPreferences.getString("listType","").trim();
        final String searched_username=sharedPreferences.getString("friends","").trim();
        final String private_name=sharedPreferences.getString("selected_private_list","").trim();
        Long item_pos = sharedPreferences.getLong("item_pos", 0);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        username =user.getEmail().split("@");
        storage=FirebaseStorage.getInstance();
        final StorageReference storageRef = storage.getReferenceFromUrl(sharedPreferences.getString("path","gs://makeawish-3b12e.appspot.com/images.jpg"));

        final long ONE_MEGABYTE = 1024 * 1024;
        storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                img.setImageBitmap(bitmap);
//                        img.setWidth(50);
//                        img.setMaxHeight(50);
            }
        });

                    sharedPreferences.getString("clicked_item","").trim();
                    final String item_clicked = sharedPreferences.getString("clicked_item", "").trim();
                    final String item_price = sharedPreferences.getString("price", "").trim();
                    final String item_remaining_price = sharedPreferences.getString("remaining_price", "").trim();
                    final int item_quantity = sharedPreferences.getInt("quantity", 1);

                    textViewItemName.setText(item_clicked);
                    textViewPrice.setText("Item Price : " + item_price);
                    textViewQuantity.setText("Item Quantity : " +  item_quantity);
                    remaining_price.setText("Remaining price : " + item_remaining_price);


//pitching in for an item
        btnPitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Double.parseDouble(editTextPitch.getText().toString())>Double.parseDouble(item_remaining_price)){
                    Toast.makeText(ItemView.this,"Pitch in amount should be less than or equal to the remaining price", Toast.LENGTH_SHORT).show();
                }else{

                    SharedPreferences.Editor e =sharedPreferences.edit();
                    e.putFloat("credit_amount",Float.parseFloat(editTextPitch.getText().toString()));
                    e.commit();
                    startActivity(new Intent(ItemView.this,Transaction.class));
                }

            }
        });

        //buying an item
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              // startActivity(new Intent(ItemView.this,MapsActivity.class));
                if(Double.parseDouble(item_remaining_price)==Double.parseDouble(item_price))
                {
                    startActivity(new Intent(ItemView.this, Buying.class));
                }
                else {
                    Toast.makeText(ItemView.this,"Cannot buy a product that has been pitched in by anyone", Toast.LENGTH_SHORT).show();
                }

                if(ListType.equals("Public")){
                    mDatabase.child("Users").child(searched_username).child("Lists").child("Public").addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            // Get Post object and use the values to update the UI
                            if (dataSnapshot.exists()) {
                               for(DataSnapshot n: dataSnapshot.getChildren()){
                                  Item t= n.getValue(Item.class);
                                  if(t.getName().equals(item_clicked)&& t.getPrice()==Double.parseDouble(item_price) && t.getQuantity() == item_quantity){
                                      mDatabase.child("Users").child(searched_username).child("Lists").child("Public").child(n.getKey()).removeValue();
                                  }
                               }
                            }
                        }

                            @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Getting Post failed, log a message
                            Log.w(null, "loadPost:onCancelled", databaseError.toException());
                            // ...
                        }
                    });
                    //add the path
                    Gift g = new Gift(item_clicked,  Double.parseDouble(item_price), searched_username,"Public" ,item_quantity,"gs://makeawish-3b12e.appspot.com/placeholder.png");
                    mDatabase.child("Users").child(username[0]).child("itemsToBuy").push().setValue(g);

                }else{
                    mDatabase.child("Users").child(searched_username).child("Lists").child("Private").child(private_name).addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            // Get Post object and use the values to update the UI
                            if (dataSnapshot.exists()) {
                                for(DataSnapshot n: dataSnapshot.getChildren()){
                                    if(n.getKey().equals("expiration") || n.getKey().equals("name"))
                                        continue;
                                    Item t= n.getValue(Item.class);
                                    if(t.getName().equals(item_clicked)&& t.getPrice()==Double.parseDouble(item_price) && t.getQuantity() == item_quantity){
                                        mDatabase.child("Users").child(searched_username).child("Lists").child("Private").child(private_name).child(n.getKey()).removeValue();
                                    }
                                }
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Getting Post failed, log a message
                            Log.w(null, "loadPost:onCancelled", databaseError.toException());
                            // ...
                        }
                    });

                    Gift g1 = new Gift(item_clicked, Double.parseDouble(item_price), searched_username,private_name, item_quantity,sharedPreferences.getString("path","gs://makeawish-3b12e.appspot.com/images.jpg"));
                    mDatabase.child("Users").child(username[0]).child("itemsToBuy").push().setValue(g1);
                }
            }
        });
    }
}