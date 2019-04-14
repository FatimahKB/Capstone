package com.example.fatim.makeawish;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Profile extends AppCompatActivity {
    public DatabaseReference mDatabase;
    FirebaseUser user;
    ListView items_list;
    ArrayList<String> all_items_list=new ArrayList<>();
    Button private_list;
    Button public_list;
    Button profile;
    Button friend;
    Button add;
    TextView friendsNumberText;
    TextView usernameText;
    String friends;
    int friendsNumber;
//img
    ImageView profileImg;
    FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        items_list= (ListView)findViewById(R.id.Profile_publicItems_ListView);
//        private_list=(Button)findViewById(R.id.profile_private_button);
//        public_list=(Button)findViewById(R.id.profile_public_button);
     // friend = (Button)findViewById(R.id.friend1);
//        add=(Button)findViewById(R.id.profile_add_button);
     //   friendsNumberText=(TextView)findViewById(R.id.Profile_FriendsNumber_TextView);
        usernameText=(TextView)findViewById(R.id.profile_username_textView);
        //displaying the public list's items
        user= FirebaseAuth.getInstance().getCurrentUser();
        String username []=user.getEmail().split("@");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        usernameText.setText(username[0]);
        //img
        profileImg= (ImageView) findViewById(R.id.profile_profilepicture_imageView);
        storage=FirebaseStorage.getInstance();
        Log.d("hi",username[0]);
        final StorageReference storageRef = storage.getReferenceFromUrl("gs://makeawish-3b12e.appspot.com/profilepictures/"+username[0]);
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



//        mDatabase.child("Users").child(username[0]).child("Lists").child("Public").addValueEventListener( new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                all_items_list=new ArrayList<>();
//                // Get Post object and use the values to update the UI
//                for (DataSnapshot n : dataSnapshot.getChildren()) {
//                    if (n.getKey().equals("username") || n.getKey().equals("email"))
//                        continue;
//                    Item item = n.getValue(Item.class);
//                    all_items_list.add(item.getName());
//                    ArrayAdapter<String> adapter1 = (new ArrayAdapter<String>(Profile.this, android.R.layout.simple_list_item_1,all_items_list));
//                    items_list.setAdapter(adapter1);
//                }
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Getting Post failed, log a message
//                Log.w(null, "loadPost:onCancelled", databaseError.toException());
//                // ...
//            }
//        });
//
//        mDatabase.child("Users").child(username[0]).child("friends").addListenerForSingleValueEvent( new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                friends=dataSnapshot.getValue(String.class);
//                for(int i=0;i<friends.length();i++){
//                    if(friends.charAt(i)== ','){
//                        friendsNumber++;
//                    }
//                }
//                friendsNumberText.setText(friendsNumber+1+" friends");
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Getting Post failed, log a message
//                Log.w(null, "loadPost:onCancelled", databaseError.toException());
//                // ...
//            }
//        });
//
//        friendsNumberText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Profile.this,displayFriends.class));
//            }
//        });
//
//
//        add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                    startActivity(new Intent(Profile.this,AddingItem.class));
//
//            }
//        });
//
//        friend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Profile.this,FriendsWishList.class));
//
//            }
//        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
//        setSupportActionBar(toolbar);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Public"));
        tabLayout.addTab(tabLayout.newTab().setText("Private"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        TabsAdapterProfile tabsAdapter = new TabsAdapterProfile(getSupportFragmentManager(), tabLayout.getTabCount());
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
                        startActivity(new Intent(Profile.this,History.class));break;
                    case R.id.navigation_todo:
                        startActivity(new Intent(Profile.this,ToDo.class));break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(Profile.this,Profile.class));break;

                    case R.id.navigation_search:
                        startActivity(new Intent(Profile.this,Search.class));break;
                    case R.id.navigation_settings:
                        startActivity(new Intent(Profile.this,Settings.class));break;
                    default:



                }
                return false;
            }
        });


    }

}
