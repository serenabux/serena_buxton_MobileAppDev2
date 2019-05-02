package com.example.dogsfurfun;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v4.app.Fragment;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Fragment fragment;
    int frag;
   String breed;
   Dog dog;

    public void loadFragment(Fragment fragment){
        if (fragment != null){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayout, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_dog:
                    frag = 0;
                    fragment = new DogFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_favorites:
                    fragment = new FavoritesFragment();
                    frag = 1;
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_adopt:
                    fragment = new AdoptFragment();
                    frag = 2;
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(savedInstanceState != null){
            breed = savedInstanceState.getString("breed");

            int whichFragment = savedInstanceState.getInt("fragment");
            switch (whichFragment){
                case 0:{
                    fragment = new DogFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("breeds", savedInstanceState.getString("breed"));
                    bundle.putString("temp", savedInstanceState.getString("temp") );
                    bundle.putString("weight", savedInstanceState.getString("weight") );
                    bundle.putString("height", savedInstanceState.getString("height") );
                    bundle.putString("lifeSpan", savedInstanceState.getString("lifeSpan") );
                    bundle.putString("bredFor", savedInstanceState.getString("bredFor") );
                    bundle.putStringArrayList("images", savedInstanceState.getStringArrayList("images") );


                    fragment.setArguments(bundle);
                    loadFragment(fragment);
                    break;
                }
                case 1:{
                    loadFragment(new FavoritesFragment());
                    break;
                }
                case 2:{
                    loadFragment(new AdoptFragment());
                    break;
                }
            }
        }else{
            loadFragment(new DogFragment());
        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

//    public void getResults(String newBreed, String newTemp, String newWeight, String newHeight, String newBred, String newLife, List<String> newImageUrls){
//        breed = newBreed;
//    }
        public void getResults(Dog newDog){
            dog = newDog;
        }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("fragment", frag);
        outState.putString("breed", dog.getBreed());
        outState.putString("temp", dog.getTemperat());
        outState.putString("weight", dog.getWeight());
        outState.putString("height", dog.getHeight());
        outState.putString("lifeSpan", dog.getLifeSpan());
        outState.putString("bredFor", dog.getBredFor());
        outState.putStringArrayList("images", dog.getImageURLS());
        super.onSaveInstanceState(outState);
    }



}
