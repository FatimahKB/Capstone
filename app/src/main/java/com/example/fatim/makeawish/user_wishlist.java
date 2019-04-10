package com.example.fatim.makeawish;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class user_wishlist extends AppCompatActivity {
    Button add;
    ListView list;
    ArrayList<String> all_items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_user_wishlist);
        list=(ListView)findViewById(R.id.private_user_Wishlist_list);
        add=(Button)findViewById(R.id.userwishlist_add_button);
        all_items=new ArrayList<>();
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String username [] =user.getEmail().split("@");
        Log.d("hi",username[0]+"");

        mDatabase.child("Users").child(username[0]).child("Lists").child("Private").child("Birthday").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot n : dataSnapshot.getChildren()) {
                    if (n.getKey().equals("name") )
                        continue;
                    Item item = n.getValue(Item.class);
                    all_items.add(item.getName());

                }
                ArrayAdapter<String> adapter = (new ArrayAdapter<String>(user_wishlist.this, android.R.layout.simple_list_item_1,all_items));
                list.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(user_wishlist.this,AddingItem.class));            }
        });
    }
}
