package com.example.lab6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class InstructionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int brewID = (Integer) getIntent().getExtras().get("brewID");
        Brew brew = Brew.brewTypes.get(brewID);

        getSupportActionBar().setTitle(brew.getName());

        String url = brew.getUrl();


        //reference: https://developer.android.com/guide/webapps/webview#java
        WebView myWebView = findViewById(R.id.webView);
        myWebView.loadUrl(url);

    }



}
