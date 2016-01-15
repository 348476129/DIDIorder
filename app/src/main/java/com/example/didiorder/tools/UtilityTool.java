package com.example.didiorder.tools;

import java.io.File;

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
}
