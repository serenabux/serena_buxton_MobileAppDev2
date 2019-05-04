package com.example.androidfinal;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ResturantAdapter extends RecyclerView.Adapter<ResturantAdapter.ViewHolder>{
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView restuarntTextView;

        //constructor method
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            restuarntTextView = itemView.findViewById(R.id.textView);
            restuarntTextView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            itemClickListener.onListItemClick(getAdapterPosition());
        }
    }

    private List<Resturant> resturants;

    public ResturantAdapter(List<Resturant> newResturants, ListItemClickListener resturantClickListener){
        resturants = newResturants;
        itemClickListener = resturantClickListener;
    }

    @NonNull
    @Override
    public ResturantAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View bulbView = inflater.inflate(R.layout.list_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(bulbView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ResturantAdapter.ViewHolder viewHolder, int i) {
        Resturant resturant = resturants.get(i);
        viewHolder.restuarntTextView.setText(resturant.getName());
    }

    @Override
    public int getItemCount() {
        return resturants.size();
    }

    public interface ListItemClickListener{
        void onListItemClick(int position);
    }

    ListItemClickListener itemClickListener;

}