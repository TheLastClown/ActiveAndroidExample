package com.thelastclown.activeandroidtest;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by c02stfgvfvh3 on 2/11/17.
 */

@Table(name = "Item")
public class Item extends Model {

    @Column(name = "Name")
    public String name;

    @Column(name = "Category")
    public Category category;

    public Item() {
        super();
    }
}