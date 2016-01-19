package com.example.didiorder.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.didiorder.BaseActivity;
import com.example.didiorder.R;
import com.example.didiorder.bean.Dishes;
import com.example.didiorder.bean.User;
import com.example.didiorder.presenter.UploadActivityPresenter;
import com.example.didiorder.presenter.UserActivityPresenter;
import com.example.didiorder.view.IUploadView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ButtonRectangle;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;

/**
 * Created by qqq34 on 2016/1/19.
 */
public class UploadActivity extends BaseActivity implements IUploadView{
    private Toolbar toolbar;
    @Bind(R.id.activity_upload_image)
    SimpleDraweeView simpleDraweeView ;
    @Bind(R.id.activity_upload_button)
    ButtonFlat buttonFlat;
    @Bind(R.id.til_name)
    TextInputLayout TipName;
    @Bind(R.id.til_price)
    TextInputLayout TipPrice;
    @Bind(R.id.upload_progressbar)
    ProgressBar progressView;
    @Bind(R.id.activity_upload_updata_button)
    ButtonRectangle buttonRectangle;
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    private boolean isNameTrue;
    private boolean isPriceTrue;
    UploadActivityPresenter uploadActivityPresenter;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_upload);
        ButterKnife.bind(this);
        context = this;
        initdata();
        initview();
        iniclick();
    }
    private void initdata() {
        uploadActivityPresenter = new UploadActivityPresenter(UploadActivity.this);
    }
    private void initview() {
        setStarusBarBackground(R.mipmap.status);
        setNavigationBarBackground(R.mipmap.navigation);
        toolbar = getToolbar();
        toolbar.setTitle("上传菜品");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        CheckButton();
        hideLoading();
    }
    private  void iniclick(){
        toolbar.setNavigationOnClickListener(v -> finish());
        buttonFlat.setOnClickListener(v -> {
            Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
            getAlbum.setType("image/*");
            startActivityForResult(getAlbum, PHOTO_REQUEST_GALLERY);
        });
        TipName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()==0){
                    TipName.setErrorEnabled(true);
                    TipName.setError("请填写名字");
                    isNameTrue=false;
                }else {
                    isNameTrue=true;
                    TipPrice.setErrorEnabled(false);

                }
                CheckButton();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        TipPrice.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()==0){
                    TipPrice.setErrorEnabled(true);
                    TipPrice.setError("请填写价格");
                    isPriceTrue=false;
                }else {
                    isPriceTrue=true;
                    TipName.setErrorEnabled(false);
                }
                CheckButton();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        buttonRectangle.setOnClickListener(v ->
                uploadActivityPresenter.updata
                        (context,TipName.getEditText().getText().toString(),Integer.parseInt(TipPrice.getEditText().getText().toString())));
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO_REQUEST_GALLERY:// 当选择从本地获取图片时
                // 做非空判断，当我们觉得不满意想重新剪裁的时候便不会报异常，下同
                if (data != null) {
                    uploadActivityPresenter.startPhotoZoom(data.getData());
                } else {
                }
                break;
            case PHOTO_REQUEST_CUT:// 返回的结果
                if (data != null)
                    uploadActivityPresenter.setImageUri();

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
simpleDraweeView.setImageURI(uri);
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
        simpleDraweeView.setEnabled(isHide);
        TipName.setEnabled(isHide);
        TipPrice.setEnabled(isHide);
        buttonFlat.setEnabled(isHide);
        buttonRectangle.setEnabled(isHide);
    }

    @Override
    public void toMainActivity(Dishes dishes) {
        finish();
    }

    @Override
    public void showFailedError(String s) {
        Snackbar.make(toolbar, s, Snackbar.LENGTH_SHORT).setAction("重试", v -> uploadActivityPresenter.updata
                (context,TipName.getEditText().getText().toString(),Integer.parseInt(TipPrice.getEditText().getText().toString()))).show();

    }
    private void CheckButton() {
        buttonRectangle.setEnabled(isNameTrue && isPriceTrue);
    }
}
