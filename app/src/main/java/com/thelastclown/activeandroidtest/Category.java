package com.thelastclown.activeandroidtest;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

/**
 * Created by c02stfgvfvh3 on 2/11/17.
 */

@Table(name = "Categories")
public class Category extends Model {
    @Column(name = "Name")
    public String name;

    // This method is optional, does not affect the foreign key creation.
    public List<Item> item() {
        return getMany(Item.class, "Category");
    }


    public Category() {
        super();
    }
}