package com.example.dogsfurfun;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DogBreedDetailFragment extends Fragment {

    String urlTest;
    ImageView imageView;
    TextView breedNameText;
    TextView temperateText;
    TextView weightText;
    TextView heightText;
    TextView lifeExpectancyText;
    TextView bredforText;


    public DogBreedDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dog_breed_detail, container, false);
        imageView = rootView.findViewById(R.id.imageView);
        breedNameText = rootView.findViewById(R.id.breedTextView);
        weightText = rootView.findViewById(R.id.weightTextView);
        heightText = rootView.findViewById(R.id.heightTextView);
        bredforText = rootView.findViewById(R.id.bredTextView);
        temperateText = rootView.findViewById(R.id.temperatTextView);
        lifeExpectancyText = rootView.findViewById(R.id.lifeExpectancyTextView);
        Bundle test = getArguments();
        if (getArguments() != null) {
            String breed = getArguments().getString("breed");
            String weight = getArguments().getString("weight");
            String height = getArguments().getString("height");
            String lifeSpan = getArguments().getString("lifeSpan");
            String temp = getArguments().getString("temp");
            String bredFor = getArguments().getString("bredFor");
            ArrayList<String> imageURLS = new ArrayList<String>();
            for (String url: getArguments().getStringArrayList("urls")){
                imageURLS.add(url);
            }

            Picasso.get().load(imageURLS.get(0)).into(imageView);
            breedNameText.setText(breed);
            weightText.setText("Weight: " + weight + " pounds");
            heightText.setText("Height: " + height + " inches");
            bredforText.setText("Bred for: " + bredFor);
            lifeExpectancyText.setText("Life Span: " + lifeSpan);
            temperateText.setText(temp);
        }

        return rootView;
    }


}
