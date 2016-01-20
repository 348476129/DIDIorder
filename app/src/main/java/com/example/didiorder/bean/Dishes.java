package com.example.didiorder.bean;

/**
 * Created by qqq34 on 2016/1/19.
 */

import cn.bmob.v3.BmobObject;

public class Dishes extends BmobObject {
    private String name;
    private String imageUrl;
    private Integer price;

    @Override
    public String toString() {
        return "Dishes{" +
                "name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", price=" + price +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
