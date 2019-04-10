package com.example.fatim.makeawish;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;

public class createPrivateList extends Activity {
    EditText name;
    Button expiration;
    Button create;
    String date;
    DatabaseReference mDatabase;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_private_list);
        name=(EditText)findViewById(R.id.createPrivate_label_editText);
        expiration= (Button)findViewById(R.id.createPrivate_expDate_textView);
        create = (Button)findViewById(R.id.createPrivate_Create_button);

        //create a datepicker
        final Calendar c=Calendar.getInstance();
        DateFormat expDate= DateFormat.getDateInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            }
        };

        expiration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(createPrivateList.this,d, c.get(Calendar.YEAR), c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user= FirebaseAuth.getInstance().getCurrentUser();
                String username []=user.getEmail().split("@");
                date=c.get(Calendar.YEAR)+"/"+ c.get(Calendar.MONTH)+"/"+c.get(Calendar.DAY_OF_MONTH);
                mDatabase.child("Users").child(username[0]).child("Lists").child("Private").child(name.getText().toString().trim()).child("expiration").setValue(date);
                Toast.makeText(createPrivateList.this, "List has been successfully created", Toast.LENGTH_LONG).show();
            }
        });

    }

}
