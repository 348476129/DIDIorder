package com.example.didiorder.bean;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;

import com.example.didiorder.adapter.StickListTaskAdapter;
import com.example.didiorder.fragment.FragmentOrder;

import java.util.List;

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
   public abstract FragmentOrder[] getFragment();
    public abstract void setAdapter(Context context,List<AllOrder> orderLista  , List<AllOrder> orderListb, List<AllOrder> orderListc);
    public abstract void addAdapter(List<AllOrder> orderLista  , List<AllOrder> orderListb, List<AllOrder> orderListc);
}
