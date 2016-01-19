package com.example.didiorder.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.example.didiorder.bean.Cooker;
import com.example.didiorder.bean.Ijob;
import com.example.didiorder.bean.User;
import com.example.didiorder.bean.Waiter;
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
    public Ijob getJob(User user){
        if (user.getJob().equals("1")){
            return new Cooker();
        } else if (user.getJob().equals("2")) {
            return new Waiter();
        }else {
            return null;
        }
    }
}
