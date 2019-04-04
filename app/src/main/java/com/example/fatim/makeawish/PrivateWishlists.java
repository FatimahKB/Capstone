package com.example.fatim.makeawish;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PrivateWishlists extends AppCompatActivity {
    public DatabaseReference mDatabase;
    ListView private_lists;
    FirebaseUser user;
    ArrayList<String> all_private_list=new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_wishlists);
        private_lists= (ListView)findViewById(R.id.PrivateWishlists_privateLists_ListView);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        user= FirebaseAuth.getInstance().getCurrentUser();
        String username []=user.getEmail().split("@");


        mDatabase.child("Users").child(username[0]).child("Lists").child("Private").addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                all_private_list=new ArrayList<>();
                // Get Post object and use the values to update the UI
                for (DataSnapshot n : dataSnapshot.getChildren()) {
                    if (n.getKey().equals("username") || n.getKey().equals("email"))
                        continue;
                    PrivateWishlist item = n.getValue(PrivateWishlist.class);
                    all_private_list.add(item.getName());
                    ArrayAdapter<String> adapter1 = (new ArrayAdapter<String>(PrivateWishlists.this, android.R.layout.simple_list_item_1,all_private_list));
                    private_lists.setAdapter(adapter1);

                }
                Toast.makeText(PrivateWishlists.this, "bye"+all_private_list.size(), Toast.LENGTH_SHORT).show();


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(null, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });

        ImageButton btn_imageButton =(ImageButton) findViewById(R.id.imageButton);
        btn_imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PrivateWishlists.this,createPrivateList.class));

            }
        });
    }
}
