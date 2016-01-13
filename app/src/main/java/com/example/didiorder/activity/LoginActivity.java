package com.example.didiorder.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import com.example.didiorder.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;

public class LoginActivity extends AppCompatActivity {
    private  Toolbar toolbar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams. FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.activity_login);
        initView();
    }
private void initView(){
    SystemBarTintManager tintManager = new SystemBarTintManager(this);
    tintManager.setStatusBarTintEnabled(true);
    tintManager.setNavigationBarTintEnabled(true);
    tintManager.setNavigationBarTintResource(R.mipmap.navigation);
    tintManager.setStatusBarTintResource(R.mipmap.status);
    toolbar = (Toolbar) findViewById(R.id.toolbar);
    toolbar.setTitle("登录");
    setSupportActionBar(toolbar);
}
}
