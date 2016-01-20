package com.example.didiorder.view;

import android.content.Context;

import com.example.didiorder.adapter.OrderAdapter;

/**
 * Created by qqq34 on 2016/1/20.
 */
public interface OrderView {
    void isHideSwipe(boolean isHide);

    Context getContext();

    void setSwipEnable(boolean isEnable);

    void showSnackBar(String s);

    void SetAdapter(OrderAdapter adapter);
    void setViewEnable(boolean isEnable);
    void hideProgressbar(boolean isHide);
    void finishActivity();
    }
