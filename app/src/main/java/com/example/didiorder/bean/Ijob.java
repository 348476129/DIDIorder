package com.example.didiorder.bean;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;

import cn.bmob.v3.BmobUser;

/**
 * Created by qqq34 on 2016/1/19.
 */
public abstract class Ijob {
    public User getUser(Context context){
        User user = BmobUser.getCurrentUser(context,User.class);
        return user;
    }
    public abstract void setFloat(FloatingActionButton floatingActionButton);
   public abstract Fragment getFragment();
}
