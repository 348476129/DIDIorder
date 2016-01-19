package com.example.didiorder.biz;

import android.content.Context;

import com.example.didiorder.bean.Dishes;
import com.example.didiorder.bean.User;

import java.io.File;

import rx.Observable;

/**
 * Created by qqq34 on 2016/1/19.
 */
public interface IDishesBiz {
    Observable<Dishes> updata(Context context , String name, Integer price, File file);
}
