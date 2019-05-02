package com.example.dogsfurfun;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("DogBreeds");
    //define an adapter
    FirebaseRecyclerAdapter adapter;

    public FavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);
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
            protected void onBindViewHolder(@NonNull final breedViewHolder holder, int position, @NonNull
                    final Dog model) {
                holder.setBreedName(model.getBreed());

                //onclick listener
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //get breed that was pressed
                        int position = holder.getAdapterPosition();
                        //reference: https://stackoverflow.com/questions/37891277/get-current-activity-from-fragment
                        //reference: https://stackoverflow.com/questions/28984879/how-to-open-a-different-fragment-on-recyclerview-onclick
                        //reference:https://stackoverflow.com/questions/34310592/how-open-fragment-from-recyclerview-adaptercardadapter-viewholder

                        Bundle bundle = new Bundle();
                        bundle.putString("breed", breeds.get(position).getBreed());
                        bundle.putString("weight", breeds.get(position).getWeight());
                        bundle.putString("height", breeds.get(position).getHeight());
                        bundle.putString("lifeSpan", breeds.get(position).getLifeSpan());
                        bundle.putString("temp", breeds.get(position).getTemperat());
                        bundle.putString("bredFor", breeds.get(position).getBredFor());
                        bundle.putStringArrayList("urls", breeds.get(position).getImageURLS());

                        Fragment newFragment = new DogBreedDetailFragment();
                        newFragment.setArguments(bundle);

                        ((MainActivity)getActivity()).loadFragment(newFragment);
                        //activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();

                    }
                });


                //Deal with delete
                //context menu
                holder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                    @Override
                    public void onCreateContextMenu(ContextMenu menu, final View v, ContextMenu.ContextMenuInfo menuInfo) {
                        //set the menu title
                        menu.setHeaderTitle("Delete " + model.getBreed());
                        //add the choices to the menu
                        menu.add(1, 1, 1, "Yes").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                //get breed that was pressed
                                int position = holder.getAdapterPosition();
                                //get breed id
                                String breedId = breeds.get(position).getId();
                                //delete from Firebase
                                ref.child(breedId).removeValue();
                                Snackbar.make(v, "Item removed", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();

                                return false;
                            }
                        });
                        menu.add(2, 2, 2, "No");
                    }
                });
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
