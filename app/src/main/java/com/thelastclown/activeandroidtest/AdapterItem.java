package com.thelastclown.activeandroidtest;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by c02stfgvfvh3 on 2/11/17.
 */

public class AdapterItem extends RecyclerView.Adapter<AdapterItem.AdapterItem_ViewHolder> {


    private List<Item> ItemList;
    public static ClickListenerItem listner;

    public AdapterItem(List<Item> dispositivos) {
        this.ItemList = dispositivos;
    }

    @Override
    public AdapterItem.AdapterItem_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_item, parent, false);
        return new AdapterItem.AdapterItem_ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterItem_ViewHolder holder, int position) {
        Item dispositivos = ItemList.get(position);
        holder.bindTitular(dispositivos);
    }


    @Override
    public int getItemCount() {
        return ItemList.size();
    }

    public void setOnClickListener(ClickListenerItem listener) {
        this.listner = listener;
    }

    public static class AdapterItem_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        @BindView(R.id.name)
        EditText name;
        @BindView(R.id.category)
        EditText category;
        @BindView(R.id.delete)
        Button delete;
        @BindView(R.id.modifyItem)
        Button modify;

        Item itemActual;

        AdapterItem_ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindTitular(final Item item ){
            name.setText(item.name);
            category.setText(item.category.name);
            delete.setOnClickListener(this);
            modify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!TextUtils.isEmpty(name.getText())){
                        item.name = name.getText().toString();
                        item.save();
                    } else {
                        name.setError("Field required");
                    }
                    if(!TextUtils.isEmpty(category.getText())){
                        Category categoryNew;
                        categoryNew = dbUtils.getCategoryByName( category.getText().toString());

                        if(categoryNew != null){

                            item.category = categoryNew;
                            item.save();

                        } else {
                            category.setError("No existe categoria con este nombre");
                        }
                    } else {
                        name.setError("Field required");
                    }
                }
            });

            itemActual = item;
        }

        @Override
        public void onClick(View v) {
            if(listner!=null) {
                listner.onItemClickItem(v, itemActual);
            }
        }
    }

    public interface ClickListenerItem {
        void onItemClickItem(View v, Item itemActual);
    }
}

