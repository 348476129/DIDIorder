package com.example.didiorder.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.a.a.a.V;
import com.example.didiorder.BaseActivity;
import com.example.didiorder.R;

import butterknife.ButterKnife;

/**
 * Created by qqq34 on 2016/1/19.
 */
public class OrderActivity extends BaseActivity {
    private Context context;
    private Toolbar toolbar;
    private FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_order);
        ButterKnife.bind(this);
        context = this;
        initview();
        initClick();
    }
    private void initview(){
        setStarusBarBackground(R.mipmap.status);
        setNavigationBarBackground(R.mipmap.navigation);
        toolbar = getToolbar();
        toolbar.setTitle("新客人");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        floatingActionButton = getFloatingActionButton();
        floatingActionButton.setVisibility(View.VISIBLE);
    }
    private void initClick(){
        toolbar.setNavigationOnClickListener(v -> finish());
    }
}
