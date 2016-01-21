package com.example.didiorder.presenter;

import android.content.Context;
import android.util.Log;

import com.example.didiorder.MyApplication;
import com.example.didiorder.adapter.OrderAdapter;
import com.example.didiorder.bean.Order;
import com.example.didiorder.bean.under_order;
import com.example.didiorder.biz.DishesBiz;
import com.example.didiorder.biz.IDishesBiz;
import com.example.didiorder.view.OrderView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by qqq34 on 2016/1/20.
 */
public class OrderActivityPresenter {
    private OrderView orderView;
    private IDishesBiz iDishesBiz;
    private Context context;
    private OrderAdapter orderAdapter;
    private int page = 1;

    public OrderActivityPresenter(OrderView orderView) {
        this.orderView = orderView;
        this.iDishesBiz = new DishesBiz();
        this.context = orderView.getContext();
    }
    public void getDishes(){
        if (page==1){
            orderAdapter=null;
        }
        orderView.setSwipEnable(false);
        iDishesBiz.getDishesList(context,page).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dishes ->{
                    for (int i = 0;i<dishes.size();i++){
                        MyApplication.hashMap.put(dishes.get(i).getObjectId(),dishes.get(i).getPrice());
                    }
                    if (orderAdapter==null){
                        orderAdapter = new OrderAdapter(context,dishes);
                        orderView.SetAdapter(orderAdapter);
                    }else{
                        orderAdapter.addData(dishes);
                    }
                    orderView.isHideSwipe(false);
                    orderView.setSwipEnable(true);
                    Log.d("OrderActivity","加载成功");
                    page++;
                }, throwable -> {
                    orderView.isHideSwipe(false);
                    orderView.setSwipEnable(true);
                    orderView.showSnackBar(throwable.getLocalizedMessage());
                } );

    }
    public void setPage(int page){
        this.page=page;
    }
    public void Updata(String a,String b){
        List<BmobObject> UnderOrderList = new ArrayList<>();
        orderView.setViewEnable(false);
        orderView.hideProgressbar(false);
        Order order = new Order(a,b,false,false, MyApplication.list);
        iDishesBiz.updataDeshes(context,order).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(order1->{
                    for (int i=0;i<order1.getDisheses().size();i++){
                        under_order UnderOrder = new under_order(order1.getDisheses().get(i),order1.getObjectId(),1,MyApplication.hashMap.get(order1.getDisheses().get(i)),false,false,false,b);
                        UnderOrderList.add(UnderOrder);
                    }

                },throwable -> {
                    orderView.showSnackBar(throwable.getLocalizedMessage());
                    orderView.setViewEnable(true);
                    orderView.hideProgressbar(true);
                },()->{
                    iDishesBiz.updataUnderOrder(context,UnderOrderList).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(aBoolean -> {
                                if (aBoolean){
                                    orderView.finishActivity();
                                }
                            },throwable -> {
                                orderView.showSnackBar(throwable.getLocalizedMessage());
                            });
                });
    }

}
