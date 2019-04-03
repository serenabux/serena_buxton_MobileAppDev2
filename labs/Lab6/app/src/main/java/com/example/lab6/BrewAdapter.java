package com.example.lab6;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class BrewAdapter extends RecyclerView.Adapter<BrewAdapter.ViewHolder> {

    private List<Brew> brewOptions;

    public interface ListItemClickListener{
        void onListItemClick(int position);
    }

    ListItemClickListener itemClickListener;


    public BrewAdapter(List<Brew> brews, ListItemClickListener brewClickListener){
        brewOptions = brews;
        itemClickListener = brewClickListener;
    }

    @NonNull
    @Override
    public  BrewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View brewView = inflater.inflate(R.layout.list_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(brewView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Brew brew = brewOptions.get(i);
        viewHolder.brewTextView.setText(brew.getName());

    }

    @Override
    public int getItemCount() {
        return brewOptions.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView brewTextView;

        //constructor method
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            brewTextView = itemView.findViewById(R.id.textView);
            brewTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            itemClickListener.onListItemClick(getAdapterPosition());
        }
    }


}
