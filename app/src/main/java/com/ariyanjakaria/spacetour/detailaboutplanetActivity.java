package com.ariyanjakaria.spacetour;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class detailaboutplanetActivity extends AppCompatActivity {
    public static boolean detainactivity;

    ImageView image;
    TextView Nameplanet;
    Button book;
    String IMGe,NAM;
    private RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailaboutplanet);
        image=findViewById(R.id.IMAGE);
        Nameplanet=findViewById(R.id.nameplanet);
        IMGe=getIntent().getStringExtra("G");
       NAM=getIntent().getStringExtra("N");
       book=findViewById(R.id.bbbook);
        Glide.with(this).load(IMGe).into(image);
        Nameplanet.setText(NAM);
        firebaseFirestore=FirebaseFirestore.getInstance();
        recyclerView=findViewById(R.id.photogalleryrecyclerview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

        firebaseFirestore.collection("package")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            List<packageModel> packageModelList=new ArrayList<>();
                            for(QueryDocumentSnapshot documentSnapshot:task.getResult())
                            {
                                String Name=documentSnapshot.getString("name");
                                String Image=documentSnapshot.getString("image");
                                String UID=documentSnapshot.getString("did");
                                String Dview=documentSnapshot.getString("3d");
                                String gif= documentSnapshot.getString("g");
                                packageModel packageModel=new packageModel(Image,Name,UID,Dview,gif);
                                packageModelList.add(packageModel);
                               // Toast.makeText(HomeAct.this, "retrive", Toast.LENGTH_SHORT).show();
                            }
                            packageAdapter packageAdapter=new packageAdapter(packageModelList,false);
                            recyclerView.setAdapter(packageAdapter);
                            packageAdapter.notifyDataSetChanged();



                        }
                    }
                });

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(detailaboutplanetActivity.this, bookActivity.class);
                startActivity(intent);
            }
        });




    }
}