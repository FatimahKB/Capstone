package com.example.fatim.makeawish;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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

        //Intialize controls
        username_control = (EditText) findViewById(R.id.create_account_name_editText);
        email_control = (EditText) findViewById(R.id.create_account_email_editText);
        password_control = (EditText) findViewById(R.id.create_account_password_editText);
        confirmPassword_control = (EditText) findViewById(R.id.create_account_confirm_password_editText);
        create_control = (Button)findViewById(R.id.create_account_create_button);
        firebaseAuth = FirebaseAuth.getInstance();


        create_control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add to database
                if (valid()) {
                    final String email = email_control.getText().toString().trim();
                    final String password = password_control.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //add to database
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
                return false;}


        }
    }
    }
