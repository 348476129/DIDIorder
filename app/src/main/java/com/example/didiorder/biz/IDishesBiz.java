package com.example.didiorder.biz;

import android.content.Context;

import com.example.didiorder.bean.Dishes;
import com.example.didiorder.bean.Order;
import com.example.didiorder.bean.User;
import com.example.didiorder.bean.under_order;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobObject;
import rx.Observable;

/**
 * Created by qqq34 on 2016/1/19.
 */
public interface IDishesBiz {
    Observable<Dishes> updata(Context context , String name, Integer price, File file);
    Observable<List<Dishes>> getDishesList(Context context,int page);
    Observable<Order> updataDeshes(Context context, Order order);
    Observable<Boolean> updataUnderOrder(Context context, List<BmobObject> lis);
    Observable<List<Order>> getOrderList(Context context,int page);
}
