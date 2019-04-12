package com.example.fatim.makeawish;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class SearchUserFragment extends Fragment {

    public DatabaseReference mDatabase;
    public ArrayList<String> all_users=new ArrayList<String>();
    EditText search;
    public ArrayList<String> users=new ArrayList<String>();
    Button search_charity_btn;
    Button search_item_btn;
    ListView search_list;
    TextView k;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState){
     //   return inflater.inflate(R.layout.search_users_layout, viewGroup, false);
final View view = inflater.inflate(R.layout.search_users_layout, viewGroup, false);
        Button userSearch =(Button) view.findViewById(R.id.userSearchbtn);
//        search=(EditText)findViewById(R.id.search_searchBar_editText);
        final ListView  search_list=(ListView) view.findViewById(R.id.searchuser_result_listView);

        search=(EditText)view.findViewById(R.id.search_searchBar_editText);


        //copied
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    String str = postSnapshot.getKey();
                    all_users.add(str);
//                    if(postSnapshot.hasChild("ischarity")) {
//                        String isCharity = postSnapshot.child("ischarity").getValue(String.class);
//                        if (isCharity.equals("true"))
//                            all_charities.add(str);
//                    }
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(null, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });


        //end
        userSearch.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

            users = new ArrayList<String>();
            for(String lala: all_users){
                if (lala.contains(search.getText().toString())) {
                    users.add(lala);}
                ArrayAdapter<String> adapter1 = (new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,users));
                search_list.setAdapter(adapter1);
            }
            users = new ArrayList<String>();
    }
});
        search_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected_user=(search_list.getItemAtPosition(position)).toString();
                Toast.makeText(getContext(),"toast 1 the searched user is :"+selected_user,Toast.LENGTH_LONG).show();
                SharedPreferences.Editor e =sharedPreferences.edit();
                e.putString("searched_user",selected_user);
                e.commit();
                Toast.makeText(getContext(),"toast 2 the searched user is :"+selected_user,Toast.LENGTH_LONG).show();

                startActivity(new Intent(getContext(),History.class));
            }
        });

        return view;
    }
}
