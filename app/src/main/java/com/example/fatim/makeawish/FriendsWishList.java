package com.example.fatim.makeawish;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FriendsWishList extends AppCompatActivity {
    //Controllers
    Button private_list;
    TextView name;
//    ListView item_display;
//    List<String> all_items_list=new ArrayList<>();
//    Button public_list;
//    String selected_item;

    //Database
    public DatabaseReference mDatabase;

    int ifi;
    public ArrayList<String> friend_list=new ArrayList<String>();
    SharedPreferences sharedPreferences;

    //img
    ImageView profileImg;
    FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_wish_list);

        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(FriendsWishList.this);

        //setting the tabs
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
//        setSupportActionBar(toolbar);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Public"));
        tabLayout.addTab(tabLayout.newTab().setText("Private"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        String str_name=sharedPreferences.getString("chosenUser","").trim();
        TabsAdapterFriend tabsAdapter = new TabsAdapterFriend(getSupportFragmentManager(), tabLayout.getTabCount());
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


        //img

        profileImg= (ImageView) findViewById(R.id.friends_wish_list_profile_imageview);
        storage=FirebaseStorage.getInstance();
        Log.d("hi",str_name);
        final StorageReference storageRef = storage.getReferenceFromUrl("gs://makeawish-3b12e.appspot.com/profilepictures/"+str_name);
        final long ONE_MEGABYTE = 1024 * 1024;
        storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {

            @Override

            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                profileImg.setImageBitmap(bitmap);
//                        img.setWidth(50);
//                        img.setMaxHeight(50);
            }

        });
    }}
