package com.example.didiorder.biz;

import android.content.Context;

import com.example.didiorder.bean.User;

import java.io.File;

import rx.Observable;

/**
 * Created by qqq34 on 2016/1/11.
 */
public interface IUserBiz {   //接口
     Observable<User> login(Context context , String username, String password); //定义一个接口方法 ，实现这个接口的类需要实现这个方法
    Observable<User> updata(Context context , String name, String job, File file);
}
