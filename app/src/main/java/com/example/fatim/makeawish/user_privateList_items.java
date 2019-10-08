package com.example.fatim.makeawish;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
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

import java.util.ArrayList;

public class user_privateList_items extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    TextView listName;
    DatabaseReference mDatabase;
    FirebaseUser user;
    ListView listItems;
    ArrayList all_items_list;
    String selected_item;
    FirebaseStorage storage;
    StorageReference storageRef;
    ArrayList<String> all_itemsImg_list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_private_list_items);
        storage= FirebaseStorage.getInstance();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String selectedList=sharedPreferences.getString("selected_private_list","").trim();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        final String username[] = user.getEmail().split("@");
        listItems = (ListView) findViewById(R.id.itemsPriavteList_ListView);
        all_items_list=new ArrayList<>();

        listName= (TextView) findViewById(R.id.PriavteList_name_TextView);
        listName.setText(selectedList);

        CustomAdapter customAdapter1 = new CustomAdapter();
        listItems.setAdapter(customAdapter1);

        mDatabase.child("Users").child(username[0]).child("Lists").child("Private").child(selectedList).addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    all_itemsImg_list = new ArrayList<>();

                    // Get Post object and use the values to update the UI
                    for (DataSnapshot n : dataSnapshot.getChildren()) {
                        if (n.getKey().equals("expiration") || n.getKey().equals("name"))
                            continue;
                        Item item = n.getValue(Item.class);
                        all_items_list.add(item.getName());
                        all_itemsImg_list.add(item.getImgPath());

                    }
//                    ArrayAdapter<String> adapter1 = (new ArrayAdapter<String>(user_privateList_items.this, android.R.layout.simple_list_item_1, all_items_list));
//                    listItems.setAdapter(adapter1);
                }
        }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        listItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected_item  =(listItems.getItemAtPosition(position)).toString();
                sharedPreferences= PreferenceManager.getDefaultSharedPreferences(user_privateList_items.this);
                SharedPreferences.Editor e =sharedPreferences.edit();
                mDatabase.child("Users").child(username[0]).child("Lists").child("Private").child(selectedList).addValueEventListener( new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get Post object and use the values to update the UI
                        for (DataSnapshot n : dataSnapshot.getChildren()) {
                            if (n.getKey().equals("name") || n.getKey().equals("expiration") )
                                continue;
                            Item item = n.getValue(Item.class);

                            if (item.getName().equals(selected_item)){
                                SharedPreferences.Editor e =sharedPreferences.edit();
                                e.putString("clicked_item",selected_item);
                                e.putString("price",item.getPrice()+"");
                                e.putInt("quantity",item.getQuantity());
                                e.putString("remaining_price",item.getRemaining_price()+"");
                                e.putString("path",item.getImgPath());
                                if(item.imgPath!=null) {
                                    e.putString("path", item.imgPath);
                                }
                                e.commit();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Getting Post failed, log a message
                        Log.w(null, "loadPost:onCancelled", databaseError.toException());
                        // ...
                    }
                });
                e.putLong("item_pos",position);
//                e.putString("listType","private");
                e.commit();
                startActivity(new Intent(user_privateList_items.this,users_item_view.class));
            }
        });
}
    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return all_itemsImg_list.size();
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

            storageRef = storage.getReferenceFromUrl(all_itemsImg_list.get(position));
            final View view = getLayoutInflater().inflate(R.layout.custom_layout,null);
            final ImageView mImageView = view.findViewById(R.id.item_image);
            TextView mTitle = view.findViewById(R.id.item_name);

            //     mImageView.setImageResource(images[position]);
            mTitle.setText(all_items_list.get(position).toString());
            Log.d("custom",""+all_items_list.get(0));
            final long ONE_MEGABYTE = 1024 * 1024;
            storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {

                @Override

                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    mImageView.setImageBitmap(bitmap);
                }

            }        );

            return view;
        }}
}
