package com.ariyanjakaria.spacetour;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class splashActivity extends AppCompatActivity {
    private Button button;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        button=findViewById(R.id.start);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(firebaseUser==null)
                {
                    Intent intent=new Intent(splashActivity.this, loginActivity.class);
                    startActivity(intent);
                }else
                {
                    Intent intent=new Intent(splashActivity.this, HomeActivity.class);
                    startActivity(intent);
                }

            }
        });
    }
}