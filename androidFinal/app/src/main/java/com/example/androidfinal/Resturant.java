package com.example.androidfinal;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class Resturant {
    private String name;
    private String url;

    private static final String PREFS_NAME = "RESTAURANTS";

    Resturant(String newName, String newURL){
        name = newName;
        url = newURL;
    }

    public String getName(){
        return name;
    }

    public String getUrl() {
        return url;
    }

    public static List<Resturant> boulderFood = new ArrayList<Resturant>(){{
//        add(new Resturant("Cafe Mexicali", "https://www.cafemexicali.com/"));
//        add(new Resturant("Rincon Argentino", "https://www.rinconargentinoboulder.com/"));
//        add(new Resturant("Little Tibet", "https://www.littletibetrestaurant.com/"));
    }};

    public static void storeData(Context context) {
//get access to a shared preferences file
        SharedPreferences sharedPrefs = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
//create an editor to write to the shared preferences file
        SharedPreferences.Editor editor = sharedPrefs.edit();
//add size to the editor
        editor.putInt("size", boulderFood.size());
//add items to the editor
        for (int i = 0; i < boulderFood.size(); i++) {
            editor.putString("restaurant" + i, boulderFood.get(i).getName());
            editor.putString("url" + i, boulderFood.get(i).getUrl());
        }
        editor.apply();
    }

    public static void loadData(Context context) {
//get access to a shared preferences file
        SharedPreferences sharedPrefs = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
//create an editor to read from the shared preferences file
        SharedPreferences.Editor editor = sharedPrefs.edit();
        int size = sharedPrefs.getInt("size", 0);
        if(size>0) {
            for (int i = 0; i < size; i++) {
                boulderFood.add(new Resturant(sharedPrefs.getString("restaurant" + i, ""),sharedPrefs.getString("url" + i, "")));
            }
        }
    }
}