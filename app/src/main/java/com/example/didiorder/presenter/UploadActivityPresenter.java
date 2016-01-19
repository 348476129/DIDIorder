package com.example.didiorder.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.example.didiorder.biz.DishesBiz;
import com.example.didiorder.biz.IDishesBiz;
import com.example.didiorder.event.UserEvent;
import com.example.didiorder.tools.UtilityTool;
import com.example.didiorder.view.IUploadView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.greenrobot.event.EventBus;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by qqq34 on 2016/1/19.
 */
public class UploadActivityPresenter {
    private IDishesBiz iDishesBiz;
    private IUploadView iUploadView;
    private File tempFile;

    public UploadActivityPresenter(IUploadView iUploadView) {
        this.iDishesBiz =new DishesBiz();
        this.iUploadView = iUploadView;
    }
    public void setImageUri(){

        iUploadView.setImageUri(Uri.fromFile(tempFile));
    }
    public void setImageUri(Uri uri){

        iUploadView.setImageUri(uri);
    }
    public String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }
    public void startPhotoZoom(Uri uri) {
        tempFile = new File(Environment.getExternalStorageDirectory(),
                getPhotoFileName());
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("output", Uri.fromFile(tempFile));
        intent.putExtra("outputX", 100);
        intent.putExtra("outputY", 100);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        System.out.println("22================");
        iUploadView.startCutActivity(intent);
    }
    public void updata(Context context, String name , Integer price){
        if (!UtilityTool.fileIsExists(tempFile)){
            tempFile=null;
        }

        iUploadView.setViewEnable(false);
        iUploadView.showLoading();
        iDishesBiz.updata(context,name,price,tempFile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dishes -> {
                    iUploadView.setViewEnable(true);
                    iUploadView.hideLoading();
                    iUploadView.toMainActivity(dishes);
                },throwable -> {
                    iUploadView.setViewEnable(true);
                    iUploadView.showFailedError(throwable.getLocalizedMessage());
                    iUploadView.hideLoading();
                });

    }
}
