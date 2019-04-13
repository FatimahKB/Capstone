package com.example.fatim.makeawish;


import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import java.util.List;


public class ItemsOfPrivateList extends AppCompatActivity {

    FirebaseUser user;
    public DatabaseReference mDatabase;
    ListView item_display;
    List<String> all_items_list=new ArrayList<>();
    String selected_item;
    Button back ;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_of_private_list);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        user= FirebaseAuth.getInstance().getCurrentUser();
        item_display= (ListView)findViewById(R.id.list_private);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        back = (Button)findViewById(R.id.Back) ;
        final String username =sp.getString("username","");
        //displaying the public list's items

        mDatabase.child("Users").child(username).child("Lists").child("Private").addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                all_items_list=new ArrayList<>();
                // Get Post object and use the values to update the UI
                for (DataSnapshot n : dataSnapshot.getChildren()) {
                    if (n.getKey().equals("username") || n.getKey().equals("email"))
                        continue;
                    Item item = n.getValue(Item.class);
                    all_items_list.add(item.getName());
                    ArrayAdapter<String> adapter1 = (new ArrayAdapter<String>(ItemsOfPrivateList.this, android.R.layout.simple_list_item_1,all_items_list));
                    item_display.setAdapter(adapter1);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(null, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ItemsOfPrivateList.this,FriendsWishList.class));


            }});

        item_display.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected_item  =(item_display.getItemAtPosition(position)).toString();
                sharedPreferences= PreferenceManager.getDefaultSharedPreferences(ItemsOfPrivateList.this);
                SharedPreferences.Editor e =sharedPreferences.edit();
                e.putString("clicked_item",selected_item);
                e.putLong("item_pos",position);
                e.commit();
                startActivity(new Intent(ItemsOfPrivateList.this,ItemView.class));
            }
        });
    }}

