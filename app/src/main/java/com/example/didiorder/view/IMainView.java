package com.example.didiorder.view;

import android.content.Intent;
import android.net.Uri;

/**
 * Created by qqq34 on 2016/1/15.
 */
public interface  IMainView {
    void setImageUri(Uri uri);
    void startNewActivity(Intent intent);
}
