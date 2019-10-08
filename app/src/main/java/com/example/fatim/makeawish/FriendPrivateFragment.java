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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FriendPrivateFragment extends Fragment {
    //Controllers
    List<String> all_items_list2=new ArrayList<>();
    ListView item_display2;
    String selected_item;

    //Database
    SharedPreferences sharedPreferences;
    public DatabaseReference mDatabase;
    public ArrayList<String> friend_list=new ArrayList<String>();
    Boolean isFriend=false;
    FirebaseUser user;
    String username[];
    TextView name;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.friend_private_layout, viewGroup, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        name=(TextView)view.findViewById(R.id.friendprivate_name_text);
        //setting the controllers
        item_display2 = (ListView) view.findViewById(R.id.friend_Private_ListView);
        final String searched_username = sharedPreferences.getString("chosenUser", "").trim();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        username = user.getEmail().split("@");
        String str_name = sharedPreferences.getString("chosenUser", "").trim();
        name.setText(str_name);
        mDatabase.child("Users").child(username[0]).child("friends").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String all_friends = dataSnapshot.getValue(String.class);//.toString();
                    friend_list = new ArrayList<String>(Arrays.asList(all_friends.split(",")));
                    for (int i = 0; i < friend_list.size(); i++) {
                        if (friend_list.get(i).equals(searched_username.trim())) {
                            isFriend = true;
                            break;
                        }
                    }
                    Log.d("friend?",""+isFriend);

                }
                // Toast.makeText(FriendsWishList.this,"friend : the searched user is :"+friend_list.get(0), Toast.LENGTH_LONG).show();
            }

            @Override

            public void onCancelled(DatabaseError databaseError) {

                // Getting Post failed, log a message

                Log.w(null, "loadPost:onCancelled", databaseError.toException());

                // ...
            }

        });

        //getting all private lists
        mDatabase.child("Users").child(searched_username).child("Lists").child("Private").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                all_items_list2 = new ArrayList<>();
                // Get Post object and use the values to update the UI
                for (DataSnapshot n : dataSnapshot.getChildren()) {
                    Item item = n.getValue(Item.class);
                    all_items_list2.add(item.getName());
                }
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, new ArrayList<String>());
                item_display2.setAdapter(adapter2);
                if(isFriend) {
                    adapter2 = (new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, all_items_list2));
                    item_display2.setAdapter(adapter2);
                }
                Log.d("ff",""+getContext().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(null, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });

        //displaying the private list's items
        item_display2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected_list = (item_display2.getItemAtPosition(position)).toString().trim();
                SharedPreferences.Editor r = sharedPreferences.edit();
                r.putString("listType", "Private");
                r.putString("selected_private_list", selected_list);
                r.commit();

                startActivity(new Intent(getActivity(), ItemsOfPrivateList.class));
            }
        });

        return view;

    }
}
