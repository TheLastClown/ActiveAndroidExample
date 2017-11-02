package com.thelastclown.activeandroidtest;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by c02stfgvfvh3 on 2/11/17.
 */

public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.AdapterCategory_ViewHolder> {
    
    
    private List<Category> categoryList;
    private Context activity;
    public static ClickListenerCategory listner;

    public AdapterCategory(List<Category> category, Context activity) {
        this.categoryList = category;
        this.activity = activity;
    }

    @Override
    public AdapterCategory.AdapterCategory_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new AdapterCategory.AdapterCategory_ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterCategory_ViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.bindTitular(category, activity);
    }
    

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public void setOnClickListener(ClickListenerCategory listener) {
        this.listner = listener;
    }

    public static class AdapterCategory_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.name)
        EditText name;
        @BindView(R.id.itemlist)
        Spinner itemList;
        @BindView(R.id.deleteCategory)
        Button delete;
        @BindView(R.id.modify)
        Button modify;


        Category categoryActual;

        AdapterCategory_ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindTitular(final Category category, Context activity){

            name.setText(category.name);

            categoryActual = category;

            // Spinner Drop down elements
            List<Item> categories = category.item();
            ArrayList<String> itemsCategory = new ArrayList<>();

            for (int i = 0 ; i < categories.size(); i++){

                itemsCategory.add(categories.get(i).name);

            }
            // Creating adapter for spinner
            ArrayAdapter adapter = new ArrayAdapter(activity.getApplicationContext(), android.R.layout.simple_spinner_item, itemsCategory);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Drop down layout style - list view with radio button
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            itemList.setAdapter(adapter);

            delete.setOnClickListener(this);
            modify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!TextUtils.isEmpty(name.getText())){

                        category.name = name.getText().toString();

                    } else {
                        name.setError("Field required");
                    }
                }
            });


        }

        @Override
        public void onClick(View v) {
            if(listner!=null) {
                listner.onItemClickCategory(v,categoryActual );
            }
        }
    }

    public interface ClickListenerCategory {
        void onItemClickCategory(View v, Category category);
    }
}
