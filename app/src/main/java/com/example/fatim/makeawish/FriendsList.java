//package com.example.fatim.makeawish;
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.preference.PreferenceManager;
//import android.support.annotation.NonNull;
//import android.support.design.widget.BottomNavigationView;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//
//public class FriendsList extends AppCompatActivity {
//    Button add;
//    TextView name;
//    public DatabaseReference mDatabase;
//    SharedPreferences sharedPreferences;
//    ArrayList<String> item_list=new ArrayList<String>();
//    ListView item_display;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_friends_list2);
//        add= (Button)findViewById(R.id.friend_add_button);
//        name=(TextView)findViewById(R.id.friends_list_name_textview);
//        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        final String username [] =user.getEmail().split("@");
//
//        Button btn_friends_list_public_button=(Button) findViewById(R.id.friends_list_public_button);
//        btn_friends_list_public_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //NOTE: SharedPreferences for public/private refreshed the view
//                startActivity(new Intent(FriendsList.this,FriendsList.class));
//            }
//        });
//
//        Button btn_friends_list_private_button=(Button) findViewById(R.id.friends_list_private_button);
//        btn_friends_list_private_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //NOTE: SharedPreferences for public/private refreshed the view
//                startActivity(new Intent(FriendsList.this,FriendsList.class));
//
//            }
//        });
//        //Navigation bar
//        BottomNavigationView bottom = findViewById(R.id.navigationView);
//
//        bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                switch (menuItem.getItemId())
//                {
//                    case R.id.navigation_history:
//                        startActivity(new Intent(FriendsList.this,History.class));break;
//                    case R.id.navigation_todo:
//                        startActivity(new Intent(FriendsList.this,ToDo.class));break;
//                    case R.id.navigation_profile:
//                        startActivity(new Intent(FriendsList.this,Profile.class));break;
//
//                    case R.id.navigation_search:
//                        startActivity(new Intent(FriendsList.this,Search.class));break;
//                    case R.id.navigation_settings:
//                        startActivity(new Intent(FriendsList.this,Settings.class));break;
//                    default:
//
//
//
//                }
//                return false;
//            }
//        });
//
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        item_display=(ListView)findViewById(R.id.friends_list_items_listview);
//
//        ////////////////////////////
//        name=findViewById(R.id.friends_list_name_textview);
//        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
//        String str_name=sharedPreferences.getString("searched_user","").trim();
//        name.setText(str_name);
//
//        mDatabase.child("Users").child(str_name).addValueEventListener( new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // Get Post object and use the values to update the UI
//                for (DataSnapshot n : dataSnapshot.getChildren()) {
//                    if (n.getKey().equals("username")|| n.getKey().equals("email"))
//                        continue;
//                    Item item = n.getValue(Item.class);
//                    item_list.add(item.getName());
//                    ArrayAdapter<String> adapter1 = (new ArrayAdapter<String>(FriendsList.this, android.R.layout.simple_list_item_1,item_list));
//                    item_display.setAdapter(adapter1);
//                }
//
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Getting Post failed, log a message
//                Log.w(null, "loadPost:onCancelled", databaseError.toException());
//                // ...
//            }
//        });
//        item_display.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String selected_item=(item_display.getItemAtPosition(position)).toString();
//                SharedPreferences.Editor e =sharedPreferences.edit();
//                e.putString("clicked_item",selected_item);
//                e.putLong("item_pos",position);
//                e.commit();
//                startActivity(new Intent(FriendsList.this,item_view.class));
//            }
//        });
//
//
//        final DatabaseReference finalMDatabase = mDatabase;
//        add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finalMDatabase.child("Users").child(name.getText().toString().trim()).child("friendRequests").push().setValue(username[0]);
//                Toast.makeText(FriendsList.this,"A request has been successfully sent", Toast.LENGTH_SHORT).show();
//
//            }
//
//        });
//
//
//        // Get Post object and use the values to update the UI
////                Toast.makeText(AddingItem.this,"here "+(mDatabase.child("Users").child(username1).child("Private").child("Birthday")), Toast.LENGTH_SHORT).show();
////                for (DataSnapshot n : dataSnapshot.getChildren()) {
//////                    Toast.makeText(AddingItem.this,"I'm here", Toast.LENGTH_SHORT).show();
////                        lists.add(n.child("name").getValue().toString());
////                    }
//
//
//
//    }
//}
