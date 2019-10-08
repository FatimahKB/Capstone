package com.example.fatim.makeawish;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class History extends AppCompatActivity {

    public DatabaseReference mDatabase;
    ListView items_list;
    ArrayList<BoughtHistory> all_items_list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        items_list = findViewById(R.id.history_listview);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        CustomAdapter customAdapter = new CustomAdapter();
        items_list.setAdapter(customAdapter);

        String username []=FirebaseAuth.getInstance().getCurrentUser().getEmail().split("@");
        mDatabase.child("Users").child(username[0]).child("History").addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                all_items_list=new ArrayList<>();
                // Get Post object and use the values to update the UI
                for (DataSnapshot n : dataSnapshot.getChildren()) {

                    BoughtHistory bh = n.getValue(BoughtHistory.class);
                    all_items_list.add(bh);
//                    ArrayAdapter<String> adapter1 = (new ArrayAdapter<String>(History.this, android.R.layout.simple_list_item_1,all_items_list));
//                    items_list.setAdapter(adapter1);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(null, "loadPost:onCancelled", databaseError.toException());
                // ...
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
                        startActivity(new Intent(History.this,History.class));break;
                    case R.id.navigation_todo:
                        startActivity(new Intent(History.this,ToDo.class));break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(History.this,Profile.class));break;

                    case R.id.navigation_search:
                        startActivity(new Intent(History.this,Search.class));break;
                    case R.id.navigation_settings:
                        startActivity(new Intent(History.this,Signout.class));break;
                    default:



                }
                return false;
            }
        });

    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return all_items_list.size();
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
        public View getView(int position, View convertView, ViewGroup parent) {

            final View view = getLayoutInflater().inflate(R.layout.custom_history, null);
            final TextView history_info = view.findViewById(R.id.history_item_info);
            TextView history_price = view.findViewById(R.id.history_item_price);

            //     mImageView.setImageResource(images[position]);
            history_info.setText("Item Bought : "+all_items_list.get(position).getItem_name()+"\nUser Gifted : "+all_items_list.get(position).gifted_user);

            history_price.setText("Price : "+all_items_list.get(position).getItem_price()+"\nDate Bought : "+all_items_list.get(position).date_bought);

            return view;
        }

    }
}
