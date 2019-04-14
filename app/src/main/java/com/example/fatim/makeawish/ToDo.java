package com.example.fatim.makeawish;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
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

import java.util.ArrayList;


public class ToDo extends AppCompatActivity {
    ListView mlistView;
    ListView reminders;

    FirebaseUser user;
    DatabaseReference mDatabase;

    ArrayList<String> friendRequestsList = new ArrayList<>();
    ArrayList<Gift> itemsToBuyList = new ArrayList<Gift>();

    Object friendRequestsArray[];
    Gift g;

    String username[];
    String friends="";
    Gift gift;
    Gift gift1;
    FirebaseStorage storage;

    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);

        //Controls initialization
        mlistView = findViewById(R.id.listView);
        reminders = findViewById(R.id.todo_reminders_list);

        //databse and user initialization
        mDatabase = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        username=user.getEmail().split("@");



        //Friend requests retrieval
        mDatabase.child("Users").child(username[0]).child("friendRequests").addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot n : dataSnapshot.getChildren()) {
                    friendRequestsList.add(n.getValue().toString());
                }
                mlistView.setAdapter(new CustomAdapter(friendRequestsList));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(null, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });

        //Items to buy retrieval
        mDatabase.child("Users").child(username[0]).child("itemsToBuy").addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot n : dataSnapshot.getChildren()) {
                    Gift gift = n.getValue(Gift.class);
                    itemsToBuyList.add(gift);
                }
                reminders.setAdapter(new CustomAdapter1(itemsToBuyList));
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
                        startActivity(new Intent(ToDo.this,History.class));break;
                    case R.id.navigation_todo:
                        startActivity(new Intent(ToDo.this,ToDo.class));break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(ToDo.this,Profile.class));break;

                    case R.id.navigation_search:
                        startActivity(new Intent(ToDo.this,Search.class));break;
                    case R.id.navigation_settings:
                        startActivity(new Intent(ToDo.this,Settings.class));break;
                    default:
                }
                return false;
            }
        });
    }

    class CustomAdapter extends BaseAdapter {
        ArrayList<String> r=new ArrayList<>();

        CustomAdapter(ArrayList r){
            this.r=r;
        }

        @Override
        public int getCount() {
            return r.size();
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
        public View getView( int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.customlayout,null);
            ImageView mImageView = view.findViewById(R.id.imageView);
            TextView mTitle = view.findViewById(R.id.title);
            Button mAccept= view.findViewById(R.id.button);
            Button mDecline= view.findViewById(R.id.button2);
            friendRequestsArray=r.toArray();
            mAccept.setTag(position);
            mDecline.setTag(position);
            mTitle.setText(friendRequestsArray[position].toString());
            //////////////////////////////////////////////////
            String path ="gs://makeawish-3b12e.appspot.com/"+"profilepictures/"+friendRequestsArray[position].toString();
            storage=FirebaseStorage.getInstance();
            final StorageReference storageRef = storage.getReferenceFromUrl(path);

            final long ONE_MEGABYTE = 1024 * 1024;
            storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    image.setImageBitmap(bitmap);
//                        img.setWidth(50);
//                        img.setMaxHeight(50);
                }
            });
            return view;
        }
    }

    class CustomAdapter1 extends BaseAdapter {
        ArrayList<Gift> gifts=new ArrayList<Gift>();

        CustomAdapter1(ArrayList<Gift> gifts){
            this.gifts=gifts;
        }

        @Override
        public int getCount() {
            return gifts.size();
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
        public View getView( int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.customlayout1,null);
            image = view.findViewById(R.id.tobuy_giftee_image);
            Button buy= view.findViewById(R.id.tobuy_buy_button);
            Button cancel= view.findViewById(R.id.tobuy_cancel_button);
            TextView giftee = view.findViewById(R.id.tobuy_giftee_name);
            TextView gift = view.findViewById(R.id.tobuy_giftname_text);
            TextView price = view.findViewById(R.id.tobuy_giftprice_text);
            buy.setTag(position);
            cancel.setTag(position);

            g= gifts.get(position);
            giftee.setText("Giftee: "+g.getUsername());
            gift.setText("Gift: "+g.getGiftname());
            price.setText("price: "+g.getPrice());
            storage=FirebaseStorage.getInstance();
            final StorageReference storageRef = storage.getReferenceFromUrl(g.getImgpath());

            final long ONE_MEGABYTE = 1024 * 1024;
            storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    image.setImageBitmap(bitmap);
//                        img.setWidth(50);
//                        img.setMaxHeight(50);
                }
            });

            return view;
        }
    }

    public void accept(View v){
        final int position=(Integer)v.getTag();
        mDatabase.child("Users").child(username[0]).child("friends").addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    friends = dataSnapshot.getValue(String.class);
                    mDatabase.child("Users").child(username[0]).child("friends").setValue(friends+", "+friendRequestsArray[position]);
                }else{
                    mDatabase.child("Users").child(username[0]).child("friends").setValue(friendRequestsArray[position]);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(null, "loadPost:onCancelled", databaseError.toException());
                // ...
            }

        });
        mDatabase.child("Users").child(username[0]).child("friendRequests").addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot n : dataSnapshot.getChildren()) {
                    if (n.getValue().equals(friendRequestsArray[position])) {
                        mDatabase.child("Users").child(username[0]).child("friendRequests").child(n.getKey()).removeValue();
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
    }

    public void decline(View v){

        final int position=(Integer)v.getTag();
        mDatabase.child("Users").child(username[0]).child("friendRequests").addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot n : dataSnapshot.getChildren()) {
                    if (n.getValue().equals(friendRequestsArray[position])) {
                        mDatabase.child("Users").child(username[0]).child("friendRequests").child(n.getKey()).removeValue();
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
    }

    public void buy(View v){
        final int position=(Integer)v.getTag();
        mDatabase.child("Users").child(username[0]).child("itemsToBuy").addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot n : dataSnapshot.getChildren()) {
                    Gift gift = n.getValue(Gift.class);
                    Gift gift1=itemsToBuyList.get(position);
                    if (gift.getGiftname().equals(gift1.getGiftname()) && gift.getUsername().equals(gift1.getUsername()) && gift.getPrice()==gift1.getPrice())
                        mDatabase.child("Users").child(username[0]).child("itemsToBuy").child(n.getKey()).removeValue();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(null, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });
        startActivity(new Intent(ToDo.this,Buying.class));
    }

    public void cancel(View v){
        final int position=(Integer)v.getTag();
        mDatabase.child("Users").child(username[0]).child("itemsToBuy").addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot n : dataSnapshot.getChildren()) {
                    gift = n.getValue(Gift.class);
                    gift1=itemsToBuyList.get(position);
                    if (gift.getGiftname().equals(gift1.getGiftname()) && gift.getUsername().equals(gift1.getUsername()) && gift.getWishlist().equals(gift1.getWishlist()))
                    {
                        mDatabase.child("Users").child(username[0]).child("itemsToBuy").child(n.getKey()).removeValue();

                        if(gift1.getWishlist().equals("Public")){
                            mDatabase.child("Users").child(gift1.getUsername()).child("Lists").child("Public").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                        Item item = new Item(gift1.getGiftname(), gift1.getQuantity(), gift1.getPrice(), gift1.getPrice(),"gs://makeawish-3b12e.appspot.com/placeholder.png");
                                    mDatabase.child("Users").child(gift1.getUsername()).child("Lists").child("Public").child("item"+(dataSnapshot.getChildrenCount()+1)).setValue(item);
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    // Getting Post failed, log a message
                                    Log.w(null, "loadPost:onCancelled", databaseError.toException());
                                    // ...
                                }
                            });

                        }else{
                            mDatabase.child("Users").child(gift1.getUsername()).child("Lists").child("Private").child(gift1.getWishlist()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Item item = new Item(gift1.getGiftname(), gift1.getQuantity(), gift1.getPrice(), gift1.getPrice(),"gs://makeawish-3b12e.appspot.com/placeholder.png");
                                    mDatabase.child("Users").child(gift1.getUsername()).child("Lists").child("Private").child(gift1.getWishlist()).child("item"+(dataSnapshot.getChildrenCount()+1)).setValue(item);
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