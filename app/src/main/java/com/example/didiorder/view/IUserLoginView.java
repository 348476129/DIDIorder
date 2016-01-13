package com.example.didiorder.view;

import com.example.didiorder.bean.User;

/**
 * Created by qqq34 on 2016/1/13.
 */
public interface IUserLoginView {
    String getUserName();
    String getPassword();
    void showLoading();
    void hideLoading();
    void setViewEnable(boolean isHide);
    void toMainActivity(User user);
    void showFailedError(String s);
}
