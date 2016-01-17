package com.example.didiorder.tools;

import android.content.Context;

import java.io.File;

import cn.bmob.v3.BmobUser;

/**
 * Created by qqq34 on 2016/1/15.
 */
public  class UtilityTool {
    public  static boolean fileIsExists(File file){
        try{
            File f=file;
            if(!f.exists()){
                return false;
            }

        }catch (Exception e) {
            // TODO: handle exception
            return false;
        }
        return true;
    }
    public  static void  LogOut(Context context){
        BmobUser.logOut(context);   //清除缓存用户对象
        BmobUser currentUser = BmobUser.getCurrentUser(context); // 现在的currentUser是null了
    }
}
