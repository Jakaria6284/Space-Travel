package com.ariyanjakaria.spacetour;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class view extends AppCompatActivity {
 ImageView imageView;
 String d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        imageView=findViewById(R.id.photoview);
        d=getIntent().getStringExtra("UID");
        //Glide.with(this).load(d).into(imageView);

      FirebaseFirestore.getInstance().collection("package").document(d)
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

                               String IMg=documentSnapshot.getString("i");


                                Glide.with(view.this).load(IMg).into(imageView);


                            }


                        }
                    }
                });
    }
}