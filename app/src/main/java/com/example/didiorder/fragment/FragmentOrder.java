package com.example.didiorder.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;

import com.example.didiorder.R;
import com.example.didiorder.adapter.StickListTaskAdapter;
import com.example.didiorder.bean.AllOrder;
import com.example.didiorder.bean.Order;
import com.example.didiorder.presenter.MainActivityPresenter;
import com.rey.material.widget.ProgressView;

import java.util.List;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by qqq34 on 2016/1/14.
 */
public class FragmentOrder extends Fragment {
    private StickListTaskAdapter stickListTaskAdapter;
   private StickyListHeadersListView stickyListHeadersListView;
    private ProgressView progressView;
    private int getLastVisiblePosition = 0,lastVisiblePositionY=0;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_order_list,parent,false);
        stickyListHeadersListView = (StickyListHeadersListView)v.findViewById(R.id.list) ;
        progressView = (ProgressView)v.findViewById(R.id.fragment_progressbar);
        stickyListHeadersListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    //滚动到底部
                    if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
                        fragmentScrollListener.OnTop(false);
                        View v=(View) view.getChildAt(view.getChildCount()-1);
                        int[] location = new  int[2] ;
                        v.getLocationOnScreen(location);//获取在整个屏幕内的绝对坐标
                        int y=location [1];

                        if (view.getLastVisiblePosition()!=getLastVisiblePosition
                                && lastVisiblePositionY!=y)//第一次拖至底部
                        {
                            getLastVisiblePosition=view.getLastVisiblePosition();
                            lastVisiblePositionY=y;
                            return;
                        }
                        else if (view.getLastVisiblePosition()==getLastVisiblePosition
                                && lastVisiblePositionY==y)//第二次拖至底部
                        {
                           presenterListener.OnUpDataList();

                        }
                    }
                    if(view.getFirstVisiblePosition() == 0){
                        fragmentScrollListener.OnTop(true);
                    }else {
                        fragmentScrollListener.OnTop(false);
                    }

                    getLastVisiblePosition=0;
                    lastVisiblePositionY=0;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        return v;
    }
    public void setAdapter(StickListTaskAdapter stickListTaskAdapter){
        this.stickListTaskAdapter=stickListTaskAdapter;
        stickyListHeadersListView.setAdapter(this.stickListTaskAdapter);
        progressView.setVisibility(View.GONE);
    }
    public void  addAdapter(List<AllOrder> orderLista){
        if (stickListTaskAdapter!=null){
            stickListTaskAdapter.addData(orderLista);
        }
    }
   public  PresenterListener presenterListener;
    public interface PresenterListener{
        void OnUpDataList();
    }

    public  PresenterListener getPresenterListener() {
        return presenterListener;
    }

    public  void setPresenterListener(PresenterListener presenterListener) {
        this.presenterListener = presenterListener;
    }
    public interface  FragmentScrollListener{
        void OnTop(boolean isTop);
    }
   public FragmentScrollListener fragmentScrollListener;

    public FragmentScrollListener getFragmentScrollListener() {
        return fragmentScrollListener;
    }

    public void setFragmentScrollListener(FragmentScrollListener fragmentScrollListener) {
        this.fragmentScrollListener = fragmentScrollListener;
    }
}
