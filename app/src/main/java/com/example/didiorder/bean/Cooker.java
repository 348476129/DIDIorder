package com.example.didiorder.bean;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.didiorder.adapter.StickListTaskAdapter;
import com.example.didiorder.fragment.FragmentOrder;

import java.util.List;

/**
 * Created by qqq34 on 2016/1/19.
 */
public class Cooker extends Ijob{
    private FragmentOrder[] fragmentOrders;
    private FragmentOrder all = new FragmentOrder();
    private FragmentOrder under=new FragmentOrder();
    private FragmentOrder finish= new FragmentOrder();
    @Override
    public void setFloat(FloatingActionButton floatingActionButton) {
        floatingActionButton.setVisibility(View.GONE);
    }

    @Override
    public FragmentOrder[] getFragment() {
        fragmentOrders = new FragmentOrder[]{all,under,finish};
        return fragmentOrders;
    }
    @Override
    public void setAdapter(Context context, List<AllOrder> orderLista, List<AllOrder> orderListb, List<AllOrder> orderListc) {
        StickListTaskAdapter a = new StickListTaskAdapter(context,orderLista,"1");
        StickListTaskAdapter b = new StickListTaskAdapter(context,orderListb,"1");
        StickListTaskAdapter c = new StickListTaskAdapter(context,orderListc,"1");
        all.setAdapter(a);
        under.setAdapter(b);
        finish.setAdapter(c);
    }

    @Override
    public void addAdapter(List<AllOrder> orderLista, List<AllOrder> orderListb, List<AllOrder> orderListc) {
        all.addAdapter(orderLista);
        under.addAdapter(orderListb);
        finish.addAdapter(orderListc);
    }

}
