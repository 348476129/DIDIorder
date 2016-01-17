package com.example.didiorder.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.didiorder.BaseActivity;
import com.example.didiorder.R;
import com.example.didiorder.bean.under_order;
import com.example.didiorder.presenter.IncomeActivityPresenter;
import com.example.didiorder.view.IIncomeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.FindStatisticsListener;

/**
 * Created by qqq34 on 2016/1/17.
 */
public class IncomeActivity extends BaseActivity implements IIncomeView{
    private Toolbar toolbar;
    @Bind(R.id.Income_swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.income_text_tatle)
    TextView text_tatle;
    @Bind(R.id.income_text_num)
    TextView text_num;
    private IncomeActivityPresenter incomeActivityPresenter;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_income);
        ButterKnife.bind(this);
        context = this;
        incomeActivityPresenter = new IncomeActivityPresenter(this);
        initview();
        initClick();

    }
    private void initview(){
        text_num.setVisibility(View.GONE);
        text_tatle.setVisibility(View.GONE);
        setStarusBarBackground(R.mipmap.status);
        setNavigationBarBackground(R.mipmap.navigation);
        toolbar = getToolbar();
        toolbar.setTitle("收益");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimary,R.color.deep,R.color.colorPrimaryDark);
        setSwipEnable(false);
        incomeActivityPresenter.getData(context);
    }
    private void initClick(){
        toolbar.setNavigationOnClickListener(v -> finish());
        swipeRefreshLayout.setOnRefreshListener(()-> incomeActivityPresenter.getData(context)
        );
    }

    @Override
    public void showSnackBar(String s) {
        Snackbar.make(toolbar,s,Snackbar.LENGTH_SHORT).setAction("重试",v -> incomeActivityPresenter.getData(context)).show();
    }

    @Override
    public void setText(String a, String b) {
        text_num.setText("总收入为："+a+"元");
        text_tatle.setText("一共卖出了："+b+"份菜");
    }

    @Override
    public void isRefresh(boolean isRefresh) {
        swipeRefreshLayout.setRefreshing(isRefresh);
    }

    @Override
    public void hideView(boolean ishide) {
        if (ishide){
            text_num.setVisibility(View.GONE);
            text_tatle.setVisibility(View.GONE);
        }else {
            text_num.setVisibility(View.VISIBLE);
            text_tatle.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setSwipEnable(boolean isEnable) {
        swipeRefreshLayout.setEnabled(isEnable);
    }
}
