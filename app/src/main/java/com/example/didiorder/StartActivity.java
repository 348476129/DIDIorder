package com.example.didiorder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.example.didiorder.activity.LoginActivity;
import com.example.didiorder.presenter.StartActivityPresenter;
import com.example.didiorder.view.IStartView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.gc.materialdesign.views.ButtonRectangle;
import com.readystatesoftware.systembartint.SystemBarTintManager;

public class StartActivity extends AppCompatActivity implements IStartView {
    private ButtonRectangle LoginButton ;
    private Context context;
    private StartActivityPresenter startActivityPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(StartActivity.this);
        context = StartActivity.this;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams. FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.activity_start);
        startActivityPresenter = new StartActivityPresenter(this);
        initView();
        initClick();
    }

    private void initClick(){
        LoginButton.setOnClickListener(v ->startActivityPresenter.StartButtonClicked());
    }
    private void initView(){
        LoginButton = (ButtonRectangle)findViewById(R.id.login_button);
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setNavigationBarTintResource(R.mipmap.navigation);
        tintManager.setStatusBarTintColor(0x00ffffff);
    }

    @Override
    public void startLoginActivity() {
        startActivity(new Intent(context,LoginActivity.class));
    }
}
