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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class FriendsWishList extends AppCompatActivity {




    public DatabaseReference mDatabase;
    ListView item_display;
    List<String> all_items_list=new ArrayList<>();
    Button private_list;
    Button public_list;
    TextView name;
    String selected_item;
    int ifi;
    public ArrayList<String> friend_list=new ArrayList<String>();
    SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_wish_list);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        item_display= (ListView)findViewById(R.id.friends_wish_list_items_listview);
        private_list=(Button)findViewById(R.id.friends_wish_list_private_button);
        public_list=(Button)findViewById(R.id.friends_wish_list_public_button);
        name=findViewById(R.id.friends_wish_list_name_textview);
//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
//        final String searched_username =sp.getString("searched_user","");

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String searched_username=sharedPreferences.getString("friends","").trim();

        name.setText(searched_username);

//            SharedPreferences.Editor e;
//            e = SharedPreferences.edit();
//            e.putString("clicked_item",selected_item);
//            e.putLong("item_pos",ifi);
//            sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
//
//            SharedPreferences.Editor e =sharedPreferences.edit();
//            e.putString("clicked_item",selected_item);
//            e.putLong("item_pos",position);
//            e.commit();
        mDatabase.child("Users").child(searched_username).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot n : dataSnapshot.getChildren()){

                }
                //               String all_friends=dataSnapshot.getValue().toString();
                //          friend_list =new ArrayList<String>(Arrays.asList(all_friends.split(",")));
                Toast.makeText(FriendsWishList.this,"friend : the searched user is :"+searched_username, Toast.LENGTH_LONG).show();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(null, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });

        ///////////////////////////////////////////////////////////////////////
        public_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("Users").child(searched_username).child("Lists").child("Public").addValueEventListener( new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        all_items_list=new ArrayList<>();
                        // Get Post object and use the values to update the UI
                        for (DataSnapshot n : dataSnapshot.getChildren()) {
                            if (n.getKey().equals("name"))
                                continue;
                            Item item = n.getValue(Item.class);
                            all_items_list.add(item.getName());
                            ArrayAdapter<String> adapter1 = (new ArrayAdapter<String>(FriendsWishList.this, android.R.layout.simple_list_item_1,all_items_list));
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
/////////////////////////////////////
                //public
                item_display.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selected_item=(item_display.getItemAtPosition(position)).toString();
                        mDatabase.child("Users").child(searched_username).child("Lists").child("Public").addValueEventListener( new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                // Get Post object and use the values to update the UI
                                for (DataSnapshot n : dataSnapshot.getChildren()) {
                                    if (n.getKey().equals("name"))
                                        continue;
                                    Item item = n.getValue(Item.class);
                                    if (item.getName().equals(selected_item)){
                                        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(FriendsWishList.this);
                                        SharedPreferences.Editor e =sharedPreferences.edit();
                                        e.putString("clicked_item",selected_item);
                                        e.putString("price",item.getPrice()+"");
                                        e.putInt("quantity",item.getQuantity());
                                        e.putString("remaining_price",item.getRemaining_price()+"");
                                        e.putString("listType","Public");
                                        e.commit();
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
//                        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(FriendsWishList.this);
//                        SharedPreferences.Editor e =sharedPreferences.edit();
//                        e.putString("clicked_item",selected_item);
//                        e.putString("listType","public");
//                        e.putLong("item_pos",position);
//                        e.commit();
                        startActivity(new Intent(FriendsWishList.this,ItemView.class));
                    }
                });
/////////////////////////////////
            }

        });


//            item_display.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    selected_item=(item_display.getItemAtPosition(position)).toString();
//                    sharedPreferences= PreferenceManager.getDefaultSharedPreferences(FriendsWishList.this);
//                    SharedPreferences.Editor e =sharedPreferences.edit();
//                    e.putString("clicked_item",selected_item);
//                    e.putLong("item_pos",position);
//                    e.commit();
//                    startActivity(new Intent(FriendsWishList.this,ItemView.class));
//                }
//            });


        private_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//check
                mDatabase.child("Users").child(searched_username).child("Lists").child("Private").addValueEventListener( new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        all_items_list=new ArrayList<>();
                        // Get Post object and use the values to update the UI
                        for (DataSnapshot n : dataSnapshot.getChildren()) {
                            if (n.getKey().equals("username") || n.getKey().equals("email"))
                                continue;
                            Item item = n.getValue(Item.class);
                            all_items_list.add(item.getName());
                            ArrayAdapter<String> adapter1 = (new ArrayAdapter<String>(FriendsWishList.this, android.R.layout.simple_list_item_1,all_items_list));
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
                item_display.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selected_list=(item_display.getItemAtPosition(position)).toString();
                        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(FriendsWishList.this);
                        Toast.makeText(FriendsWishList.this," and the searched list is "+selected_item, Toast.LENGTH_LONG).show();

                        SharedPreferences.Editor e =sharedPreferences.edit();
                        e.putString("selected_private_list",selected_list);
                        e.putLong("item_pos",position);
                        e.putString("listType","private");
                        e.commit();
                        startActivity(new Intent(FriendsWishList.this,ItemsOfPrivateList.class));
                    }
                });
            }
        });
//                    boolean isFriend=false;
//                    for(int i =0; i<friend_list.size(); i++)
//                    {
//                        if (friend_list.get(i).equals(searched_username.trim())) {
//                            isFriend = true;
//                            break;
//                        }
//                    }
//                    if(isFriend) {
//
//                    }else
//                        Toast.makeText(FriendsWishList.this, "You are not friends!", Toast.LENGTH_LONG).show();

    }}








