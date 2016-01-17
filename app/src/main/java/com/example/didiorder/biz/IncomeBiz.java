package com.example.didiorder.biz;

import android.content.Context;
import android.util.Log;

import com.example.didiorder.bean.User;
import com.example.didiorder.bean.under_order;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindStatisticsListener;
import rx.Observable;

/**
 * Created by qqq34 on 2016/1/17.
 */
public class IncomeBiz implements IIncomeBiz {
    @Override
    public Observable<Integer> Income(Context context) {
        Observable<Integer> observable = Observable.create(subscriber -> {
            BmobQuery<under_order> query = new BmobQuery<under_order>();
//查询playerName叫“比目”的数据
            query.addWhereEqualTo("ispay", true);
            query.sum(new String[] { "price" });
            query.findStatistics(context, under_order.class, new FindStatisticsListener() {
                @Override
                public void onSuccess(Object object) {
                    JSONArray ary = (JSONArray) object;
                    if(ary!=null){//
                        try {
                            JSONObject obj = ary.getJSONObject(0);
                            int sum = obj.getInt("_sumPrice");//_(关键字)+首字母大写的列名
                            subscriber.onNext(sum);
                            subscriber.onCompleted();
                        } catch (JSONException e) {
                            subscriber.onError(new Throwable("查询失败"));
                        }
                    }else{
                        subscriber.onNext(0);
                        subscriber.onCompleted();
                    }
                }

                @Override
                public void onFailure(int i, String s) {
                    subscriber.onError(new Throwable("查询失败"));
                }
            });
        });
        return observable;
    }

    @Override
    public Observable<Integer> tatle(Context context) {
        Observable<Integer> observable = Observable.create(subscriber -> {
            BmobQuery<under_order> query = new BmobQuery<under_order>();
//查询playerName叫“比目”的数据
            query.sum(new String[] { "count" });
            query.findStatistics(context, under_order.class, new FindStatisticsListener() {
                @Override
                public void onSuccess(Object object) {
                    JSONArray ary = (JSONArray) object;
                    if(ary!=null){//
                        try {
                            JSONObject obj = ary.getJSONObject(0);
                            int sum = obj.getInt("_sumCount");//_(关键字)+首字母大写的列名
                            subscriber.onNext(sum);
                            subscriber.onCompleted();
                        } catch (JSONException e) {
                            subscriber.onError(new Throwable("查询失败"));
                        }
                    }else{
                        subscriber.onNext(0);
                        subscriber.onCompleted();
                    }
                }

                @Override
                public void onFailure(int i, String s) {
                    subscriber.onError(new Throwable("查询失败"));
                }
            });
        });
        return observable;
    }
}
