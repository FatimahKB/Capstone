package com.example.fatim.makeawish;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Transaction extends AppCompatActivity {

    EditText editTextCardNum;
    EditText editTextCVV;
    EditText editTextMonth;
    EditText editTextYear;
    Button btnPay;
    SharedPreferences sharedPreferences;
    TextView creditamount;
    String notif_price;
    public DatabaseReference mDatabase;
    Item items;

    private static final String CHANNEL_ID = "wishlist";
    private static final String CHANNEL_NAME = "Wishlist";
    private static final String CHANNEL_DESC = "Wishlist Notifications";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        editTextCardNum = (EditText) findViewById(R.id.editTextCardNum);
        editTextCVV = (EditText) findViewById(R.id.editTextCVV);
        editTextMonth = (EditText) findViewById(R.id.editTextMonth);
        editTextYear = (EditText) findViewById(R.id.editTextYear);
        btnPay=(Button)findViewById(R.id.btnPay);
        creditamount=(TextView)findViewById(R.id.creditamount);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final Float amount = sharedPreferences.getFloat("credit_amount", 0);
        final String user_name = sharedPreferences.getString("searched_user", "").trim();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        notif_price=amount+"";
        creditamount.setText("Checkout Price : "+amount);
        final Long item_pos = sharedPreferences.getLong("item_pos", 0);

        //Notification
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        //
        mDatabase.child("Users").child(user_name).child("item" + item_pos).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                items = dataSnapshot.getValue(Item.class);
            }
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(null, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    double new_remaining_price = items.getRemaining_price() - amount;
                    if (new_remaining_price == 0) {
                        mDatabase.child("Users").child(user_name).child("item" + item_pos).removeValue();
                    } else {
                        mDatabase.child("Users").child(user_name).child("item" + item_pos).child("remaining_price").setValue(new_remaining_price);
                    }
                    Toast.makeText(Transaction.this, "Transaction successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Transaction.this, Profile.class));
                    displayNotification();
                }
            }
        });
    }


    private void displayNotification(){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.add) // wrong
                        .setContentTitle("Transaction Successful")
                        .setContentText("Congratulations! Your transaction of SAR "+notif_price+" has been completed successfully :)")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat mNotificationMgr = NotificationManagerCompat.from(this);
        mNotificationMgr.notify(1, mBuilder.build());
    }

    public boolean validate(){
        Boolean result = false;
        String number= editTextCardNum.getText().toString();
        String cvv = editTextCVV.getText().toString();
        String month = editTextMonth.getText().toString();
        String year = editTextYear.getText().toString();

        if(number.isEmpty() || cvv.isEmpty() || month.isEmpty() || year.isEmpty()){
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
        }
        else{
            result= true;
        }
        return result;
    }
}

