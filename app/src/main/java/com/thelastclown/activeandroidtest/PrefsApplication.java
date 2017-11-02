package com.thelastclown.activeandroidtest;


import com.activeandroid.ActiveAndroid;

/**
 * Created by c02stfgvfvh3 on 2/11/17.
 */
public class PrefsApplication extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ActiveAndroid.initialize(this);
    }
}
