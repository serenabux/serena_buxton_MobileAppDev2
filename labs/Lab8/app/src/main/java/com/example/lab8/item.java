package com.example.lab8;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class item extends RealmObject {
    @Required
    @PrimaryKey
    private String id;
    private String groceryItem;
    private String section;
    private boolean got;

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getGroceryItem(){
        return groceryItem;
    }

    public void setGroceryItem(String gitem){
        this.groceryItem = gitem;
    }

    public String getSection(){
        return section;
    }

    public void setSection(String sec){
        this.section = sec;
    }

    public boolean getGot(){
        return got;
    }

    public void setGot(boolean done){
        this.got = done;
    }
}
