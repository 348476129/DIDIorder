package com.example.didiorder.presenter;

import android.content.Context;

import com.example.didiorder.biz.IIncomeBiz;
import com.example.didiorder.biz.IncomeBiz;
import com.example.didiorder.view.IIncomeView;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by qqq34 on 2016/1/17.
 */
public class IncomeActivityPresenter {
    private IIncomeView iIncomeView;
    private IIncomeBiz iIncomeBiz;
    private int a,b;
    public IncomeActivityPresenter(IIncomeView iIncomeView) {
        this.iIncomeView = iIncomeView;
       this. iIncomeBiz = new IncomeBiz();
    }
    public void getData(Context context){
        iIncomeView.hideView(true);
        iIncomeView.isRefresh(true);
        iIncomeView.setSwipEnable(false);
        iIncomeBiz.Income(context).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                    a=integer.intValue();
                },throwable -> {
                    iIncomeView.isRefresh(false);
                    iIncomeView.setSwipEnable(true);
                    iIncomeView.showSnackBar(throwable.getLocalizedMessage());
                    return;
                });
        iIncomeBiz.tatle(context).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                    b=integer.intValue();
                    iIncomeView.isRefresh(false);
                    iIncomeView.hideView(false);
                    iIncomeView.setSwipEnable(true);
                    iIncomeView.setText(a+"",b+"");
                },throwable -> {
                    iIncomeView.isRefresh(false);
                    iIncomeView.setSwipEnable(true);
                    iIncomeView.showSnackBar(throwable.getLocalizedMessage());
                    return;
                });
    }
}
