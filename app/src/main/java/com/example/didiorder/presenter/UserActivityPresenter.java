package com.example.didiorder.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.example.didiorder.biz.IUserBiz;
import com.example.didiorder.biz.UserBiz;
import com.example.didiorder.tools.UtilityTool;
import com.example.didiorder.view.IUserActivityView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by qqq34 on 2016/1/15.
 */
public class UserActivityPresenter {
    private IUserBiz userBiz;
private IUserActivityView iUserActivityView;
    private String TAG = "UserActivityPresenter";
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    private File tempFile = new File(Environment.getExternalStorageDirectory(),
            getPhotoFileName());

    public UserActivityPresenter(IUserActivityView iUserActivityView) {
        this.iUserActivityView = iUserActivityView;
        this.userBiz = new UserBiz();
    }
    public void updata(Context context, String name , String job, File file){
        if (job.equals("厨师")){
            job="1";
        }else if (job.equals("服务员")){
            job="2";
        }
        if (!UtilityTool.fileIsExists(file)){
            file=null;
        }
        iUserActivityView.setViewEnable(false);
        iUserActivityView.showLoading();
        userBiz.updata(context,name,job,file)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                    iUserActivityView.setViewEnable(true);
                    iUserActivityView.toMainActivity(user);
                    iUserActivityView.hideLoading();
                },throwable -> {
                    iUserActivityView.setViewEnable(true);
                    iUserActivityView.showFailedError(throwable.getLocalizedMessage());
                    iUserActivityView.hideLoading();
                });

    }
    public void setImageUri(Uri uri){
        iUserActivityView.setImageUri(uri);
    }
    public String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("output", Uri.fromFile(tempFile));
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        System.out.println("22================");
        iUserActivityView.startCutActivity(intent);
    }
}
