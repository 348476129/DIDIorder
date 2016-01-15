package com.example.didiorder.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;

import com.example.didiorder.BaseActivity;
import com.example.didiorder.R;
import com.example.didiorder.bean.User;
import com.example.didiorder.presenter.UserActivityPresenter;
import com.example.didiorder.view.IUserActivityView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ButtonRectangle;
import com.rey.material.widget.ProgressView;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;

/**
 * Created by qqq34 on 2016/1/15.
 */
public class UserActivity extends BaseActivity implements IUserActivityView {
    @Bind(R.id.activity_user_spinner)   //做一半发现一个新的绑定控件的方法，这个更简单 就用这个 了
     BetterSpinner spinner2;
    @Bind(R.id.til_name)
     TextInputLayout TilName;
    @Bind(R.id.activity_user_image)
    SimpleDraweeView userImage;
    @Bind(R.id.activity_user_button)
    ButtonFlat imageButton;
    @Bind(R.id.activity_user_updata_button)
    ButtonRectangle updataButton;
    @Bind(R.id.user_progressbar)
    ProgressView progressView;
    private Context context;
    private Toolbar toolbar;
    private User user;
    private UserActivityPresenter userActivityPresenter;
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    private File tempFile ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentLayout(R.layout.activity_user);
        ButterKnife.bind(this);
        context = this;
        initdata();
        initview();
        initClick();
    }

    private void initdata() {
        user = BmobUser.getCurrentUser(this, User.class);
        userActivityPresenter = new UserActivityPresenter(UserActivity.this);
        tempFile = new File(Environment.getExternalStorageDirectory(),
               userActivityPresenter. getPhotoFileName());
    }

    private void initview() {
        setStarusBarBackground(R.mipmap.status);
        setNavigationBarBackground(R.mipmap.navigation);
        toolbar = getToolbar();
        toolbar.setTitle("修改资料");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        String[] list = getResources().getStringArray(R.array.job);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, list);
        spinner2.setAdapter(adapter);
        spinner2.setText(list[(Integer.parseInt(user.getJob()) - 1)]);
        TilName.getEditText().setText(user.getName());
        if (user.getUser_image_url()!=null&&!user.getUser_image_url().isEmpty()){
            Uri uri = Uri.parse(user.getUser_image_url());
            userActivityPresenter.setImageUri(uri);
        }
    }

    private void initClick() {
        toolbar.setNavigationOnClickListener(v -> finish());
        imageButton.setOnClickListener(v -> {
            Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
            getAlbum.setType("image/*");
            startActivityForResult(getAlbum, PHOTO_REQUEST_GALLERY);
        });
        TilName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()==0){
                    TilName.setError("名字不能为空");
                    updataButton.setEnabled(false);
                }else {
                    updataButton.setEnabled(true);
                    TilName.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        updataButton.setOnClickListener(v -> userActivityPresenter.updata(context,TilName.getEditText().getText().toString(),spinner2.getText().toString(),tempFile));
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO_REQUEST_GALLERY:// 当选择从本地获取图片时
                // 做非空判断，当我们觉得不满意想重新剪裁的时候便不会报异常，下同
                if (data != null) {
                    userActivityPresenter.startPhotoZoom(data.getData());
                } else {
                }
                break;
            case PHOTO_REQUEST_CUT:// 返回的结果
                if (data != null)
                    userActivityPresenter.setImageUri(Uri.fromFile(tempFile));
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void startCutActivity(Intent intent) {
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    @Override
    public void setImageUri(Uri uri) {
        userImage.setImageURI(uri);
    }

    @Override
    public void showLoading() {
        progressView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressView.setVisibility(View.GONE);
    }

    @Override
    public void setViewEnable(boolean isHide) {
        spinner2.setEnabled(isHide);
        TilName.setEnabled(isHide);
         userImage.setEnabled(isHide);
         imageButton.setEnabled(isHide);
        updataButton.setEnabled(isHide);
    }

    @Override
    public void toMainActivity(User user) {
        finish();
    }

    @Override
    public void showFailedError(String s) {
        Snackbar.make(toolbar, s, Snackbar.LENGTH_SHORT).setAction("重试", v -> userActivityPresenter.updata(context,TilName.getEditText().getText().toString(),spinner2.getText().toString(),tempFile)).show();
    }
}
