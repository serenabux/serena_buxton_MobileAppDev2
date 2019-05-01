package com.example.dogsfurfun;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;




/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {

    //array list of recipes
    List<Dog> breeds = new ArrayList<>();
    //Firebase database dogBreed node reference
    DatabaseReference reciperef = FirebaseDatabase.getInstance().getReference("DogsFurFun");
    //define an adapter
    FirebaseRecyclerAdapter adapter;

    public FavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);
        //define a query
        Query query = FirebaseDatabase.getInstance().getReference().child("DogBreeds");
        //define a parser
        SnapshotParser<Dog> parser = new SnapshotParser<Dog>() {
            @NonNull
            @Override
            public Dog parseSnapshot(@NonNull DataSnapshot snapshot) {
                String key = snapshot.getKey();
                String breedName = snapshot.child("breedName").getValue().toString();
                String weight = snapshot.child("weight").getValue().toString();
                String height = snapshot.child("height").getValue().toString();
                String lifeSpan = snapshot.child("lifeSpan").getValue().toString();
                String temperament = snapshot.child("temperament").getValue().toString();
                String bredFor = snapshot.child("bredFor").getValue().toString();
                 Iterable<DataSnapshot> imageUrlsData = snapshot.child("imageURL").getChildren();
                List<String> imageURLS=new ArrayList<String>();;
                for(DataSnapshot data: imageUrlsData){
                    imageURLS.add(data.getValue().toString());
                }
                Dog newBreed = new Dog(key,breedName,temperament,weight,height,bredFor,lifeSpan,imageURLS);
                breeds.add(newBreed);
                return newBreed;
            }
        };


        //define adapter options
        FirebaseRecyclerOptions<Dog> options = new
                FirebaseRecyclerOptions.Builder<Dog>().setQuery(query, parser).build();
//create an adapter
        adapter = new FirebaseRecyclerAdapter<Dog, breedViewHolder>(options) {
            @NonNull
            @Override
            public breedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item,
                        viewGroup, false);
                return new breedViewHolder(view);
            }
            @Override
            protected void onBindViewHolder(@NonNull breedViewHolder holder, int position, @NonNull
                    Dog model) {
                holder.setRecipeName(model.getBreed());
            }
        };

        // get the recyclerview
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);

        //divider line between rows
        recyclerView.addItemDecoration(new DividerItemDecoration(rootView.getContext(),
                LinearLayoutManager.VERTICAL));
//assign the adapter to the recycle view
        recyclerView.setAdapter(adapter);

        //set a layout manager on the recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }


}
