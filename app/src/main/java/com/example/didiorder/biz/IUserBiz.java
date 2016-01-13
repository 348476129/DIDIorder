package com.example.didiorder.biz;

import android.content.Context;

import com.example.didiorder.bean.User;

import rx.Observable;

/**
 * Created by qqq34 on 2016/1/11.
 */
public interface IUserBiz {   //接口
    public Observable<User> login(Context context , String username, String password); //定义一个接口方法 ，实现这个接口的类需要实现这个方法
}
