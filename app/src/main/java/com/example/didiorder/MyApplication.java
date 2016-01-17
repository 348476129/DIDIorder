package com.example.didiorder;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by qqq34 on 2016/1/17.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(getApplicationContext());
    }
}
