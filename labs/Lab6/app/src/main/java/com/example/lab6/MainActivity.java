package com.example.lab6;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

public class MainActivity extends AppCompatActivity implements BrewAdapter.ListItemClickListener {

    @Override
    public void onListItemClick(int position){
        Intent intent = new Intent(MainActivity.this, BrewActivity.class);
        intent.putExtra("brew_id", position);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get recycler view
        RecyclerView recyclerView = findViewById(R.id.recycleView);
        //define an adapter
        BrewAdapter adapter = new BrewAdapter(Brew.brewTypes, this);
        //divider line between rows
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        //assign the adapter to the recycle view
        recyclerView.setAdapter(adapter);
        //set the layout manager of the recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

}
