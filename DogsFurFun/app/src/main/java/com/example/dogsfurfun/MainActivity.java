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

public class MainActivity extends AppCompatActivity {

    Fragment fragment;
    int frag;

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
                    Bundle bundle = new Bundle();
                    bundle.putInt("setUp", 0);
                    fragment = new DogFragment();
                    fragment.setArguments(bundle);
                    frag = 0;
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
            int whichFragment = savedInstanceState.getInt("fragment");
            switch (whichFragment){
                case 0:{
                    loadFragment(new DogFragment());
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("fragment", frag);
        super.onSaveInstanceState(outState);
    }



}
