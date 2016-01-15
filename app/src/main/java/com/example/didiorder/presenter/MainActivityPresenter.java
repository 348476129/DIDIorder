package com.example.didiorder.presenter;

import android.net.Uri;

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
}
