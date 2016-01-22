package com.example.didiorder.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.example.didiorder.adapter.StickListTaskAdapter;
import com.example.didiorder.bean.AllOrder;
import com.example.didiorder.bean.Ijob;

import java.util.List;

/**
 * Created by qqq34 on 2016/1/15.
 */
public interface  IMainView {
    void setImageUri(Uri uri);
    void startNewActivity(Intent intent);
    Context getContext();
    void setAdapter(List<AllOrder> orderLista  , List<AllOrder> orderListb, List<AllOrder> orderListc);
    void showError(String s);
    Ijob getJob();
    void addAdapter(List<AllOrder> orderLista  , List<AllOrder> orderListb, List<AllOrder> orderListc);
        void isSwipe(boolean swipe);
        }
