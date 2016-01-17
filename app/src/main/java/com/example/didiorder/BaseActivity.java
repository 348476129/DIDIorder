package com.example.didiorder;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.readystatesoftware.systembartint.SystemBarTintManager;

public class BaseActivity extends AppCompatActivity {
private SystemBarTintManager tintManager;
    private View contentView;
    private LinearLayout ly_content;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams. FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.activity_base);
        ly_content = (LinearLayout) findViewById(R.id.ly_content);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
         tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
    }
    /***
     * 设置内容区域
     *
     * @param resId
     *            资源文件ID
     */
    public void setContentLayout(int resId) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(resId, null);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT);
        contentView.setLayoutParams(layoutParams);
        contentView.setBackgroundDrawable(null);
        if (null != ly_content) {
            ly_content.addView(contentView);
        }

    }
    public void setStarusBarBackground(int id){
        tintManager.setStatusBarTintResource(id);
    }
    public void setNavigationBarBackground(int id){
        tintManager.setNavigationBarTintResource(id);
    }
    public Toolbar getToolbar(){
        return toolbar;
    }
}
