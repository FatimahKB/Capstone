package com.example.fatim.makeawish;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

public class users_item_view extends AppCompatActivity {
    TextView name;
    TextView quantity;
    TextView price;
    SharedPreferences sharedPreferences;
    Button delete;
    public DatabaseReference mDatabase;
    String username[];
    User user;
    Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_item_view);
        name = (TextView) findViewById(R.id.textViewItemName);
        quantity = (TextView) findViewById(R.id.textViewQuantity);
        price = (TextView) findViewById(R.id.textViewPrice);
        delete = (Button) findViewById(R.id.usersItems_deleteItem_Button);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String item_clicked = sharedPreferences.getString("clicked_item", "").trim();
        final String item_price = sharedPreferences.getString("price", "").trim();
        final String item_remaining_price = sharedPreferences.getString("remaining_price", "").trim();
        final int item_quantity = sharedPreferences.getInt("quantity", 1);
        final String ListType=sharedPreferences.getString("listType","").trim();
        final String private_name=sharedPreferences.getString("selected_private_list","").trim();

        name.setText(item_clicked);
        quantity.setText(item_price);
        price.setText(item_quantity+"");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        username = user.getEmail().split("@");

        final ImageView img =findViewById(R.id.item_details_image);
        FirebaseStorage storage= FirebaseStorage.getInstance();
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
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 item = new Item(item_clicked, item_quantity, Double.parseDouble(item_price), Double.parseDouble(item_remaining_price));

                if(ListType.equals("Public")) {
                    mDatabase.child("Users").child(username[0]).child("Lists").child("Public").addValueEventListener(new ValueEventListener() {
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot n : dataSnapshot.getChildren()) {
                                    Item item1= n.getValue(Item.class);
                                    if(item.getName().equals(item1.getName())){
                                        mDatabase.child("Users").child(username[0]).child("Lists").child("Public").child(n.getKey()).removeValue();
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
                }else{
                    mDatabase.child("Users").child(username[0]).child("Lists").child("Private").child(private_name).addValueEventListener(new ValueEventListener() {
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot n : dataSnapshot.getChildren()) {
                                    Item item1= n.getValue(Item.class);
                                    if(item.getName().equals(item1.getName())){
                                        mDatabase.child("Users").child(username[0]).child("Lists").child("Private").child(private_name).child(n.getKey()).removeValue();
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
                }
            }
        });
    }
}