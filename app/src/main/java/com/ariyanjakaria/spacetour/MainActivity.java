package com.ariyanjakaria.spacetour;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private WebView webView;
    String DVieW,Name;
    private Button InFormation,SeTtelite;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        webView = findViewById(R.id.web_view);
        DVieW=getIntent().getStringExtra("DVIEw");
        Name=getIntent().getStringExtra("N");
        Name=Name.toUpperCase();
        InFormation=findViewById(R.id.information);
        SeTtelite=findViewById(R.id.settelite);



        // Enable JavaScript (required for WebGL)
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Configure WebView to open links in the app itself
        webView.setWebViewClient(new WebViewClient());

        // Load the GLB file from a local or remote source
        loadGLBFile(DVieW,Name);

        SeTtelite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, PPTActivity.class);
                startActivity(intent);
            }
        });

        InFormation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, videoActivity.class);
                startActivity(intent);
            }
        });





    }
    private void loadGLBFile(String view,String name) {
        // Load a local GLB file (replace with your GLB file's path)
        // Example for loading a GLB file from assets:
        // webView.loadUrl("file:///android_asset/your_model.glb");

        // Load a remote GLB file (replace with the URL of your GLB file)
        webView.loadUrl(view);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
          if("MARS".equalsIgnoreCase(name)) {
              SeTtelite.setVisibility(View.VISIBLE);
          }else {
              SeTtelite.setVisibility(View.GONE);
          }
             InFormation.setVisibility(View.VISIBLE);
            }
        });
    }













}
