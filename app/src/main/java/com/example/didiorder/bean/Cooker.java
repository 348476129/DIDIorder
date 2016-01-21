package com.example.didiorder.bean;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.didiorder.fragment.FragmentOrder;

/**
 * Created by qqq34 on 2016/1/19.
 */
public class Cooker extends Ijob{
    @Override
    public void setFloat(FloatingActionButton floatingActionButton) {
        floatingActionButton.setVisibility(View.GONE);
    }

    @Override
    public FragmentOrder getFragment() {
        return new FragmentOrder();
    }
}
