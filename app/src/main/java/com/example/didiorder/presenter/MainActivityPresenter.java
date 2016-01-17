package com.example.didiorder.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.example.didiorder.tools.UtilityTool;
import com.example.didiorder.view.IMainView;

/**
 * Created by qqq34 on 2016/1/15.
 */
public class MainActivityPresenter {
   private IMainView iMainView;

    public MainActivityPresenter(IMainView iMainView) {
        this.iMainView = iMainView;
    }
    public void setImageUri(Uri uri){
        iMainView.setImageUri(uri);
    }
    public void startNewActivity(Intent intent){
        iMainView.startNewActivity(intent);
    }
    public void LogOut(Context context){
        UtilityTool.LogOut(context);
    }
}
