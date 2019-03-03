package com.example.fatim.makeawish;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.DateFormat;
import java.util.Calendar;

public class createPrivateList extends Activity {
    EditText name;
    Button expiration;
    Button create;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_private_list);
        Button btn_createPrivate_Create_button =(Button) findViewById(R.id.createPrivate_Create_button);
        name=(EditText)findViewById(R.id.createPrivate_label_editText);
        expiration= (Button)findViewById(R.id.createPrivate_expDate_textView);
        create = (Button)findViewById(R.id.createPrivate_Create_button);
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

        expiration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(createPrivateList.this,d, c.get(Calendar.YEAR), c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PrivateWishlist list = new PrivateWishlist(name.getText().toString().trim(),expiration.getText().toString().trim());
            }
        });

        btn_createPrivate_Create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(createPrivateList.this,PrivateWishlists.class));

            }
        });
    }

}
