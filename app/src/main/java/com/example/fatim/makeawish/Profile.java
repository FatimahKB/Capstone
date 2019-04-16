package com.example.fatim.makeawish;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Profile extends AppCompatActivity {
    public DatabaseReference mDatabase;
    FirebaseUser user;
    ListView items_list;
    ArrayList<String> all_items_list=new ArrayList<>();
    Button private_list;
    Button public_list;
    Button profile;
    Button add;
    TextView v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        items_list= (ListView)findViewById(R.id.Profile_publicItems_ListView);
        private_list=(Button)findViewById(R.id.profile_private_button);
        public_list=(Button)findViewById(R.id.profile_public_button);
        add=(Button)findViewById(R.id.profile_add_button);
        v=(TextView) findViewById(R.id.textView4Test);
        profile=(Button)findViewById(R.id.button2);
        //displaying the public list's items
        user= FirebaseAuth.getInstance().getCurrentUser();
        String username []=user.getEmail().split("@");
        mDatabase = FirebaseDatabase.getInstance().getReference();

        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        String username1=sharedPreferences.getString("username","");
        v.setText(username1);

        mDatabase.child("Users").child(username[0]).child("Lists").child("Public").addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                all_items_list=new ArrayList<>();
                // Get Post object and use the values to update the UI
                for (DataSnapshot n : dataSnapshot.getChildren()) {
                    if (n.getKey().equals("username") || n.getKey().equals("email"))
                        continue;
                    Item item = n.getValue(Item.class);
                    all_items_list.add(item.getName());
                    ArrayAdapter<String> adapter1 = (new ArrayAdapter<String>(Profile.this, android.R.layout.simple_list_item_1,all_items_list));
                    items_list.setAdapter(adapter1);

                }
                Toast.makeText(Profile.this, "bye"+all_items_list.size(), Toast.LENGTH_SHORT).show();


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(null, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(Profile.this,AddingItem.class));

            }
        });

        private_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent (Profile.this, PrivateWishlists.class));
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent (Profile.this, MapsActivity.class));
            }
        });
    }
}
