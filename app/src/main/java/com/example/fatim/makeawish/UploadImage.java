package com.example.fatim.makeawish;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class UploadImage extends AppCompatActivity {

    Button btnChoose,btnUpload;
    ImageView img;


    FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        btnChoose=findViewById(R.id.btnChoose);
        img=findViewById(R.id.imgToUpload);
        storage=FirebaseStorage.getInstance();
        final StorageReference storageRef = storage.getReferenceFromUrl("gs://makeawish-3b12e.appspot.com/UserItemImage/test1/Public/fimg");

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                try {
//                    final File localFile = File.createTempFile("images", "jpg");
//                    storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
//                            img.setImageBitmap(bitmap);
//
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception exception) {
//                        }
//                    });
//                } catch (IOException e ) {}


                final long ONE_MEGABYTE = 1024 * 1024;
                storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        img.setImageBitmap(bitmap);
//                        img.setWidth(50);
//                        img.setMaxHeight(50);
                    }
                });
            }
        });

        //end of oncreate
    }




}
