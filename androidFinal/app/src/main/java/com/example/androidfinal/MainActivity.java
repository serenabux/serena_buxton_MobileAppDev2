package com.example.androidfinal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class MainActivity extends AppCompatActivity implements ResturantAdapter.ListItemClickListener {

    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get the recycler view
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        //define an adapter
        final ResturantAdapter adapter = new ResturantAdapter(Resturant.boulderFood, this);

        //divider line between rows
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        //assign the adapter to the recycle view
        recyclerView.setAdapter(adapter);

        //set a layout manager on the recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //load data
        if(Resturant.boulderFood.isEmpty()) {
            Resturant.loadData(this);
        }

        //load JSON data
        requestData(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create a vertical linear layout to hold edit texts
                LinearLayout layout = new LinearLayout(MainActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                final EditText resturantEditText = new EditText(MainActivity.this);
                resturantEditText.setHint("Resturant Name");
                layout.addView(resturantEditText);
                final EditText urlEditText = new EditText(MainActivity.this);
                urlEditText.setHint("URL Name");
                layout.addView(urlEditText);
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Add Resturant");
                dialog.setView(layout);
                //sets OK action
                dialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //get item entered
                        String newResturant = resturantEditText.getText().toString();
                        String newURL = urlEditText.getText().toString();
                        if (!newResturant.isEmpty() && !newURL.isEmpty()) {
                            // add item
                            adapter.addResturant(newResturant,newURL);
                        }
                    }
                });
                //sets cancel action
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // cancel
                    }
                });
                //present alert dialog
                dialog.show();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, ResturantWeb.class);
        intent.putExtra("rest_id", position);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
//save data
        Resturant.storeData(this);
    }

    private void requestData(final ResturantAdapter adapter){

        queue = Volley.newRequestQueue(this);
        //create URL for request
        String json_url = "https://creative.colorado.edu/~apierce/samples/data/yelp_restaurants.json";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, json_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseJSON(response, adapter);
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

    private void parseJSON(String response, ResturantAdapter adapter){
        //response should be a String with JSON objects
        if (response == null) {
            response = "THERE WAS AN ERROR";
        }

        //parse JSON object
        try {
            JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
            JSONArray resturantData = object.getJSONArray("restaurants");
            for (int i = 0; i < resturantData.length(); i++) {
                JSONObject food = resturantData.getJSONObject(i);
                String foodName = food.getString("name");
                String foodUrl = food.getString("url");
                adapter.addResturant(foodName,foodUrl);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




}
