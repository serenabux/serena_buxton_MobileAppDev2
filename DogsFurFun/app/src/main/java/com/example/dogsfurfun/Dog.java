package com.example.dogsfurfun;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Dog {
    private String id;
    private String breed;
    private String temperat;
    private String weight;
    private String height;
    private String bredFor;
    private String lifeSpan;
    private List<String> imageURLS = new ArrayList<String>();

    public Dog(){
        // Default constructor required for calls to DataSnapshot.getValue(Recipe.class)
    }

    public Dog(String newId, String newBreed, String newTemp, String newWeight, String newHeight, String newBred, String newLife, List<String> newImageUrls){
        id = newId;
        breed = newBreed;
        temperat = newTemp;
        weight = newWeight;
        height = newHeight;
        bredFor = newBred;
        lifeSpan = newLife;
        for (String url:newImageUrls){
            imageURLS.add(url);
        }
    }
    public String getBreed() {
        return breed;
    }

    public String getId() {
        return id;
    }

    public String getTemperat() {
        return temperat;
    }

    public String getBredFor() {
        return bredFor;
    }

    public String getLifeSpan() {
        return lifeSpan;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

    public List<String> getImageURLS() {
        return imageURLS;
    }
}
