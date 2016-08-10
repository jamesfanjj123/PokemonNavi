package com.example.fanjunjie.pokemonnavi;

import android.app.Application;
import android.content.Context;

/**
 * Created by revanth on 8/9/16.
 */
public class MyApplication extends Application {
    private static MyApplication  instance;
    public MyApplication()
    {
        instance = this;
    }
    public static Context getContext()
    {
        return instance;
    }
}
