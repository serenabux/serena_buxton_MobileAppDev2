package com.example.androidfinal;

import java.util.ArrayList;
import java.util.List;

public class Resturant {
    private String name;
    private String url;

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
        add(new Resturant("Cafe Mexicali", "https://www.cafemexicali.com/"));
        add(new Resturant("Rincon Argentino", "https://www.rinconargentinoboulder.com/"));
        add(new Resturant("Little Tibet", "https://www.littletibetrestaurant.com/"));
    }};
}