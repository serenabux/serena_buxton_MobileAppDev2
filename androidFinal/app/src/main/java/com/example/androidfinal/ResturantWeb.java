package com.example.androidfinal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ResturantWeb extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resturant_web);

        int restNum = (Integer)getIntent().getExtras().get("rest_id");
        Resturant rest = Resturant.boulderFood.get(restNum);

        WebView webView = findViewById(R.id.webView);
        //webView.loadUrl(mItem.url);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //tells Android to use webview instead of opening in a browser
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(rest.getUrl());

    }
}
