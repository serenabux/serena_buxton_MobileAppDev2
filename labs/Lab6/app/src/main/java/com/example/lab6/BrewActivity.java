package com.example.lab6;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import org.w3c.dom.Text;

public class BrewActivity extends AppCompatActivity {

    int brewID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brew);
        //get brew id from the intent
        int brewNum = (Integer) getIntent().getExtras().get("brew_id");
        Brew brew = Brew.brewTypes.get(brewNum);
        brewID = brewNum;
        //populate image
        ImageView brewImage = (ImageView) findViewById(R.id.brewImageView);
        brewImage.setImageResource(brew.getImageRecourseID());
        //populate description
        TextView brewDescription = (TextView) findViewById(R.id.brewDescription);
        brewDescription.setText(brew.getDescriptionID());

        //get toolbar and set it as the app bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(brew.getName());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate menu add items to the action bar
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        {
            //get the ID of the item on the action bar that was clicked
            switch (item.getItemId()) {
                case R.id.instructions:
                    Intent intent = new Intent(this, InstructionsActivity.class);
                    intent.putExtra("brewID", brewID);
                    startActivity(intent);
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }

    }
}
