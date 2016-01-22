package com.example.didiorder.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.example.didiorder.MyApplication;
import com.example.didiorder.adapter.StickListTaskAdapter;
import com.example.didiorder.bean.AllOrder;
import com.example.didiorder.bean.Cooker;
import com.example.didiorder.bean.Dishes;
import com.example.didiorder.bean.Ijob;
import com.example.didiorder.bean.Order;
import com.example.didiorder.bean.User;
import com.example.didiorder.bean.Waiter;
import com.example.didiorder.bean.under_order;
import com.example.didiorder.biz.DishesBiz;
import com.example.didiorder.biz.IDishesBiz;
import com.example.didiorder.tools.ErrorList;
import com.example.didiorder.tools.UtilityTool;
import com.example.didiorder.view.IMainView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by qqq34 on 2016/1/15.
 */
public class MainActivityPresenter {
    private IMainView iMainView;
    private IDishesBiz iDishesBiz;
    private int page = 1;
    private Context context;

    public MainActivityPresenter(IMainView iMainView) {
        this.iMainView = iMainView;
        iDishesBiz = new DishesBiz();
        context = iMainView.getContext();
    }

    public void setImageUri(Uri uri) {
        iMainView.setImageUri(uri);
    }

    public void startNewActivity(Intent intent) {
        iMainView.startNewActivity(intent);
    }

    public void LogOut(Context context) {
        UtilityTool.LogOut(context);
    }

    public Ijob getJob(User user) {
        if (user.getJob().equals("1")) {
            return new Cooker();
        } else if (user.getJob().equals("2")) {
            return new Waiter();
        } else {
            return null;
        }
    }

    public void getOrder() {
        iMainView.isSwipe(true);
        BmobQuery<under_order> orderBmobQuery = new BmobQuery<>();
        List<BmobQuery<Dishes>> orQuerys = new ArrayList<BmobQuery<Dishes>>();
        List<AllOrder> orderList = new ArrayList<>();
        List<AllOrder> orderListFinish = new ArrayList<>();
        List<AllOrder> orderListUnder = new ArrayList<>();
        iDishesBiz.getOrderList(context, page).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(orders -> {
                    for (Order order:orders){
                        orderBmobQuery.addWhereEqualTo("orderId", order.getObjectId());
                        orderBmobQuery.order("-createdAt");
                        orderBmobQuery.findObjects(context, new FindListener<under_order>() {
                            @Override
                            public void onSuccess(List<under_order> list) {
                                List<under_order> listFinish = new ArrayList<under_order>();
                                List<under_order> listUnder = new ArrayList<under_order>();
                                for (int i = 0; i < list.size(); i++) {
                                    if (order.iscomplete()) {
                                        listFinish.add(list.get(i));
                                    } else {
                                        listUnder.add(list.get(i));
                                    }
                                    orQuerys.add(new BmobQuery<Dishes>().addWhereEqualTo("objectId", list.get(i).getDishes()));
                                }
                                if (order.iscomplete()) {
                                    orderListFinish.add(new AllOrder(order, listFinish));
                                } else {
                                    orderListUnder.add(new AllOrder(order, listUnder));
                                }
                                orderList.add(new AllOrder(order, list));
                                Log.d("DEBUG","G:"+orderListFinish.size());
                                Log.d("DEBUG","h:"+orderListUnder.size());
                                BmobQuery<Dishes> mainQuery = new BmobQuery<Dishes>();
                                mainQuery.or(orQuerys);
                                mainQuery.findObjects(context, new FindListener<Dishes>() {
                                    @Override
                                    public void onSuccess(List<Dishes> list) {
                                        for (int i = 0; i < list.size(); i++) {
                                            MyApplication.deshesMap.put(list.get(i).getObjectId(), list.get(i).getName());
                                            MyApplication.imageMap.put(list.get(i).getObjectId(), list.get(i).getImageUrl());
                                        }
                                        if (orderList.size()==orders.size()){
                                            if (page==1){
                                                iMainView.setAdapter(orderList,  orderListUnder,  orderListFinish);
                                                Log.d("DEBUG",""+orderList.size());
                                            }else {
                                                iMainView.addAdapter(orderList,  orderListUnder,  orderListFinish);
                                            }
                                            iMainView.isSwipe(false);
                                        }

                                    }

                                    @Override
                                    public void onError(int i, String s) {
                                        iMainView.isSwipe(false);
                                        iMainView.showError(new ErrorList().getErrorMsg(i));
                                    }
                                });
                            }

                            @Override
                            public void onError(int i, String s) {
                                iMainView.isSwipe(false);
                                iMainView.showError(new ErrorList().getErrorMsg(i));
                            }
                        });
                    }

                },throwable -> {
                    iMainView.isSwipe(false);
                    iMainView.showError(throwable.getMessage());
                });


    }

    public void setPage(int page) {
        this.page = page;
    }
}
