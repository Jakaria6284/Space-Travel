package com.ariyanjakaria.spacetour;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    public static boolean homeactivity;
    ImageView imageView;

    private TextView username,DDate,pllanetname,pllanettype,pllanetDescription,pllanetdisatance,pllanetday
            ,pllanetspeed,pllanetcost;
    private RecyclerView recyclerView;
    ShimmerFrameLayout shimmerFrameLayout;

    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }


        setContentView(R.layout.activity_home);
        username=findViewById(R.id.username);
        DDate=findViewById(R.id.date);
        imageView=findViewById(R.id.nevigationnn);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, chatActivity.class));
            }
        });
        pllanetname=findViewById(R.id.planetname);
        pllanettype=findViewById(R.id.planettype);
        pllanetDescription=findViewById(R.id.planetdescription);
        pllanetdisatance=findViewById(R.id.planetdistance);
        pllanetday=findViewById(R.id.planetduration);
        pllanetspeed=findViewById(R.id.planetspeed);
        pllanetcost=findViewById(R.id.planetcost);
        Date currentDate = Calendar.getInstance().getTime();
        firebaseFirestore=FirebaseFirestore.getInstance();
        recyclerView=findViewById(R.id.recylerview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        shimmerFrameLayout=findViewById(R.id.shimmer);
        shimmerFrameLayout.startShimmer();

        firebaseFirestore.collection("popular")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            for(QueryDocumentSnapshot documentSnapshot:task.getResult())
                            {
                                String Pname=documentSnapshot.getString("pname");
                                String Ptype=documentSnapshot.getString("ptype");
                                String Pdes=documentSnapshot.getString("pdes");
                                String Pdistance=documentSnapshot.getString("pdis");
                                String Pduration=documentSnapshot.getString("pduration");
                                String Pspeed=documentSnapshot.getString("pspeed");
                                String Pcost=documentSnapshot.getString("pcost");

                                pllanetname.setText(Pname);
                                pllanettype.setText(Ptype);
                                pllanetDescription.setText(Pdes);
                                pllanetdisatance.setText(Pdistance);
                                pllanetday.setText(Pduration);
                                pllanetspeed.setText(Pspeed);
                                pllanetcost.setText(Pcost);
                            }


                        }
                    }
                });

        firebaseFirestore.collection("package")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            List<packageModel>packageModelList=new ArrayList<>();
                            for(QueryDocumentSnapshot documentSnapshot:task.getResult())
                            {
                                String Name=documentSnapshot.getString("name");
                                String Image=documentSnapshot.getString("image");
                                String UID=documentSnapshot.getString("did");
                                String Dview=documentSnapshot.getString("3d");
                                String gif= documentSnapshot.getString("g");
                                packageModel packageModel=new packageModel(Image,Name,UID,Dview,gif);
                                packageModelList.add(packageModel);
                               // Toast.makeText(HomeActivity.this, "retrive", Toast.LENGTH_SHORT).show();
                            }
                            packageAdapter packageAdapter=new packageAdapter(packageModelList,true);
                            recyclerView.setAdapter(packageAdapter);
                            packageAdapter.notifyDataSetChanged();
                            shimmerFrameLayout.stopShimmer();
                            shimmerFrameLayout.setVisibility(View.GONE);


                        }
                    }
                });

        // Format the date
        String formattedDate = formatDate(currentDate);
        DDate.setText(formattedDate);
    }
    private String formatDate(Date date) {
        // Create a SimpleDateFormat with the desired format
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, d MMMM yyyy", Locale.getDefault());

        // Format the date
        return dateFormat.format(date);
    }
}