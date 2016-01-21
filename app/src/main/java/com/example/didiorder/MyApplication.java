package com.example.didiorder;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by qqq34 on 2016/1/17.
 */
public class MyApplication extends Application {
    public static List<String> list;
    public  static HashMap<String,Integer> hashMap;
    public static  HashMap<String,String> deshesMap;
    public static  HashMap<String,String> imageMap;
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(getApplicationContext());
        list = new ArrayList<>();
        hashMap = new HashMap<>();
        deshesMap=new HashMap<>();
        imageMap =new HashMap<>();
    }
}
