package com.example.fatim.makeawish;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

public class FriendPrivateFragment extends Fragment {
    SharedPreferences sharedPreferences;
    public DatabaseReference mDatabase;
    List<String> all_items_list=new ArrayList<>();
    ListView item_display;
    String selected_item;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.friend_private_layout, viewGroup, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        item_display= (ListView)view.findViewById(R.id.friend_Private_ListView);
        final String searched_username=sharedPreferences.getString("chosenUser","").trim();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Users").child(searched_username).child("Lists").child("Private").addValueEventListener( new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        all_items_list=new ArrayList<>();
                        // Get Post object and use the values to update the UI
                        for (DataSnapshot n : dataSnapshot.getChildren()) {
//                            if (n.getKey().equals("username") || n.getKey().equals("email"))
//                                continue;
                            Item item = n.getValue(Item.class);
                            all_items_list.add(item.getName());
                            ArrayAdapter<String> adapter1 = (new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,all_items_list));
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
                        Toast.makeText(getActivity()," and the searched list is "+selected_item, Toast.LENGTH_LONG).show();
                        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getActivity());
                        SharedPreferences.Editor r =sharedPreferences.edit();
                        r.putString("listType","Private");
                        r.putString("selected_private_list",selected_list);
                        r.commit();

//                        e.putLong("item_pos",position);
//                        e.putString("listType","private");
//                        e.commit();
                        startActivity(new Intent(getActivity(),ItemsOfPrivateList.class));
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
        return view;

    }
}
