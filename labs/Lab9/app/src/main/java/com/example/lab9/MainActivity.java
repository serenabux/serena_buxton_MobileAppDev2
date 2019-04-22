package com.example.lab9;

import android.graphics.Bitmap;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class MainActivity extends AppCompatActivity {
    private static final String API_URL = "https://api.nasa.gov/planetary/apod?api_key=2V34QK97Hz0PmFivapzpx5OsMh1Xhe6AIApQmOTM";

    private TextView descriptionView;
    private TextView nameView;
    private TextView dateView;
    private ProgressBar progressBar;

    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        descriptionView = findViewById(R.id.explanationTextView);
        nameView = findViewById(R.id.titleTextView);
        dateView = findViewById(R.id.dateTextView);
        requestData();

    }

    private void requestData(){
        progressBar.setVisibility(View.VISIBLE);
        descriptionView.setText("");
        nameView.setText("");
        queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, API_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseJSON(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR", error.getMessage(), error);
            }
        });
        queue.add(stringRequest);
    }

    private void parseJSON(String response){
        if(response == null){
            response = "An error occured";
        }
        progressBar.setVisibility(View.GONE);

        try{
            JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
            String  name = object.getString("title");
            nameView.setText(name);
            String description = object.getString("explanation");
            descriptionView.setText(description);
            String date = object.getString("date");
            dateView.setText("Planetary News for "  + date);




        } catch (JSONException e){
            e.printStackTrace();
        }
    }



}
