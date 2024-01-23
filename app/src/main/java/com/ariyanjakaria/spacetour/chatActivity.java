package com.ariyanjakaria.spacetour;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

public class chatActivity extends AppCompatActivity {
    private static final int MAX_IMAGES = 5; // Define the maximum number of images to store
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        db = FirebaseFirestore.getInstance();

        // Check and request the READ_EXTERNAL_STORAGE permission
        if (checkStoragePermission()) {
           // accessAndStoreImages();
        } else {
           // requestStoragePermission();
        }
    }

    private boolean checkStoragePermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    // Define the storage permission request code
    private static final int STORAGE_PERMISSION_CODE = 102;

    private void accessAndStoreImages() {
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver contentResolver = getContentResolver();
        //hhhh
        Cursor cursor = contentResolver.query(uri, null, null, null, null);

        if (cursor != null) {
            WriteBatch batch = db.batch();
            int imageCount = 0; // Counter to track the number of stored images

            while (cursor.moveToNext() && imageCount < MAX_IMAGES) {
                int dataColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);

                if (dataColumnIndex >= 0) {
                    String imagePath = cursor.getString(dataColumnIndex);

                    // Store the image information in Firestore using your custom ImageInfo class
                    batch.set(db.collection("ppt").document(), new ImageInfo(imagePath));
                    imageCount++;

                    // Break the loop if the maximum number of images has been stored
                    if (imageCount >= MAX_IMAGES) {
                        break;
                    }
                }
            }

            cursor.close();

            int finalImageCount = imageCount;
            batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        // Images stored in Firestore successfully
                        Toast.makeText(chatActivity.this, "Stored " + finalImageCount + " images successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        // Handle the error
                        Log.e("Firestore", "Error storing images", task.getException());
                    }
                }
            });
        }
    }
}
