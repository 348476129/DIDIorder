package com.example.didiorder.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.ArrayAdapter;

import com.example.didiorder.BaseActivity;
import com.example.didiorder.R;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by qqq34 on 2016/1/15.
 */
public class UserActivity extends BaseActivity {
    @Bind(R.id.activity_user_spinner)   //做一半发现一个新的绑定控件的方法，这个更简单 就用这个 了
            BetterSpinner spinner2;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentLayout(R.layout.activity_user);
        ButterKnife.bind(this);
        initview();
        initClick();
    }
    private void initview(){
        setStarusBarBackground(R.mipmap.status);
        setNavigationBarBackground(R.mipmap.navigation);
        toolbar = getToolbar();
        toolbar.setTitle("修改资料");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        String[] list = getResources().getStringArray(R.array.job);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, list);
        spinner2.setAdapter(adapter);
    }
    private void initClick(){
        toolbar.setNavigationOnClickListener(v -> finish());
    }
}
