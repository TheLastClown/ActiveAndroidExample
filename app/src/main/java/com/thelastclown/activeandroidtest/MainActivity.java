package com.thelastclown.activeandroidtest;

import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements AdapterCategory.ClickListenerCategory,
                AdapterItem.ClickListenerItem {

    @BindView(R.id.categories)
    RecyclerView categories;
    @BindView(R.id.items)
    RecyclerView items;

    private List<Category> categoriesList = new ArrayList<>();
    private List<Item> itemList = new ArrayList<>();
    AlertDialog.Builder alert;


    private AdapterCategory adapterCategory;
    private AdapterItem adapterItem;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.LayoutManager mLayoutManager2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        alert = new AlertDialog.Builder(this);

       /* Category restaurants = new Category();
        restaurants.name = "Restaurants";
        restaurants.save();

        categoriesList.add(restaurants);


        Item item = new Item();
        item.category = restaurants;
        item.name = "Outback Steakhouse";
        item.save();

        itemList.add(item);

        item = new Item();
        item.category = restaurants;
        item.name = "Red Robin";
        item.save();

        itemList.add(item);

        item = new Item();
        item.category = restaurants;
        item.name = "Olive Garden";
        item.save();

        itemList.add(item);*/

    }


    @Override
    protected void onStart() {
        super.onStart();

        loadData();

        categories.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        categories.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        adapterCategory.setOnClickListener(this);
        categories.setAdapter(adapterCategory);


        items.setHasFixedSize(true);
        mLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        items.setLayoutManager(mLayoutManager2);
        // specify an adapter (see also next example)
        adapterItem.setOnClickListener(this);
        items.setAdapter(adapterItem);
    }

    @Override
    public void onItemClickItem(View v, Item item) {
        switch (v.getId()){
            case R.id.delete:
                item.delete();
                reloadItem();
                reloadCategory();
                break;
            case R.id.modifyItem:
                item.save();
                reloadItem();
                break;
        }

    }

    @Override
    public void onItemClickCategory(View v, Category category) {
        switch (v.getId()){
            case R.id.deleteCategory:
                List<Item> items = category.item();
                for (int i = 0; i < items.size(); i++){
                    items.get(i).delete();
                }
                category.delete();
                reloadItem();
                reloadCategory();

                break;
            case R.id.modify:
                category.save();
                reloadItem();
                reloadCategory();
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @OnClick({R.id.agregarcategoria, R.id.agregaritem})
    public void OnClickedListener(View v){
        switch (v.getId()){
            case R.id.agregarcategoria:

                final EditText edittext = new EditText(this);

                alert.setMessage("Enter Your Category Name");
                alert.setTitle("Category");
                alert.setView(edittext);
                alert.setCancelable(true);
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //What ever you want to do with the value
                        Toast.makeText(getApplicationContext(), "Saving category" + edittext.getText().toString(), Toast.LENGTH_SHORT).show();
                        Category category = new Category();
                        category.name = edittext.getText().toString();
                        category.save();
                        Log.e("ActivityMain","Saving category: "+ category.name);

                        reloadCategory();
                        //OR
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                    }
                });
                alert.show();


                break;
            case R.id.agregaritem:


                LinearLayout layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText nameItem = new EditText(this);
                nameItem.setHint("Name");
                layout.addView(nameItem);

                final EditText categoryName = new EditText(this);
                categoryName.setHint("Category");
                layout.addView(categoryName);


                final AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("EntertheText:").setView(layout).setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {

                              Item item = new Item();

                              String name = nameItem.getText().toString();
                              if( name != null){
                                  item.name = name;
                              }
                              String category = categoryName.getText().toString();
                              if(  category != null){
                                  Category category1 = dbUtils.getCategoryByName(category);

                                      if(category1 != null){
                                          item.category = category1;
                                          Log.e("adding category:",category);

                                          item.save();
                                          reloadItem();
                                          reloadCategory();
                                      } else {
                                          Toast.makeText(getApplicationContext(), "Category not found", Toast.LENGTH_SHORT).show();
                                      }

                              } else {
                                  Toast.makeText(getApplicationContext(), "Field Required categoryName", Toast.LENGTH_SHORT).show();

                              }
                         /* User clicked OK so do some stuff */
                            }
                        }).setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                             /*
                             * User clicked cancel so do some stuff
                             */
                            }
                        });
                alert.show();




                break;
        }
    }

    public void loadData(){
        categoriesList = dbUtils.getCategories();
        itemList = dbUtils.getItems();

        Log.e("ActivityMain","Reloading data: " + itemList.size() + " : " + categoriesList.size());

        adapterCategory = new AdapterCategory(categoriesList, this);
        adapterItem = new AdapterItem(itemList);


    }

    public void reloadCategory(){
        loadData();
        categories.setAdapter(new AdapterCategory(categoriesList,this));
        categories.invalidate();
    }

    public void reloadItem(){
        loadData();
        items.setAdapter(new AdapterItem(itemList));
        items.invalidate();
    }
}
