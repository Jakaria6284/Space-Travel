package com.ariyanjakaria.spacetour;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class PPTActivity extends AppCompatActivity {



  WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pptactivity);
        webView=findViewById(R.id.weather);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Configure WebView to open links in the app itself
        webView.setWebViewClient(new WebViewClient());

        // Load the GLB file from a local or remote source
        loadGLBFile();


    }
    private void loadGLBFile() {
        // Load a local GLB file (replace with your GLB file's path)
        // Example for loading a GLB file from assets:
        // webView.loadUrl("file:///android_asset/your_model.glb");

        // Load a remote GLB file (replace with the URL of your GLB file)
        webView.loadUrl("https://mars.nasa.gov/layout/embed/image/mslweather/");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {

            }
        });
    }


}
