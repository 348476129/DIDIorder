package com.example.didiorder.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by qqq34 on 2016/1/17.
 */
public class under_order extends BmobObject {
    private String dishes,table_number;
    private boolean isrefund,isfinished,ispay;
    private Integer price,count;
    private String orderId;

    public under_order(String dishes, String orderId, Integer count, Integer price, boolean ispay, boolean isfinished, boolean isrefund, String table_number) {
        this.dishes = dishes;
        this.orderId = orderId;
        this.count = count;
        this.price = price;
        this.ispay = ispay;
        this.isfinished = isfinished;
        this.isrefund = isrefund;
        this.table_number = table_number;
    }
    public under_order(){}
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "under_order{" +
                "dishes='" + dishes + '\'' +
                ", table_number='" + table_number + '\'' +
                ", isrefund=" + isrefund +
                ", isfinished=" + isfinished +
                ", ispay=" + ispay +
                ", price=" + price +
                ", count=" + count +
                '}';
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getDishes() {
        return dishes;
    }

    public void setDishes(String dishes) {
        this.dishes = dishes;
    }

    public boolean ispay() {
        return ispay;
    }

    public void setIspay(boolean ispay) {
        this.ispay = ispay;
    }

    public boolean isrefund() {
        return isrefund;
    }

    public void setIsrefund(boolean isrefund) {
        this.isrefund = isrefund;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getTable_number() {
        return table_number;
    }

    public void setTable_number(String table_number) {
        this.table_number = table_number;
    }

    public boolean isfinished() {
        return isfinished;
    }

    public void setIsfinished(boolean isfinished) {
        this.isfinished = isfinished;
    }
}
