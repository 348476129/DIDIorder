package com.example.didiorder.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.didiorder.R;
import com.example.didiorder.adapter.StickListTaskAdapter;
import com.example.didiorder.bean.Order;

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

    public void setArrayAdapter(StickListTaskAdapter stickListTaskAdapter) {
        this.stickListTaskAdapter = stickListTaskAdapter;
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_order_list,parent,false);
        stickyListHeadersListView = (StickyListHeadersListView)v.findViewById(R.id.list) ;
        if (stickListTaskAdapter!=null){
            stickyListHeadersListView.setAdapter(stickListTaskAdapter);
        }

        return v;
    }
    public static FragmentOrder newInstance(StickListTaskAdapter stickListTaskAdapter){
      FragmentOrder fragmentOrder = new FragmentOrder();
        fragmentOrder.setArrayAdapter(stickListTaskAdapter);
        return fragmentOrder;
    }
    public void setAdapter(StickListTaskAdapter stickListTaskAdapter){
        stickyListHeadersListView.setAdapter(stickListTaskAdapter);
    }
}
