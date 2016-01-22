package com.example.didiorder.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.didiorder.MyApplication;
import com.example.didiorder.R;
import com.example.didiorder.bean.AllOrder;
import com.example.didiorder.bean.Order;
import com.example.didiorder.bean.under_order;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ButtonRectangle;
import com.rey.material.app.Dialog;
import com.rey.material.widget.CheckBox;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindStatisticsListener;
import cn.bmob.v3.listener.UpdateListener;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by qqq34 on 2016/1/21.
 */
public class StickListTaskAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private LayoutInflater mInflater;
    private List<under_order> orderList;
    private String job;
    private Context context;

    public StickListTaskAdapter(Context context, List<AllOrder> allOrderList, String job) {
        this.context = context;
        mInflater = LayoutInflater.from(this.context);
        orderList = new ArrayList<>();
        for (int i = 0; i < allOrderList.size(); i++) {
            orderList.addAll(allOrderList.get(i).getOrderList());
        }
        this.job = job;
        Log.d("TAG", "" + orderList.size());

    }

    @Override
    public View getHeaderView(int i, View view, ViewGroup viewGroup) {
        Log.i("TAG", "get HeaderView " + i);
        HeaderViewHolder holder;

        if (view == null) {
            holder = new HeaderViewHolder();
            view = mInflater.inflate(R.layout.all_order_header_view, viewGroup, false);
            holder.text = (TextView) view.findViewById(R.id.head_textview);
            holder.buttonFlat = (ButtonFlat) view.findViewById(R.id.buttonflat);
            view.setTag(holder);
        } else {
            holder = (HeaderViewHolder) view.getTag();
        }

        // set header text as first char in name
        holder.text.setText("座位号:" + orderList.get(i).getTable_number());
        if (job.equals("1")) {
            holder.buttonFlat.setVisibility(View.INVISIBLE);
        } else {
            holder.buttonFlat.setEnabled(!orderList.get(i).ispay());
        }
        holder.buttonFlat.setOnClickListener(v -> {
            int price = 0;
            int num = 0;
            for (int b = 0; b < orderList.size(); b++) {
                if (orderList.get(b).getOrderId().equals(orderList.get(i).getOrderId())) {
                    num++;
                    price = price + orderList.get(b).getPrice();
                }
            }
            TextView textView = new TextView(context);
            textView.setText("       客人一共点了：" + num + "份菜\n" + "       应收款：" + price + "元");
            Dialog mDialog = new Dialog(context);
            mDialog.title("付款")
                    .positiveAction("确认收款")
                    .negativeAction("取消")
                    .contentView(textView)
                    .cancelable(true)
                    .negativeActionClickListener(v2 -> {
                        mDialog.dismiss();
                    })
                    .positiveActionClickListener(v1 -> {
                        mDialog.dismiss();
                        Order order = new Order();
                        order.setIscomplete(true);
                        order.setIspaying(true);
                        order.update(context, orderList.get(i).getOrderId(), new UpdateListener() {

                            @Override
                            public void onSuccess() {
                                // TODO Auto-generated method stub
                                List<BmobObject> list = new ArrayList<BmobObject>();
                                for (int a = 0; a < orderList.size(); a++) {
                                    if (orderList.get(a).getOrderId().equals(orderList.get(i).getOrderId())) {
                                        under_order u = new under_order();
                                        u.setObjectId(orderList.get(a).getObjectId());
                                        u.setIsfinished(true);
                                        u.setIspay(true);
                                        list.add(u);
                                    }
                                }
                                new BmobObject().updateBatch(context, list, new UpdateListener() {
                                    @Override
                                    public void onSuccess() {
                                        // TODO Auto-generated method stub
                                        Snackbar.make(v, "付款成功请刷新当前页面查看", Snackbar.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(int code, String msg) {
                                        // TODO Auto-generated method stub
                                        Snackbar.make(v, "付款失败请重试", Snackbar.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onFailure(int code, String msg) {
                                // TODO Auto-generated method stub
                                Log.i("bmob", "更新失败：" + msg);
                            }
                        });
                    })
                    .show();
        });
        return view;
    }

    /**
     * 这个是用来边标记浮动headerView的一个方法，返回相同ID的将被显示为同一View
     *
     * @param i 当前位置
     * @return 返回唯一ID
     */
    @Override
    public long getHeaderId(int i) {
        long l = Long.parseLong(orderList.get(i).getOrderId(), 16);
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
        Log.i("TAG", "get View " + position);
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.all_order_view, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.order_name);
            holder.buttonRectangle = (ButtonRectangle) convertView.findViewById(R.id.buttonRectangle);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
            holder.simpleDraweeView = (SimpleDraweeView) convertView.findViewById(R.id.view);
            holder.textPrice = (TextView) convertView.findViewById(R.id.order_price);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.text.setText(MyApplication.deshesMap.get(orderList.get(position).getDishes()));
        holder.textPrice.setText(orderList.get(position).getPrice().toString());
        if (MyApplication.imageMap.get(orderList.get(position).getDishes()) != null) {
            holder.simpleDraweeView.setImageURI(Uri.parse(MyApplication.imageMap.get(orderList.get(position).getDishes())));
            Log.d("TAGGG", "图片还在就是不出来");
        } else {
            holder.simpleDraweeView.setImageURI(Uri.parse("res://com.example.didiorder/" + R.mipmap.start_image));
            Log.d("TAGGG", "图片不见了！");
        }
        holder.checkBox.setChecked(orderList.get(position).isfinished());
        if (job.equals("1")) {
            holder.checkBox.setEnabled(true);
            if (holder.checkBox.isChecked()) {
                holder.checkBox.setEnabled(false);
            }
            holder.buttonRectangle.setVisibility(View.GONE);
        } else {
            holder.checkBox.setEnabled(false);
            holder.buttonRectangle.setVisibility(View.VISIBLE);
        }
        holder.buttonRectangle.setEnabled(!orderList.get(position).ispay());
        if (job.equals("1") && orderList.get(position).isrefund()) {
            holder.buttonRectangle.setVisibility(View.VISIBLE);
        } else if ((job.equals("2") && orderList.get(position).isrefund())||holder.checkBox.isChecked()) {
            holder.buttonRectangle.setEnabled(false);
        }
        holder.buttonRectangle.setOnClickListener(v -> {
            if (job.equals("2")) {
                holder.buttonRectangle.setEnabled(false);
                under_order u = new under_order();
                u.setIsrefund(true);
                u.update(context, orderList.get(position).getObjectId(), new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        Snackbar.make(v, "退菜申请已发送给厨师", Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Snackbar.make(v, "退菜申请已发送失败，请重试", Snackbar.LENGTH_SHORT).show();
                        holder.buttonRectangle.setEnabled(true);
                    }
                });
            } else if (job.equals("1")) {
                holder.buttonRectangle.setEnabled(false);
                under_order u = new under_order();
                u.setObjectId(orderList.get(position).getObjectId());
                u.delete(context, new DeleteListener() {
                    @Override
                    public void onSuccess() {
                        Snackbar.make(v, "菜品已成功退回，请刷新界面", Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Snackbar.make(v, "菜品退回失败，请重试", Snackbar.LENGTH_SHORT).show();
                        holder.buttonRectangle.setEnabled(true);
                    }
                });
            }
        });
        holder.checkBox.setOnClickListener(v -> {
            holder.checkBox.setEnabled(false);
            under_order u = new under_order();
            u.setIsfinished(true);
            u.update(context, orderList.get(position).getObjectId(), new UpdateListener() {
                @Override
                public void onSuccess() {
                    Snackbar.make(v, "通知服务员上菜成功", Snackbar.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(int i, String s) {
                    Snackbar.make(v, "通知服务员上菜失败请重试", Snackbar.LENGTH_SHORT).show();
                    holder.checkBox.setEnabled(true);
                }
            });
        });


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

    public void addData(List<AllOrder> list) {
        for (int i = 0; i < list.size(); i++) {
            orderList.addAll(list.get(i).getOrderList());
        }
        notifyDataSetChanged();
    }
}
