package com.example.didiorder.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.didiorder.MyApplication;
import com.example.didiorder.R;
import com.example.didiorder.bean.AllOrder;
import com.example.didiorder.bean.under_order;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ButtonRectangle;
import com.rey.material.widget.CheckBox;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by qqq34 on 2016/1/21.
 */
public class StickListTaskAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private LayoutInflater mInflater;
    private  List<under_order> orderList;


    public StickListTaskAdapter(Context context, List<AllOrder> allOrderList) {
        mInflater = LayoutInflater.from(context);
        orderList = new ArrayList<>();
        for (int i = 0;i<allOrderList.size();i++){
            orderList.addAll(allOrderList.get(i).getOrderList());
        }
        Log.d("TAG",""+orderList.size());

    }
    @Override
    public View getHeaderView(int i, View view, ViewGroup viewGroup) {
        Log.i("TAG","get HeaderView "+i);
        HeaderViewHolder holder;

        if (view == null) {
            holder = new HeaderViewHolder();
            view = mInflater.inflate(R.layout.all_order_header_view, viewGroup, false);
            holder.text = (TextView) view.findViewById(R.id.head_textview);
            view.setTag(holder);
        } else {
            holder = (HeaderViewHolder) view.getTag();
        }

        // set header text as first char in name
        holder.text.setText(orderList.get(i).getTable_number());

        return view;
    }

    /**
     * 这个是用来边标记浮动headerView的一个方法，返回相同ID的将被显示为同一View
     * @param i 当前位置
     * @return 返回唯一ID
     */
    @Override
    public long getHeaderId(int i) {
        long l =Long.parseLong(orderList.get(i).getOrderId(),16);
        return l;
    }

    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i("TAG","get View "+ position);
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.all_order_view,parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.order_name);
            holder.buttonRectangle = (ButtonRectangle)convertView.findViewById(R.id.buttonRectangle);
            holder.checkBox = (CheckBox)convertView.findViewById(R.id.checkBox) ;
            holder.simpleDraweeView = (SimpleDraweeView)convertView.findViewById(R.id.view) ;
            holder.textPrice = (TextView)convertView.findViewById(R.id.order_price) ;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.text.setText(MyApplication.deshesMap.get(orderList.get(position).getDishes()));
        holder.textPrice.setText(orderList.get(position).getPrice().toString());
        if(MyApplication.imageMap.get(orderList.get(position).getDishes())!=null){
            holder.simpleDraweeView.setImageURI(Uri.parse(MyApplication.imageMap.get(orderList.get(position).getDishes())));
        }else {
            holder.simpleDraweeView.setImageURI(Uri.parse("res://com.example.didiorder/"+R.mipmap.start_image));
        }





        return convertView;
    }

    class HeaderViewHolder {
        TextView text;
        ButtonFlat buttonFlat;
    }

    class ViewHolder {
        TextView text;
        SimpleDraweeView simpleDraweeView;
        TextView textPrice;
        CheckBox checkBox;
        ButtonRectangle buttonRectangle;
    }
}
