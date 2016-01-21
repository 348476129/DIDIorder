package com.example.didiorder.bean;

import java.util.List;

/**
 * Created by qqq34 on 2016/1/21.
 */
public class AllOrder {
    private  Order order;
    private List<under_order> orderList;

    public AllOrder(Order order, List<under_order> orderList) {
        this.order = order;
        this.orderList = orderList;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<under_order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<under_order> orderList) {
        this.orderList = orderList;
    }
}
