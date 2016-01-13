package com.example.didiorder.presenter;

import com.example.didiorder.view.IStartView;

/**
 * Created by qqq34 on 2016/1/11.
 */
public class StartActivityPresenter {
   private IStartView startView;

    public StartActivityPresenter(IStartView startView) {
        this.startView = startView;
    }
    public void StartButtonClicked(){
        startView.startLoginActivity();
    }
}
