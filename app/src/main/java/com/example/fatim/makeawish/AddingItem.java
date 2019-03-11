package com.example.fatim.makeawish;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class AddingItem extends AppCompatActivity {
    TextView text;
    EditText name;
    EditText price;
    EditText quantity;
    EditText link;
    Button choose;
    Button btn_addingItem_add_button;
    Button btn_addingItem_search_button;

    public DatabaseReference mDatabase;
    String [] listsname;
    String username1;
    boolean [] checkedLists;
    ArrayList<Integer> selectedLists= new ArrayList<>();
    ArrayList<String> lists = new ArrayList<>();
    ArrayList<String> finallists = new ArrayList<>();
    String nameList="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_item);
        btn_addingItem_search_button =(Button) findViewById(R.id.addingItem_search_button);
        btn_addingItem_add_button =(Button) findViewById(R.id.addingItem_add_button);
        choose=(Button)findViewById(R.id.addingItem_choose_button);
        name=(EditText)findViewById(R.id.addingItem_name_editText);
        price=(EditText)findViewById(R.id.addingItem_price_editText);
        quantity=(EditText)findViewById(R.id.addingItem_quality_editText);
        link=(EditText)findViewById(R.id.addingItem_link_editText);
        text=(TextView)findViewById(R.id.adding_item_textView);
        // To get an instance of the databse so we can add/remove etc..
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String username [] =user.getEmail().split("@");
//        listsname=getResources().getStringArray(R.array.listsname);
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
         username1=sharedPreferences.getString("username","");

        mDatabase.child("Users").child(username1).child("Lists").child("Private").addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lists.add("Public");
                for (DataSnapshot n : dataSnapshot.getChildren()) {
                    lists.add(n.getKey());
                }
                listsname=new String[lists.size()];
                listsname= lists.toArray(listsname);
                checkedLists=new boolean [listsname.length];
            }

                // Get Post object and use the values to update the UI
//                Toast.makeText(AddingItem.this,"here "+(mDatabase.child("Users").child(username1).child("Private").child("Birthday")), Toast.LENGTH_SHORT).show();
//                for (DataSnapshot n : dataSnapshot.getChildren()) {
////                    Toast.makeText(AddingItem.this,"I'm here", Toast.LENGTH_SHORT).show();
//                        lists.add(n.child("name").getValue().toString());
//                    }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(null, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });

//                    Toast.makeText(AddingItem.this,"I'm here"+lists.size(), Toast.LENGTH_SHORT).show();

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder mBuilder=new AlertDialog.Builder(AddingItem.this);
                mBuilder.setTitle("Wishlists");

                mBuilder.setMultiChoiceItems(listsname, checkedLists, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if(isChecked){
                            if(! selectedLists.contains(which)){
                                selectedLists.add(which);
                            }else{
                                selectedLists.remove(which);
                            }
                        }
                    }
                });
                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("choose", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for(int i=0;i<selectedLists.size();i++){
                            finallists.add(listsname[selectedLists.get(i)]);
                        }
                    }
                });
                AlertDialog mDialog= mBuilder.create();
                mDialog.show();

            }
        });


        btn_addingItem_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valid()) {

                    for ( int i = 0; i < finallists.size(); i++) {
                        nameList=finallists.get(i);
                        mDatabase.child("Users").child(username1).child("Lists").child("Private").child(nameList).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Item item = new Item(name.getText().toString(), Integer.parseInt(quantity.getText().toString()), Double.parseDouble(price.getText().toString()), Double.parseDouble(price.getText().toString()));
                                mDatabase.child("Users").child(username1).child("Lists").child("Private").child(nameList).child("item"+dataSnapshot.getChildrenCount()).setValue(item);
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                // Getting Post failed, log a message
                                Log.w(null, "loadPost:onCancelled", databaseError.toException());
                                // ...
                            }
                        });
                    }
                }
        }
        });



        btn_addingItem_search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddingItem.this,Search.class));
            }
        });

//        btn_addingItem_add_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //NOTE: navigate to public/private correctly
//                startActivity(new Intent(AddingItem.this,user_wishlist.class));
//
//            }
//        });
    }
    public boolean valid() {
        if (name.getText().toString().isEmpty() || quantity.getText().toString().isEmpty() || price.getText().toString().isEmpty() ) {
            Toast.makeText(this, "Please fill all the required fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }
    }


}
