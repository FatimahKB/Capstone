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
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class displayFriends extends AppCompatActivity {
    public DatabaseReference mDatabase;
    FirebaseUser user;
    String all_friends;
    String[] friends;
    ListView mlistView;
    SharedPreferences sharedPreferences;

    String friends1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_friends);
        mlistView=(ListView)findViewById(R.id.displayFriends_friends_listview);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        user= FirebaseAuth.getInstance().getCurrentUser();
        String username []=user.getEmail().split("@");


        mDatabase.child("Users").child(username[0]).child("friends").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                all_friends=dataSnapshot.getValue(String.class);
                friends=all_friends.split(",");
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(displayFriends.this, android.R.layout.simple_list_item_1,friends);
                mlistView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(null, "loadPost:onCancelled", databaseError.toException());
                // ...
            }});

        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                friends1=(mlistView.getItemAtPosition(position)).toString();
                sharedPreferences= PreferenceManager.getDefaultSharedPreferences(displayFriends.this);
                SharedPreferences.Editor e =sharedPreferences.edit();
                e.putString("friends",friends1);
                e.commit();
                startActivity(new Intent(displayFriends.this,FriendsWishList.class));
            }
        });
    }
}
