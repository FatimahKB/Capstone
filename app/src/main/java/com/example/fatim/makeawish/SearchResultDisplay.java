//package com.example.fatim.makeawish;
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.preference.PreferenceManager;
//import android.support.design.widget.BottomNavigationView;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//
//
//public class SearchResultDisplay extends AppCompatActivity {
//    public DatabaseReference mDatabase;
//    public ArrayList<String> all_users=new ArrayList<String>();
//    public ArrayList<String> users=new ArrayList<String>();
//    Button search_btn;
//    ListView search_list;
//    TextView k;
//    EditText search;
//    SharedPreferences sharedPreferences;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_search_result_display);
//        k=(TextView)findViewById(R.id.textView3);
//        search=(EditText)findViewById(R.id.search);
//        search_list=(ListView)findViewById(R.id.user_list);
//        search_btn=(Button)findViewById(R.id.searchbtn);
//        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        BottomNavigationView bottom = findViewById(R.id.navigationView);
//
//
//
//        mDatabase.child("Users").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
//
//                    String str = postSnapshot.child("username").toString();
//                    String subString = str.substring(str.lastIndexOf("=") + 1);
//                    String name = subString.substring(0, subString.indexOf("}"));
//                    all_users.add(name + "");
//                }
//            }
//
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Getting Post failed, log a message
//                Log.w(null, "loadPost:onCancelled", databaseError.toException());
//                // ...
//            }
//        });
//        search_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                users = new ArrayList<String>();
//                for(String lala: all_users){
//                    if (lala.contains(search.getText().toString())) {
//                        users.add(lala);}
//                    ArrayAdapter<String> adapter1 = (new ArrayAdapter<String>(SearchResultDisplay.this, android.R.layout.simple_list_item_1,users));
//                    search_list.setAdapter(adapter1);
//                }
//                users = new ArrayList<String>();
//            }
//        });
//
//        search_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String selected_user=(search_list.getItemAtPosition(position)).toString();
//                SharedPreferences.Editor e =sharedPreferences.edit();
//                e.putString("searched_user",selected_user);
//                e.commit();
//                startActivity(new Intent(SearchResultDisplay.this,FriendsList.class));
//            }
//        });
//
//    }
//}