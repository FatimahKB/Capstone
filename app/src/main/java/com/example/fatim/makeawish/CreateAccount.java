package com.example.fatim.makeawish;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateAccount extends AppCompatActivity {
    EditText username_control;
    EditText email_control;
    EditText password_control;
    EditText confirmPassword_control;
    FirebaseAuth firebaseAuth;
    Button create_control;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
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
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        create_control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add to database
                if (valid()) {
                    final String email = email_control.getText().toString().trim();
                    final String [] username= email.split("@");
                    final String password = password_control.getText().toString().trim();
                    final Date DOB = new Date (c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));

                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //add to database
                                int x =0;
                                User user = new User (email,password,DOB);
                                mDatabase.child("Users").child(username[0]).setValue(user);

                                Toast.makeText(CreateAccount.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(CreateAccount.this, MainActivity.class));
                            } else {
                                Toast.makeText(CreateAccount.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

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
