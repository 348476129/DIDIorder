package com.example.didiorder.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.didiorder.MyApplication;
import com.example.didiorder.R;
import com.example.didiorder.bean.Dishes;
import com.example.didiorder.bean.Order;
import com.facebook.drawee.view.SimpleDraweeView;
import com.rey.material.widget.CheckBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by qqq34 on 2016/1/20.
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    private Context context;
    private List<Dishes> dishesList;
    private List<String> list;
    private HashMap<String,Integer> hashMap;

    public OrderAdapter(Context context, List<Dishes> dishesList) {
        this.context = context;
        this.dishesList = dishesList;
        this.list = MyApplication.list;
        this.hashMap =  new HashMap<>();
    }

    @Override
    public OrderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.order_list, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(OrderAdapter.MyViewHolder holder, int position) {
        if (!CheckList(list,dishesList.get(position).getObjectId())){
            holder.checkBox.setChecked(true);
        }else {
            holder.checkBox.setChecked(false);
        }
        holder.name.setText(dishesList.get(position).getName());
        holder.price.setText(dishesList.get(position).getPrice().toString());
        holder.checkBox.setEnabled(false);
        if (dishesList.get(position).getImageUrl()!=null){
            holder.simpleDraweeView.setImageURI(Uri.parse(dishesList.get(position).getImageUrl()));
        }else {
            holder.simpleDraweeView.setImageURI(Uri.parse("res://com.example.didiorder/"+R.mipmap.start_image));
        }
        holder.cardView.setOnClickListener(v -> {
             String id= dishesList.get(position).getObjectId();
            holder.checkBox.setChecked(!holder.checkBox.isChecked());
            if (CheckList(list,id)){
                list.add(id);
                hashMap.put(id,dishesList.get(position).getPrice());
            }else {
                list.remove(id);
            }
        });


    }

    @Override
    public int getItemCount() {
        return dishesList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, price;
        SimpleDraweeView simpleDraweeView;
        CheckBox checkBox;
        CardView cardView;


        public MyViewHolder(View view) {
            super(view);
            cardView = (CardView)view.findViewById(R.id.cardview);
            name = (TextView) view.findViewById(R.id.order_name);
            price = (TextView) view.findViewById(R.id.order_price);
            simpleDraweeView = (SimpleDraweeView) view.findViewById(R.id.view);
            checkBox = (CheckBox) view.findViewById(R.id.checkbox);
        }
    }
    public void addData(List<Dishes> dishesList1){
        dishesList.addAll(dishesList1);
        notifyDataSetChanged();
    }
    private boolean CheckList(List<String> list ,String s){
        for (int i = 0;i<list.size();i++){
            if (list.get(i).equals(s)){
                return false;
            }
        }
        return true;
    }
    public List<String> getList(){
        return list;
    }
}
