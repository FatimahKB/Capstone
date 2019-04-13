package com.example.fatim.makeawish;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment; import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
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

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PrivateListsFragment extends Fragment{
    public DatabaseReference mDatabase;
    ListView privateLists;
    FirebaseUser user;
    ArrayList<String> all_private_list;
    TextView friendsNumberText;
    ListView items_list;
    ArrayList<String> all_items_list = new ArrayList<>();
    TextView t;
    String friends;
    int friendsNumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.privatelistslayout, viewGroup, false);
        t = (TextView) view.findViewById(R.id.textView);
        privateLists = (ListView) view.findViewById(R.id.lliisstt);
        friendsNumberText=(TextView) view.findViewById(R.id.Private_FriendsNumber_TextView);
       // privateLists = (ListView) view.findViewById(R.id.Profile_publicItems_ListView);
        //displaying the public list's items
        user = FirebaseAuth.getInstance().getCurrentUser();
        String username[] = user.getEmail().split("@");
        mDatabase = FirebaseDatabase.getInstance().getReference();


        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("Users").child(username[0]).child("Lists").child("Private").addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                all_private_list=new ArrayList<>();
                // Get Post object and use the values to update the UI
                for (DataSnapshot n : dataSnapshot.getChildren()) {
                    if (n.getKey().equals("username") || n.getKey().equals("email"))
                        continue;
                    PrivateWishlist item = n.getValue(PrivateWishlist.class);
                    all_private_list.add(item.getName());
                    ArrayAdapter<String> adapter2 = (new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1,all_private_list));
                    privateLists.setAdapter(adapter2);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(null, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });

        privateLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = privateLists.getItemAtPosition(position).toString();
//                t = (TextView) getActivity().findViewById(R.id.textView);
                t.setText("Hey there" + s);
            }
        });
        mDatabase.child("Users").child(username[0]).child("friends").addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                friends=dataSnapshot.getValue(String.class);
                for(int i=0;i<friends.length();i++){
                    if(friends.charAt(i)== ','){
                        friendsNumber++;
                    }
                }
                friendsNumberText.setText(friendsNumber+1+" friends");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(null, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });
//
        friendsNumberText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent ( getActivity(),displayFriends.class));
            }
        });

        return  view;
   }
}
