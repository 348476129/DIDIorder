package com.example.didiorder.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import com.example.didiorder.BaseActivity;
import com.example.didiorder.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;

public class LoginActivity extends BaseActivity {
    private  Toolbar toolbar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams. FLAG_TRANSLUCENT_NAVIGATION);
        setContentLayout(R.layout.activity_login);
        initView();
        initClick();
    }
    private void initClick(){
        toolbar.setNavigationOnClickListener(v -> finish());
    }
private void initView(){
    setStarusBarBackground(R.mipmap.status);
    setNavigationBarBackground(R.mipmap.navigation);
    toolbar = getToolbar();
    toolbar.setTitle("登录");
    setSupportActionBar(toolbar);
    toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
}
}
