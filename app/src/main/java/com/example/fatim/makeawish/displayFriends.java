package com.example.fatim.makeawish;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    }
}
