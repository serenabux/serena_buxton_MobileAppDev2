package com.example.lab8;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class GroceryAdapter extends RealmRecyclerViewAdapter<item, GroceryAdapter.ViewHolder> {

    private MainActivity activity;

    public GroceryAdapter(RealmResults<item> data, MainActivity activity){
        super(data, true);
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = layoutInflater.inflate(R.layout.list_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final GroceryAdapter.ViewHolder viewHolder, int i) {
        item listItem = getItem(i);
        viewHolder.itemName.setText(listItem.getGroceryItem());
        viewHolder.sectionName.setText(listItem.getSection());
        viewHolder.hasBought.setTag(i);
        viewHolder.hasBought.setChecked(listItem.getGot());

        viewHolder.hasBought.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //makes sure this is only called for the current checkbox
                //solves the problem of this being called too many times
                if (buttonView.isShown()){
                    int position = (Integer) viewHolder.hasBought.getTag();
                    item listItem = getItem(position);
                    activity.changeGroceryBought(listItem.getId());
                }
            }
        });
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer) viewHolder.hasBought.getTag();
                item listItem = getItem(position);
                activity.editItem(listItem.getId());
            }
        });
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer) viewHolder.hasBought.getTag();
                item gitem = getItem(position);
                activity.editItem(gitem.getId());
            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView sectionName;
        CheckBox hasBought;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.nameTextView);
            sectionName = itemView.findViewById(R.id.sectionTextView);
            hasBought = itemView.findViewById(R.id.checkBox);
        }
    }

}
