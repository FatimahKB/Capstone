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
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ToDo extends AppCompatActivity {
    ListView mlistView;
    DatabaseReference mDatabase;
    int [] images = {R.drawable.download,R.drawable.add,R.drawable.bluecursor};
    ArrayList<String> Names = new ArrayList<>();
    String username[];
    FirebaseUser user;
    String friends="";
    TextView mTitle;
    Object names1[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Button to_do_buy_button = findViewById(R.id.to_do_buy_button);

        user = FirebaseAuth.getInstance().getCurrentUser();
        username=user.getEmail().split("@");
        mlistView = findViewById(R.id.listView);

        mDatabase.child("Users").child(username[0]).child("friendRequests").addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot n : dataSnapshot.getChildren()) {
                    Names.add(n.getValue().toString());
                }
                CustomAdapter customAdapter = new CustomAdapter(Names);
                mlistView.setAdapter(customAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(null, "loadPost:onCancelled", databaseError.toException());
                // ...
            }


        });

        Button to_do_cancel_button = findViewById(R.id.to_do_cancel_button);


        to_do_buy_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ToDo.this, ToDo.class));
            }
        });


        to_do_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ToDo.this, ToDo.class));
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
                        startActivity(new Intent(ToDo.this,History.class));break;
                    case R.id.navigation_todo:
                        startActivity(new Intent(ToDo.this,ToDo.class));break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(ToDo.this,Profile.class));break;

                    case R.id.navigation_search:
                        startActivity(new Intent(ToDo.this,Search.class));break;
                    case R.id.navigation_settings:
                        startActivity(new Intent(ToDo.this,Settings.class));break;
                    default:
                }
                return false;
            }
        });
    }

    class CustomAdapter extends BaseAdapter {
    ArrayList<String> r=new ArrayList<>();

        CustomAdapter(ArrayList r){
            this.r=r;
        }

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView( int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.customlayout,null);
            ImageView mImageView = view.findViewById(R.id.imageView);
            mTitle = view.findViewById(R.id.title);
            names1=r.toArray();
            Button mAccept= view.findViewById(R.id.button);
            mAccept.setTag(position);
            mImageView.setImageResource(images[position]);
            mTitle.setText(names1[position].toString());
            return view;
        }
    }
    public void myClickHandler(View v){

        final int position=(Integer)v.getTag();
        mDatabase.child("Users").child(username[0]).child("friends").addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Log.d("hi",friends+" ");
                    friends = dataSnapshot.getValue(String.class);
                    mDatabase.child("Users").child(username[0]).child("friends").setValue(friends+", "+names1[position]);
                }else{
                    mDatabase.child("Users").child(username[0]).child("friends").setValue(names1[position]);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(null, "loadPost:onCancelled", databaseError.toException());
                // ...
            }

        });
        mDatabase.child("Users").child(username[0]).child("friendRequests").addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot n : dataSnapshot.getChildren()) {
                    if (n.getValue().equals(names1[position])) {
                        mDatabase.child("Users").child(username[0]).child("friendRequests").child(n.getKey()).removeValue();
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


