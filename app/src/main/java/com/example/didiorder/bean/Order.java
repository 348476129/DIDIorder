package com.example.didiorder.bean;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;

/**
 * Created by qqq34 on 2016/1/20.
 */
public class Order extends BmobObject{
    private String headcount,table_number;
    private boolean ispaying,iscomplete;
    private List<String> disheses;

    @Override
    public String toString() {
        return "Order{" +
                "headcount='" + headcount + '\'' +
                ", table_number='" + table_number + '\'' +
                ", ispaying=" + ispaying +
                ", iscomplete=" + iscomplete +
                ", disheses=" + disheses +
                '}';
    }

    public Order(String headcount, String table_number, boolean ispaying, boolean iscomplete, List<String> disheses) {
        this.headcount = headcount;
        this.table_number = table_number;
        this.ispaying = ispaying;
        this.iscomplete = iscomplete;
        this.disheses = disheses;
    }
    public Order(){

    }

    public String getHeadcount() {
        return headcount;
    }

    public void setHeadcount(String headcount) {
        this.headcount = headcount;
    }

    public String getTable_number() {
        return table_number;
    }

    public void setTable_number(String table_number) {
        this.table_number = table_number;
    }

    public boolean ispaying() {
        return ispaying;
    }

    public void setIspaying(boolean ispaying) {
        this.ispaying = ispaying;
    }

    public boolean iscomplete() {
        return iscomplete;
    }

    public void setIscomplete(boolean iscomplete) {
        this.iscomplete = iscomplete;
    }

    public List<String> getDisheses() {
        return disheses;
    }

    public void setDisheses(List<String> disheses) {
        this.disheses = disheses;
    }
}
