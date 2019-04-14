package com.example.fatim.makeawish;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment; import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class PublicListFragment extends Fragment {
    public DatabaseReference mDatabase;
    FirebaseUser user;
  //  ListView items_list;
    ArrayList<String> all_items_list = new ArrayList<>();
    ArrayList<String> all_itemsImg_list = new ArrayList<>();
    FirebaseStorage   storage;
    StorageReference storageRef;
    Button profile;
    Button add;
    ListView mlistView;
    //   private View view;
    TextView friendsNumberText;
    String friends;
    int friendsNumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.publiclistlayout, viewGroup, false);
    //    items_list = (ListView) view.findViewById(R.id.Profile_publicItems_ListView);
//
        storage= FirebaseStorage.getInstance();
        friendsNumberText= (TextView) view.findViewById(R.id.PublicLayout_FriendsNumbers_TextView);



        mlistView = view.findViewById(R.id.Profile_publicItems_ListView);

        CustomAdapter customAdapter = new CustomAdapter();
        mlistView.setAdapter(customAdapter);


        add=(Button)view.findViewById(R.id.public_add_button);
        profile=(Button)view.findViewById(R.id.button2);

        //displaying the public list's items
        user = FirebaseAuth.getInstance().getCurrentUser();
        String username[] = user.getEmail().split("@");
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("Users").child(username[0]).child("Lists").child("Public").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                all_items_list = new ArrayList<>();
                all_itemsImg_list = new ArrayList<>();

                // Get Post object and use the values to update the UI
                for (DataSnapshot n : dataSnapshot.getChildren()) {
                    if (n.getKey().equals("username") || n.getKey().equals("email"))
                        continue;
                    Item item = n.getValue(Item.class);
                    all_items_list.add(item.getName());
                    all_itemsImg_list.add(item.getImgPath());
                //    ArrayAdapter<String> adapter1 = (new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, all_items_list));
                  //  items_list.setAdapter(adapter1);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(null, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddingItem.class));

            }
        });
        mDatabase.child("Users").child(username[0]).child("friends").addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                friends=dataSnapshot.getValue(String.class);
                for(int i=0;i<friends.length();i++){
                    if(friends.charAt(i)== ','){
                        friendsNumber++;
                    }
                }
                friendsNumberText.setText(friendsNumber+1+" friends");
            }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(null, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });

        friendsNumberText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),displayFriends.class));
            }
        });
        return view;
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
            mTitle.setText(all_items_list.get(position));
            final long ONE_MEGABYTE = 1024 * 1024;
            storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    mImageView.setImageBitmap(bitmap);
        }
    }        );

            return view;

        }
    }
}