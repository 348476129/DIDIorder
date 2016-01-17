package com.example.didiorder.biz;

import android.content.Context;

import com.example.didiorder.bean.User;

import rx.Observable;

/**
 * Created by qqq34 on 2016/1/17.
 */
public interface IIncomeBiz {
    Observable<Integer> Income(Context context);
    Observable<Integer> tatle(Context context);
}
