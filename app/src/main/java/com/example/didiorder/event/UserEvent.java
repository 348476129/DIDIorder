package com.example.didiorder.event;

import android.net.Uri;

/**
 * Created by qqq34 on 2016/1/17.
 */
public class UserEvent {
    private Uri uri;

    public UserEvent(Uri uri) {
        this.uri = uri;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
