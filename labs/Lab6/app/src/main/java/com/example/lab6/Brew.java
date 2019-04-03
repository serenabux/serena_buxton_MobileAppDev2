package com.example.lab6;

import java.util.ArrayList;
import java.util.List;

public class Brew {
    private String name;
    private int imageRecourseID;
    private int descriptionID;
    private String url;

    private Brew(String newName, int newImageID, int newDescriptionID, String newURL){
        this.name = newName;
        this.imageRecourseID = newImageID;
        this.descriptionID = newDescriptionID;
        this.url = newURL;

    }

    public static List<Brew> brewTypes = new ArrayList<Brew>(){{
        add(new Brew("Coffee Press", R.drawable.coffeepress, R.string.coffeePress, "https://www.starbucks.com/coffee/how-to-brew/coffee-press"));
        add(new Brew("Pour Over", R.drawable.pourover, R.string.pourOver, "https://www.starbucks.com/coffee/how-to-brew/pour-over"));
        add(new Brew("Iced Pour Over", R.drawable.icedpourover, R.string.icedPourOver, "https://www.starbucks.com/coffee/how-to-brew/iced-pour-over"));
        add(new Brew("Coffee Brewer", R.drawable.coffeebrewer, R.string.coffeeBrewer, "https://www.starbucks.com/coffee/how-to-brew/coffee-brewer"));
    }};

    public String getName(){
        return name;
    }

    public  int getImageRecourseID(){
        return imageRecourseID;
    }

    public int getDescriptionID(){
        return descriptionID;
    }

    public String getUrl(){
        return url;
    }

    public String toString(){
        return this.name;
    }

}
