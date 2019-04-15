package com.example.fatim.makeawish;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FriendPublicFragment extends Fragment {
    SharedPreferences sharedPreferences;
    public DatabaseReference mDatabase;
    List<String> all_items_list=new ArrayList<>();
    ListView item_display;
    String selected_item;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.friend_public_layout, viewGroup, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mDatabase = FirebaseDatabase.getInstance().getReference();
        item_display= (ListView)view.findViewById(R.id.Friend_Public_ListView);

        final String searched_username=sharedPreferences.getString("friends","").trim();
        Log.d("hi",""+searched_username);
        mDatabase.child("Users").child(searched_username).child("Lists").child("Public").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                all_items_list = new ArrayList<>();
                // Get Post object and use the values to update the UI
                for (DataSnapshot n : dataSnapshot.getChildren()) {
                    if (n.getKey().equals("name"))
                        continue;
                    Item item = n.getValue(Item.class);
                    SharedPreferences.Editor e =sharedPreferences.edit();
                    if(item.imgPath!=null) {
                        e.putString("path", item.getImgPath());
                        Toast.makeText(getContext(),item.getImgPath(),Toast.LENGTH_LONG).show();
                        e.commit();
                    }
                    all_items_list.add(item.getName());
                    ArrayAdapter<String> adapter1 = (new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, all_items_list));
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
                selected_item = (item_display.getItemAtPosition(position)).toString();
                mDatabase.child("Users").child(searched_username).child("Lists").child("Public").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // Get Post object and use the values to update the UI
                            for (DataSnapshot n : dataSnapshot.getChildren()) {
                                if (n.getKey().equals("name"))
                                    continue;
                                Item item = n.getValue(Item.class);
                                if (item.getName().equals(selected_item)) {
                                    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                    SharedPreferences.Editor e = sharedPreferences.edit();
                                    e.putString("clicked_item", selected_item);
                                    e.putString("price", item.getPrice() + "");
                                    e.putInt("quantity", item.getQuantity());
                                    e.putString("remaining_price", item.getRemaining_price() + "");
                                    e.putString("listType", "Public");
                                    if(item.imgPath!=null) {
                                        e.putString("path", item.imgPath);
                                    }
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
                startActivity(new Intent(getActivity(), ItemView.class));
            }
        });
        return view;
    }}

