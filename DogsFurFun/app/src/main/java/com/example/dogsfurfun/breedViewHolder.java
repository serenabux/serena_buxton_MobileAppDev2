package com.example.dogsfurfun;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.dogsfurfun.R;

public class breedViewHolder extends RecyclerView.ViewHolder {
    private TextView breedName;
    public breedViewHolder(@NonNull View itemView) {
        super(itemView);
        breedName = itemView.findViewById(R.id.listBreedText);
    }
    public void setRecipeName(String name){
        breedName.setText(name);
    }
}
