package com.example.fatim.makeawish;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class AddingItem extends AppCompatActivity {
    TextView text;
    String first_list;
    private Uri filePath;
    ImageView img;
    String path;
    final int PICK_IMAGE_REQUEST=71;
    FirebaseStorage storage;
    StorageReference storageReference;

    EditText name;
    EditText price;
    EditText quantity;
    EditText link;
    Button choose;
    Button btn_addingItem_add_button;
    Button btn_addingItem_search_button;
    Item item;
    public DatabaseReference mDatabase;
    String [] listsname;
    String username1;
    boolean [] checkedLists;
    ArrayList<Integer> selectedLists= new ArrayList<>();
    ArrayList<String> lists = new ArrayList<>();
    ArrayList<Long> listSizes = new ArrayList<>();
    ArrayList<String> finallists = new ArrayList<>();
    String nameList="";
    long number;
    String username [];
    final HashMap<String,Long> hMap = new HashMap<String, Long>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_item);
        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();
        //intializing controls
      //  btn_addingItem_search_button =(Button) findViewById(R.id.addingItem_search_button);
        btn_addingItem_add_button =(Button) findViewById(R.id.addingItem_add_button);
        choose=(Button)findViewById(R.id.addingItem_choose_button);
        name=(EditText)findViewById(R.id.addingItem_name_editText);
        price=(EditText)findViewById(R.id.addingItem_price_editText);
        quantity=(EditText)findViewById(R.id.addingItem_quality_editText);
        link=(EditText)findViewById(R.id.addingItem_link_editText);
        text=(TextView)findViewById(R.id.adding_item_textView);
        img=findViewById(R.id.addingItem_imageView);
        String styledText = "<u>Choose lists</u>";
        choose.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
        username1=FirebaseAuth.getInstance().getCurrentUser().getEmail().split("@")[0];


        // To get an instance of the databse so we can add/remove etc..
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
         username=user.getEmail().split("@");

        //
        mDatabase.child("Users").child(username[0]).child("Lists").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot n : dataSnapshot.getChildren()){
                    hMap.put(n.getKey(),n.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mDatabase.child("Users").child(username[0]).child("Lists").child("Private").addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lists.add("Public");
                Log.d("hello","insdie here");
                for (DataSnapshot n : dataSnapshot.getChildren()) {
                    lists.add(n.getKey());
                    listSizes.add(n.getChildrenCount());
                    hMap.put(n.getKey(),n.getChildrenCount());
                }
                listsname=new String[lists.size()];
                listsname= lists.toArray(listsname);
                checkedLists=new boolean [listsname.length];
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(null, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });


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

    //adding the item to database
        btn_addingItem_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valid()) {
                    first_list=finallists.get(0);
                    for ( int i = 0; i < finallists.size(); i++) {
                        nameList=finallists.get(i);
                        if(nameList.equals("Public")){
                            mDatabase.child("Users").child(username[0]).child("Lists").child("Public").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    number=dataSnapshot.getChildrenCount();
                                    Toast.makeText(AddingItem.this,"I'm here"+number, Toast.LENGTH_LONG).show();
                                    Log.d("bbyee","heey there"+number+"");
                                    mDatabase.child("Users").child(username[0]).child("Lists").child("Public").child("item" + +hMap.get("Public")).setValue(item);

                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    // Getting Post failed, log a message
                                    Log.w(null, "loadPost:onCancelled", databaseError.toException());
                                }
                            });
                            path ="gs://makeawish-3b12e.appspot.com/"+"UserItemImage/"+ username[0].toString()+"/"+first_list+"/"+name.getText().toString();
                             item = new Item(name.getText().toString(), Integer.parseInt(quantity.getText().toString()), Double.parseDouble(price.getText().toString()), Double.parseDouble(price.getText().toString()),path);

                            continue;
                            }
                        DatabaseReference d = mDatabase.child("Users").child(username[0]).child("Lists").child("Private").child(nameList);
                        mDatabase.child("Users").child(username[0]).child("Lists").child("Private").child(nameList).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot d) {
                                 number+=d.getChildrenCount();
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                // Getting Post failed, log a message
                                // ...
                            }
                        });
                        path ="gs://makeawish-3b12e.appspot.com/"+"UserItemImage/"+ username[0].toString()+"/"+first_list+"/"+name.getText().toString();
                        item = new Item(name.getText().toString(), Integer.parseInt(quantity.getText().toString()), Double.parseDouble(price.getText().toString()), Double.parseDouble(price.getText().toString()),path);
                        mDatabase.child("Users").child(username[0]).child("Lists").child("Private").child(nameList).child("item"+hMap.get(nameList)).setValue(item);

                    }
                    }
                    uploadImage();
            }

        });

        btn_addingItem_search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    startActivity(new Intent(AddingItem.this, UploadImage.class));

               startActivity(new Intent(AddingItem.this,Search.class));
            }
        });

    }

    private void uploadImage() {
        if (filePath!=null)
        {
            final ProgressDialog pd =new ProgressDialog(this);
            pd.setTitle("Uploading...");
            pd.show();

            StorageReference ref = storageReference.child("UserItemImage/"+ username[0].toString()+"/"+first_list+"/"+name.getText().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            pd.dismiss();
                            Toast.makeText(AddingItem.this, "Upload Successful", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(AddingItem.this, "Upload Failed", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            pd.setMessage("Uploaded Successfully "+(int)progress+"%");
                        }
                    });
        }
    }

    protected void choose(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK
                && data!=null && data.getData()!=null)
        {
            filePath=data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                img.setImageBitmap(bitmap);
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }
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