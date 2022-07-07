package com.kevin.breatheezy;

import com.orhanobut.hawk.Hawk;

public class Application extends android.app.Application{

    static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        Hawk.init(getApplicationContext()).build();
    }

    public static Application getInstance() {
        if (instance == null)
            instance = new Application();
        return instance;
    }
}

