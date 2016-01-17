package com.example.didiorder.presenter;

import android.content.Context;

import com.example.didiorder.biz.IUserBiz;
import com.example.didiorder.biz.UserBiz;
import com.example.didiorder.view.IUserLoginView;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by qqq34 on 2016/1/11.
 */
public class UserLoginPresenter {
    private IUserBiz userBiz;
    private IUserLoginView userLoginView;
    public UserLoginPresenter(IUserLoginView userLoginView) {
        this.userLoginView = userLoginView;
        this.userBiz = new UserBiz();
    }
    public void login(Context context) {
        userLoginView.setViewEnable(false);
        userLoginView.showLoading();
        userBiz.login(context,userLoginView.getUserName(),userLoginView.getPassword())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                    userLoginView.setViewEnable(true);
                    userLoginView.hideLoading();
                    userLoginView.toMainActivity(user);
                },throwable -> {
                    userLoginView.setViewEnable(true);
                    userLoginView.showFailedError(throwable.getLocalizedMessage());
                    userLoginView.hideLoading();
                });

    }
}
