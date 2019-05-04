package com.example.androidfinal;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
    public void onBindViewHolder(@NonNull final ResturantAdapter.ViewHolder viewHolder, int i) {
        final Resturant resturant = resturants.get(i);
        viewHolder.restuarntTextView.setText(resturant.getName());

        //long click listener
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener(){

            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });

        //context menu
        viewHolder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener(){
            @Override
            public void onCreateContextMenu(ContextMenu menu, final View v, ContextMenu.ContextMenuInfo menuInfo) {
                //set the menu title
                menu.setHeaderTitle("Delete " + resturant.getName());
                //add the choices to the menu
                menu.add(1, 1, 1, "Yes").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        deleteResturant(viewHolder.getAdapterPosition());
                        Snackbar.make(v, "Item removed", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        return false;
                    }
                });
                menu.add(2, 2, 2, "No");
            }
        });


    }

    @Override
    public int getItemCount() {
        return resturants.size();
    }

    public interface ListItemClickListener{
        void onListItemClick(int position);
    }

    ListItemClickListener itemClickListener;



    public void addResturant(String newResturant, String newUrl){
        Resturant.boulderFood.add(new Resturant(newResturant, newUrl));
        notifyItemInserted(getItemCount());
    }


    public void deleteResturant(int position){
        Resturant.boulderFood.remove(position);
        notifyItemRemoved(position);
    }

}