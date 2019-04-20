package com.example.lab8;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private Realm realm;
    private GroceryAdapter groceryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        realm = Realm.getDefaultInstance();

        RealmResults<item> items = realm.where(item.class).findAll();
        groceryAdapter = new GroceryAdapter(items,this);

        RecyclerView recyclerView = findViewById(R.id.groceriesRecyclerView);

        recyclerView.setAdapter(groceryAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout layout = new LinearLayout(MainActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                //create edit texts
                final EditText itemEditText = new EditText(MainActivity.this);
                itemEditText.setHint("Grocery Item Name");
                layout.addView(itemEditText);
                final EditText sectionEditText = new EditText(MainActivity.this);
                sectionEditText.setHint("Section of the grocery store?");
                layout.addView(sectionEditText);

                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Add Grocery Item");
                dialog.setView(layout);
                dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String newGroceryName = itemEditText.getText().toString();
                        final String newSectionName = sectionEditText.getText().toString();
                        if(!newGroceryName.isEmpty()){
                            addGrocery(UUID.randomUUID().toString(),newGroceryName, newSectionName);
                        }
                    }
                });
                dialog.setNegativeButton("Cancel",null);
                dialog.show();
            }
        });
    }

    public void changeGroceryBought(final String itemId){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                item groceryItem = realm.where(item.class).equalTo("id",itemId).findFirst();
                groceryItem.setGot(!groceryItem.getGot());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addGrocery(final String newId, final String newItem, final String newSection){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                item newItemObject = realm.createObject(item.class, newId);
                newItemObject.setGroceryItem(newItem);
                newItemObject.setSection(newSection);
            }
        });
    }

    private void changeItem(final String itemId, final String item_name, final String section_name){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                item gitem = realm.where(item.class).equalTo("id", itemId).findFirst();
                gitem.setGroceryItem(item_name);
                gitem.setSection(section_name);
            }
        });
    }

    public void editItem(final String itemId){
        LinearLayout layout = new LinearLayout(MainActivity.this);
        layout.setOrientation(LinearLayout.VERTICAL);
        item gitem = realm.where(item.class).equalTo("id",itemId).findFirst();
        final EditText itemEditText = new EditText(MainActivity.this);
        itemEditText.setText(gitem.getGroceryItem());
        final EditText sectionEditText = new EditText(MainActivity.this);
        sectionEditText.setText(gitem.getSection());
        layout.addView(itemEditText);
        layout.addView(sectionEditText);

        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("Edit Grocery Item");
        dialog.setView(layout);
        dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String updatedGroceryItem = itemEditText.getText().toString();
                final String updatedSection = sectionEditText.getText().toString();
                if(!updatedGroceryItem.isEmpty()){
                    changeItem(itemId,updatedGroceryItem,updatedSection);
                }
            }
        });
        dialog.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteItem(itemId);
            }
        });
        dialog.create();
        dialog.show();
    }

    private void deleteItem(final String itemId) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(item.class).equalTo("id", itemId)
                        .findFirst()
                        .deleteFromRealm();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
