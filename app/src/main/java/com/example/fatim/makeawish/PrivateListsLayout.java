package com.example.fatim.makeawish;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment; import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class PrivateListsLayout extends Fragment{
    public DatabaseReference mDatabase;
    ListView privateLists;
    FirebaseUser user;
    ArrayList<String> all_private_list;

    ListView items_list;
    ArrayList<String> all_items_list = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.publiclist, viewGroup, false);

//        privateLists = (ListView) view.findViewById(R.id.lliisstt);
        privateLists = (ListView) view.findViewById(R.id.Profile_publicItems_ListView);
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
        return  view;
   }
}
