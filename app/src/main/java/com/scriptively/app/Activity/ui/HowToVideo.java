package com.scriptively.app.Activity.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import com.scriptively.app.R;

public class HowToVideo extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_video);

        webView = findViewById(R.id.webView);

        webView.loadUrl("https://scriptively.com/tutorials");
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}