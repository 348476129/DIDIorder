package com.example.didiorder.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.didiorder.BaseActivity;
import com.example.didiorder.R;
import com.example.didiorder.StartActivity;
import com.example.didiorder.bean.User;
import com.example.didiorder.presenter.StartActivityPresenter;
import com.example.didiorder.presenter.UserLoginPresenter;
import com.example.didiorder.view.IUserLoginView;
import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.rey.material.widget.ProgressView;

public class LoginActivity extends BaseActivity implements IUserLoginView {
    private Toolbar toolbar;
    private ButtonRectangle buttonRectangle;
    private ProgressView progressBar;
    private TextInputLayout TilPassword, TilUsername;
    private EditText editPassword, editUsername;
    private boolean isUsernameTrue;
    private boolean isPasswordTrue;
    private Context context;
    private UserLoginPresenter mUserLoginPresenter = new UserLoginPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_login);
        context = this;
        initView();
        initClick();
    }

    private void initClick() {
        toolbar.setNavigationOnClickListener(v -> finish()); //lambda语法，使匿名内部类写法的更简洁
        editPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String msg = s.toString();
                if (s.length() == 0 || s.length() > 5) {
                    TilPassword.setErrorEnabled(false);
                    isPasswordTrue = false;
                    if (s.length() > 5) {
                        isPasswordTrue = true;

                    }
                } else {
                    isPasswordTrue = false;
                    TilPassword.setErrorEnabled(true);
                    TilPassword.setError("输入的密码过短");
                }
                CheckButton();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String msg = s.toString();
                if (s.length() == 0 || s.length() > 5) {
                    TilUsername.setErrorEnabled(false);
                    isUsernameTrue = false;
                    if (s.length() > 5) {
                        isUsernameTrue = true;
                    }
                } else {
                    isUsernameTrue = false;
                    TilUsername.setErrorEnabled(true);
                    TilUsername.setError("输入的账号过短");
                }
                CheckButton();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        buttonRectangle.setOnClickListener(v -> mUserLoginPresenter.login(context));
    }

    private void initView() {
        setStarusBarBackground(R.mipmap.status);
        setNavigationBarBackground(R.mipmap.navigation);
        toolbar = getToolbar();
        toolbar.setTitle("登录");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        TilPassword = (TextInputLayout) findViewById(R.id.til_pwd);
        editPassword = TilPassword.getEditText();
        TilUsername = (TextInputLayout) findViewById(R.id.til_unm);
        editUsername = TilUsername.getEditText();
        buttonRectangle = (ButtonRectangle) findViewById(R.id.login_button);
        progressBar = (ProgressView) findViewById(R.id.login_progressbar);
        progressBar.setVisibility(View.GONE);
        buttonRectangle.setEnabled(false);
    }

    private void CheckButton() {
        buttonRectangle.setEnabled(isPasswordTrue && isUsernameTrue);
    }

    @Override
    public String getUserName() {
        return TilUsername.getEditText().getText().toString();
    }

    @Override
    public String getPassword() {
        return TilPassword.getEditText().getText().toString();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setViewEnable(boolean isHide) {
        TilPassword.getEditText().setEnabled(isHide);
        TilUsername.getEditText().setEnabled(isHide);
        buttonRectangle.setEnabled(isHide);
    }

    @Override
    public void toMainActivity(User user) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void showFailedError(String s) {
        Snackbar.make(buttonRectangle, s, Snackbar.LENGTH_SHORT).setAction("重新登录", v -> cleanText()).show();
    }

    private void cleanText() {
        TilPassword.getEditText().setText("");
        TilUsername.getEditText().setText("");
    }
}
