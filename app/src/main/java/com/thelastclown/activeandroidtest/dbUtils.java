package com.thelastclown.activeandroidtest;

import android.util.Log;

import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by c02stfgvfvh3 on 2/11/17.
 */

public class dbUtils {

    public static List<Category> getCategories(){
        return new Select().from(Category.class).execute();
    }

    public static List<Item> getItems(){
        return new Select().from(Item.class).execute();
    }

    public static  Category getCategoryByName (String name){
        Log.e("adding category name:",name);

        Category category = new Select().from(Category.class).where("Name = ?", name).executeSingle();
        Log.e("adding category:",category.name);

        return  category;
    }
}
