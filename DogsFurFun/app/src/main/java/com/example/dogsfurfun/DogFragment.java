package com.example.dogsfurfun;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DogFragment extends Fragment {

    ImageView imageView;
    private String imageURL;
    TextView breedNameText;
    private String breed;
    TextView temperateText;
    private String temperament;
    TextView weightText;
    private String weight;
    TextView heightText;
    private String height;
    TextView lifeExpectancyText;
    private String lifeSpan;
    TextView bredforText;
    private String bredFor;
    Button anotherDogButton;
    FloatingActionButton fab;
    private RequestQueue queue;
    String API_URL ="https://api.thedogapi.com/v1/images/search";
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("DogBreeds");
    //define an adapter
    FirebaseRecyclerAdapter adapter;
    DatabaseReference breedNameRef = rootRef.child("breedName");



    public DogFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        if (savedInstanceState != null) {
            // Restore last state for checked position.
            breed = savedInstanceState.getString("breed");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View rootView = inflater.inflate(R.layout.fragment_dog, container, false);
        imageView = rootView.findViewById(R.id.imageView);
        imageView.setVisibility(View.GONE);
        breedNameText = rootView.findViewById(R.id.breedTextView);
        weightText = rootView.findViewById(R.id.weightTextView);
        heightText = rootView.findViewById(R.id.heightTextView);
        bredforText = rootView.findViewById(R.id.bredTextView);
        temperateText = rootView.findViewById(R.id.temperatTextView);
        lifeExpectancyText = rootView.findViewById(R.id.lifeExpectancyTextView);
        anotherDogButton = rootView.findViewById(R.id.button);
        fab = rootView.findViewById(R.id.floatingActionButton);

       Bundle args = getArguments();
       //hacky way to maintian upon rotation
       if (args!=null)
       {
           breed = args.getString("breeds");
           temperament = args.getString("temp");
           weight = args.getString("weight");
           height=args.getString("height");
           lifeSpan=args.getString("lifeSpan");
           bredFor=args.getString("bredFor");
           imageURL = args.getStringArrayList("images").get(0);
           breedNameText.setText(breed);
           weightText.setText("Weight: " + weight + " pounds");
           heightText.setText("Height: " + height + " inches");
           bredforText.setText("Bred for: " + bredFor);
           lifeExpectancyText.setText("Life Span: " + lifeSpan);
           temperateText.setText(temperament);
           imageView.setVisibility(View.VISIBLE);
           Picasso.get().load(imageURL).into(imageView);
       }


        else if(savedInstanceState != null){
            breed = savedInstanceState.getString("breed");
            height = savedInstanceState.getString("height");
            weight = savedInstanceState.getString("weight");
            lifeSpan = savedInstanceState.getString("lifeSpan");
            bredFor = savedInstanceState.getString("bredFor");
            temperament = savedInstanceState.getString("temp");
            imageURL = savedInstanceState.getString("imageURL");
            breedNameText.setText(breed);
            weightText.setText("Weight: " + weight + " pounds");
            heightText.setText("Height: " + height + " inches");
            bredforText.setText("Bred for: " + bredFor);
            lifeExpectancyText.setText("Life Span: " + lifeSpan);
            temperateText.setText(temperament);
            imageView.setVisibility(View.VISIBLE);
            Picasso.get().load(imageURL).into(imageView);

        }
        else{
            requestData();
        }

        anotherDogButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    requestData();
                }
            });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (breed != null) {
                    //check if breed already exists
                    //reference: https://stackoverflow.com/questions/38948905/how-can-i-check-if-a-value-exists-already-in-a-firebase-data-class-android
                    rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            ArrayList<String> images = new ArrayList<String>();
                            images.add(imageURL);
                            String key = "";
                            for(DataSnapshot data: dataSnapshot.getChildren()){
                                //check if the breed is already in database
                                String dataKey = data.getKey();
                                DatabaseReference test = rootRef.child(dataKey).child("breedName");
                                DataSnapshot test2 = data.child(dataKey);
                                Object test3 = data.child(dataKey).child("breedName").getValue();
                                if (data.child(dataKey).child("breedName").exists()) {
                                    key = data.getKey();
                                }
                            }
                            if(key!=""){
                                DatabaseReference newChildRef = rootRef.child(key).child("imageURL").push();
                                newChildRef.setValue(imageURL);
                                Snackbar.make(rootView, breed+"  added to favorites", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                            else {
                                //get new id from firebase
                                key = rootRef.push().getKey();
                                //add to Firebase
                                rootRef.child(key).child("breedName").setValue(breed);
                                rootRef.child(key).child("weight").setValue(weight);
                                rootRef.child(key).child("height").setValue(height);
                                rootRef.child(key).child("bredFor").setValue(bredFor);
                                rootRef.child(key).child("lifeSpan").setValue(lifeSpan);
                                rootRef.child(key).child("temperament").setValue(temperament);
                                rootRef.child(key).child("imageURL").setValue(images);
                                Snackbar.make(rootView, breed+"  added to favorites", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

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
                breedNameText.setText("An error occured! Try pressing 'Another Dog' to reload.");
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
            breedNameText.setText("An error occured! Try pressing 'Another Dog' to reload.");
        }
        try{
            JSONArray jsonarray = new JSONArray(response);
            JSONObject data = jsonarray.getJSONObject(0);
            JSONArray breedsArray= data.getJSONArray("breeds");
            if(breedsArray.length()>0){
                JSONObject breeds = breedsArray.getJSONObject(0);
                breed = breeds.getString("name");
                weight = breeds.getJSONObject("weight").getString("imperial");
                height = breeds.getJSONObject("height").getString("imperial");
                bredFor = breeds.getString("bred_for");
                lifeSpan = breeds.getString("life_span");
                temperament = breeds.getString("temperament");
                imageURL = data.getString("url");
                if (breed != null && imageURL!= null) {
                    List<String> imageURLS=new ArrayList<String>();
                    imageURLS.add(imageURL);
                    Dog dog  = new Dog("",breed,temperament,weight,height,bredFor,lifeSpan,imageURLS);
                    ((MainActivity) getActivity()).getResults(dog);
                    breedNameText.setText(breed);
                    weightText.setText("Weight: " + weight + " pounds");
                    heightText.setText("Height: " + height + " inches");
                    bredforText.setText("Bred for: " + bredFor);
                    lifeExpectancyText.setText("Life Span: " + lifeSpan);
                    temperateText.setText(temperament);
                    imageView.setVisibility(View.VISIBLE);
                    Picasso.get().load(imageURL).into(imageView);
                }
                else{
                    requestData();
                    return;
                }
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

    // invoked when the activity may be temporarily destroyed, save the instance state here
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("breed", breed);
        outState.putString("weight", weight);
        outState.putString("height", height);
        outState.putString("temp", temperament);
        outState.putString("lifeSpan", lifeSpan);
        outState.putString("imageURL", imageURL);
        outState.putString("bredFor", bredFor);
        super.onSaveInstanceState(outState);
    }




}
