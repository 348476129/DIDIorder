package com.example.didiorder.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.example.didiorder.adapter.StickListTaskAdapter;

/**
 * Created by qqq34 on 2016/1/15.
 */
public interface  IMainView {
    void setImageUri(Uri uri);
    void startNewActivity(Intent intent);
    Context getContext();
    void setAdapter(StickListTaskAdapter stickListTaskAdapter);
}
