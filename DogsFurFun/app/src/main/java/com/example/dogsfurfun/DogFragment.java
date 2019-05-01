package com.example.dogsfurfun;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class DogFragment extends Fragment {

    String urlTest;
    ImageView imageView;
    TextView breedNameText;
    TextView temperateText;
    TextView weightText;
    TextView heightText;
    TextView lifeExpectancyText;
    TextView bredforText;
    private RequestQueue queue;
    String API_URL ="https://api.thedogapi.com/v1/images/search";



    public DogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_dog, container, false);
        imageView = rootView.findViewById(R.id.imageView);
        breedNameText = rootView.findViewById(R.id.breedTextView);
        weightText = rootView.findViewById(R.id.weightTextView);
        heightText = rootView.findViewById(R.id.heightTextView);
        bredforText = rootView.findViewById(R.id.bredTextView);
        temperateText = rootView.findViewById(R.id.temperatTextView);
        lifeExpectancyText = rootView.findViewById(R.id.lifeExpectancyTextView);
        requestData();
        Button button = rootView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                requestData();
            }
        });
        return rootView;
    }



    void requestData(){

        queue = Volley.newRequestQueue(this.getActivity());
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, API_URL,
                new com.android.volley.Response.Listener<String>() {
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

        // Add the request to the RequestQueue
        queue.add(stringRequest);

    }

    void parseJSON(String response){
        //response should be a String with JSON objects
        if (response == null) {
            response = "THERE WAS AN ERROR";
        }
        try{
            JSONArray jsonarray = new JSONArray(response);
            JSONObject data = jsonarray.getJSONObject(0);
            JSONArray breedsArray= data.getJSONArray("breeds");
            if(breedsArray.length()>0){
                JSONObject breeds = breedsArray.getJSONObject(0);
                String name = breeds.getString("name");
                breedNameText.setText(name);
                String weight = breeds.getJSONObject("weight").getString("imperial");
                weightText.setText("Weight: " + weight+" pounds");
                String height = breeds.getJSONObject("height").getString("imperial");
                heightText.setText("Height: "+height+" inches");
                String bredFor = breeds.getString("bred_for");
                bredforText.setText("Bred for: "+bredFor);
                String lifeSpan = breeds.getString("life_span");
                lifeExpectancyText.setText("Life Span: "+lifeSpan);
                String temperament = breeds.getString("temperament");
                temperateText.setText(temperament);
                urlTest = data.getString("url");
                Picasso.get().load(urlTest).into(imageView);
            }
            else{
                requestData();
                return;
            }







        }
        catch(JSONException e){
            System.out.print("error");
        }

    }



}
