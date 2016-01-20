package com.example.didiorder.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.a.a.a.V;
import com.example.didiorder.BaseActivity;
import com.example.didiorder.MyApplication;
import com.example.didiorder.R;
import com.example.didiorder.adapter.OrderAdapter;
import com.example.didiorder.presenter.OrderActivityPresenter;
import com.example.didiorder.view.OrderView;
import com.rey.material.app.SimpleDialog;
import com.rey.material.widget.ProgressView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by qqq34 on 2016/1/19.
 */
public class OrderActivity extends BaseActivity implements OrderView{

    private Context context;
    private Toolbar toolbar;
    private FloatingActionButton floatingActionButton;
    private OrderActivityPresenter orderActivityPresenter;
    @Bind(R.id.order_swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.order_progress)
    ProgressView progressView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_order);
        ButterKnife.bind(this);
        context = this;
        initData();
        initview();
        initClick();
        orderActivityPresenter.getDishes();
    }
    private void initData(){
        orderActivityPresenter = new OrderActivityPresenter(this);
    }
    private void initview(){
        setStarusBarBackground(R.mipmap.status);
        setNavigationBarBackground(R.mipmap.navigation);
        toolbar = getToolbar();
        toolbar.setTitle("新客人");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        floatingActionButton = getFloatingActionButton();
        floatingActionButton.setVisibility(View.VISIBLE);

    }
    private void initClick(){
        toolbar.setNavigationOnClickListener(v -> finish());
        swipeRefreshLayout.setOnRefreshListener(()->{
            orderActivityPresenter.setPage(1);
            orderActivityPresenter.getDishes();
        });
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener(){
            //用来标记是否正在向最后一个滑动，既是否向右滑动或向下滑动
            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LinearLayoutManager manager = (LinearLayoutManager)recyclerView.getLayoutManager();
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的ItemPosition
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();

                    // 判断是否滚动到底部，并且是向右滚动
                    if (lastVisibleItem == (totalItemCount -1) && isSlidingToLast) {
                        //加载更多功能的代码
                        orderActivityPresenter.getDishes();
                    }
                }
                floatingActionButton.setOnClickListener(v -> {
                    if (MyApplication.list.size()==0){
                        TextView textView = new TextView(context);
                        textView.setText("请至少选择一个菜");
                        SimpleDialog mDialog = new SimpleDialog(context);
                        mDialog.setTitle("错误");
                        mDialog.setContentView(textView);
                        mDialog.positiveAction("确定");
                        mDialog.setCancelable(true);
                        mDialog.positiveActionClickListener(v1 -> mDialog.dismiss());
                        mDialog.show();
                    }else {
                        SimpleDialog mDialog = new SimpleDialog(context);
                        mDialog.setTitle("客人信息");
                        View view = LayoutInflater.from(context).inflate(R.layout.dialog, null);
                        TextInputLayout tipPeopleNum,tipTableId;
                        tipPeopleNum = (TextInputLayout)view.findViewById(R.id.peopleNum);
                        tipTableId = (TextInputLayout)view.findViewById(R.id.table_id);
                        mDialog.setContentView(view);
                        mDialog.positiveAction("提交");
                        mDialog.negativeAction("取消");
                        mDialog.positiveActionClickListener(v1 -> {
                            hideKeyboard();
                            String a = tipPeopleNum.getEditText().getText().toString();
                            String b = tipTableId.getEditText().getText().toString();
                            if (a.equals("")||a==null){
                                tipPeopleNum.setEnabled(true);
                                tipPeopleNum.setError("请输入人数");
                            }else if (a.equals("")||a==null){
                                tipTableId.setEnabled(true);
                                tipTableId.setError("请输入桌号");
                            }else {
                                tipPeopleNum.setEnabled(false);
                                tipTableId.setEnabled(false);
                                orderActivityPresenter.Updata(a,b);
                                mDialog.dismiss();
                            }
                        });
                        mDialog.negativeActionClickListener(v1 -> mDialog.dismiss());
                        mDialog.show();
                    }

                });
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
                if(dy> 0){
                    //大于0表示，正在向右滚动
                    isSlidingToLast = true;
                }else{
                    //小于等于0 表示停止或向左滚动
                    isSlidingToLast = false;
                }

            }
        });
    }

    @Override
    public void isHideSwipe(boolean isHide) {
        swipeRefreshLayout.setRefreshing(isHide);
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void setSwipEnable(boolean isEnable) {
    swipeRefreshLayout.setEnabled(isEnable);
    }

    @Override
    public void showSnackBar(String s) {
        Snackbar.make(floatingActionButton, s, Snackbar.LENGTH_SHORT).setAction("重试", v -> orderActivityPresenter.getDishes()).show();
    }

    @Override
    public void SetAdapter(OrderAdapter adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setViewEnable(boolean isEnable) {
        recyclerView.setEnabled(isEnable);
        swipeRefreshLayout.setEnabled(isEnable);
        floatingActionButton.setEnabled(isEnable);
    }

    @Override
    public void hideProgressbar(boolean isHide) {
        if (isHide){
            progressView.setVisibility(View.GONE);
        }else {
            progressView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void finishActivity() {
        finish();
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
