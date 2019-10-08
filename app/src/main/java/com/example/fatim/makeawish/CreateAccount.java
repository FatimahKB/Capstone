package com.example.fatim.makeawish;



import android.app.DatePickerDialog;

import android.app.ProgressDialog;

import android.content.Intent;

import android.graphics.Bitmap;

import android.net.Uri;

import android.provider.MediaStore;

import android.support.annotation.NonNull;

import android.support.annotation.Nullable;

import android.support.design.widget.TabLayout;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Log;

import android.view.View;

import android.widget.Button;

import android.widget.DatePicker;

import android.widget.EditText;

import android.widget.ImageView;

import android.widget.Toast;



import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.OnFailureListener;

import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.storage.FirebaseStorage;

import com.google.firebase.storage.OnProgressListener;

import com.google.firebase.storage.StorageReference;

import com.google.firebase.storage.UploadTask;



import java.io.IOException;

import java.text.DateFormat;

import java.util.Calendar;

import java.util.Date;



import static android.media.CamcorderProfile.get;



public class CreateAccount extends AppCompatActivity {
   //controllers
    EditText username_control;
    EditText email_control;
    EditText password_control;
    EditText confirmPassword_control;
    FirebaseAuth firebaseAuth;
    Button create_control;

    //img
    ImageView profilePic;
    private Uri filePath;
    String path;
    final int PICK_IMAGE_REQUEST=71;

    //Database
    FirebaseStorage storage;
    StorageReference storageReference;
    public DatabaseReference mDatabase;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
//storage

        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();

        //Creating a datepicker
        final Calendar c=Calendar.getInstance();
        DateFormat expDate= DateFormat.getDateInstance();
        final DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {

            @Override

            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            }

        };

        Button btnDOB = (Button) findViewById(R.id.create_account_dob_button);
        btnDOB.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                new DatePickerDialog(CreateAccount.this,d, c.get(Calendar.YEAR), c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH)).show();
            }

        });


        //Intialize controls
        username_control = (EditText) findViewById(R.id.create_account_name_editText);
        email_control = (EditText) findViewById(R.id.create_account_email_editText);
        password_control = (EditText) findViewById(R.id.create_account_password_editText);
        confirmPassword_control = (EditText) findViewById(R.id.create_account_confirm_password_editText);
        create_control = (Button)findViewById(R.id.create_account_create_button);
        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        profilePic =findViewById(R.id.profilepic_imageView);

        create_control.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                // add to database
                if (valid()) {
                    final String email = email_control.getText().toString().trim();
                    final String [] username= email.split("@");
                    final String password = password_control.getText().toString().trim();
                    final String DOB = c.get(Calendar.YEAR)+"/"+c.get(Calendar.MONTH)+"/"+c.get(Calendar.DAY_OF_MONTH);

                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override

                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //add to database
                                username[0]= username[0].toLowerCase();
                                user = new User (email,password,DOB);
                                mDatabase.child("Users").child(username[0]).setValue(user);
                                uploadImage();
                                Toast.makeText(CreateAccount.this, "Registration Successful", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(CreateAccount.this, MainActivity.class));
                            }
                            else {
                                Toast.makeText(CreateAccount.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
                }
            }

        });
    }


    //adding img
    protected void pick(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE_REQUEST);
    }

//img
    private void uploadImage() {
        if (filePath!=null)
        {
            final ProgressDialog pd =new ProgressDialog(this);
            pd.setTitle("Uploading...");
            pd.show();
            final String email1 = email_control.getText().toString().trim();
            final String [] username1= email1.split("@");
            StorageReference ref = storageReference.child("profilepictures/"+username1[0] );
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                        @Override

                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            pd.dismiss();
                            Toast.makeText(CreateAccount.this, "Upload Successful", Toast.LENGTH_SHORT).show();
                        }

                    })
                    .addOnFailureListener(new OnFailureListener() {

                        @Override

                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(CreateAccount.this, "Upload Failed", Toast.LENGTH_SHORT).show();

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK
                && data!=null && data.getData()!=null)
        {
            filePath=data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                profilePic.setImageBitmap(bitmap);
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    //checking for empty fields
    public boolean valid() {
        if (username_control.getText().toString().isEmpty() || email_control.getText().toString().isEmpty() || password_control.getText().toString().isEmpty() || confirmPassword_control.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please fill all the required fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            if(confirmPassword_control.getText().toString().equals(password_control.getText().toString()))
                return true;
            else{
                Toast.makeText(this, "Password and confirm password do not match", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }
}