package com.example.fatim.makeawish;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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
        //Navigation bar
        BottomNavigationView bottom = findViewById(R.id.navigationView);

        bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.navigation_history:
                        startActivity(new Intent(Profile.this,History.class));break;
                    case R.id.navigation_todo:
                        startActivity(new Intent(Profile.this,ToDo.class));break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(Profile.this,Profile.class));break;

                    case R.id.navigation_search:
                        startActivity(new Intent(Profile.this,Search.class));break;
                    case R.id.navigation_settings:
                        startActivity(new Intent(Profile.this,Settings.class));break;
                    default:



                }
                return false;
            }
        });

    }
}
