package com.ariyanjakaria.spacetour;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class paymentActivity extends AppCompatActivity {
 Button exploree;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        exploree=findViewById(R.id.explore);
        exploree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(paymentActivity.this, PPTActivity.class);
                startActivity(intent);
            }
        });
    }
}