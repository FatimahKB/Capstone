package com.example.fatim.makeawish;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
public class Search extends AppCompatActivity {

    public DatabaseReference mDatabase;
    public ArrayList<String> all_users=new ArrayList<String>();
    public ArrayList<String> all_items=new ArrayList<String>();
    public ArrayList<String> all_charities=new ArrayList<String>();
    public ArrayList<String> users=new ArrayList<String>();
    Button search_user_btn;
    Button search_charity_btn;
    Button search_item_btn;
    ListView search_list;
    TextView k;
    EditText search;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //copied
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
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

        mDatabase.child("Items").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    String str = postSnapshot.getKey();
                    all_items.add(str);
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(null, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });


        //end of copied

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
//        setSupportActionBar(toolbar);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layoutSearch);
        tabLayout.addTab(tabLayout.newTab().setText("Users"));
        tabLayout.addTab(tabLayout.newTab().setText("Items"));
        tabLayout.addTab(tabLayout.newTab().setText("Charities"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        TabsAdapterSearch tabsAdapter = new TabsAdapterSearch(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(tabsAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

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
                        startActivity(new Intent(Search.this,History.class));break;
                    case R.id.navigation_todo:
                        startActivity(new Intent(Search.this,ToDo.class));break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(Search.this,Profile.class));break;

                    case R.id.navigation_search:
                        startActivity(new Intent(Search.this,Search.class));break;
                    case R.id.navigation_settings:
                        startActivity(new Intent(Search.this,Signout.class));break;
                    default:



                }
                return false;
            }
        });

    }
}
