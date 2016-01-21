package com.example.didiorder.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.didiorder.R;
import com.example.didiorder.StartActivity;
import com.example.didiorder.adapter.FragmentAdapter;
import com.example.didiorder.adapter.StickListTaskAdapter;
import com.example.didiorder.bean.Ijob;
import com.example.didiorder.bean.User;
import com.example.didiorder.event.UserEvent;
import com.example.didiorder.fragment.FragmentOrder;
import com.example.didiorder.presenter.MainActivityPresenter;
import com.example.didiorder.view.IMainView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity implements IMainView{
    private  User user;
    private SystemBarTintManager tintManager;
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private  TextView textView;
    private NavigationView navigationView;
    private SimpleDraweeView UsersimpleDraweeView;
    private Context context;
    private MainActivityPresenter mainActivityPresenter;
    private FloatingActionButton floatingActionButton;
    private Ijob ijob;
    private FragmentOrder fragmentAll,fragmentUnder,fragmentFinish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams. FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.activity_main);
        context=this;
        mainActivityPresenter = new MainActivityPresenter(this);
        EventBus.getDefault().register(context);
        tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        initData();
        initView();
        initClick();
    }
    private void initData(){
        user = BmobUser.getCurrentUser(this,User.class);
        ijob = mainActivityPresenter.getJob(user);
        mainActivityPresenter.getOrder();
        fragmentAll = ijob.getFragment();
        fragmentFinish= ijob.getFragment();
        fragmentUnder =  ijob.getFragment();
    }
    private void initView(){
        tintManager.setStatusBarTintResource(R.mipmap.status);
        tintManager.setNavigationBarTintResource(R.mipmap.navigation);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("主页");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerLayout =(DrawerLayout)findViewById(R.id.dl_main_drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.draw_open,
                R.string.draw_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setOffscreenPageLimit(2);
        setupViewPager();
        floatingActionButton = (FloatingActionButton)findViewById(R.id.fab) ;
        ijob.setFloat(floatingActionButton);
        navigationView = (NavigationView)findViewById(R.id.nv_main_navigation);
        textView = (TextView)navigationView.getHeaderView(0).findViewById(R.id.navigation_header_text);
        textView.setText(user.getName());
        UsersimpleDraweeView = (SimpleDraweeView)navigationView.getHeaderView(0).findViewById(R.id.navigation_header_image);
        if (user.getUser_image_url()!=null&&!user.getUser_image_url().isEmpty()){
            Uri uri = Uri.parse(user.getUser_image_url());

            mainActivityPresenter.setImageUri(uri);
        }

    }
    private void initClick(){
        navigationView.setNavigationItemSelectedListener(item -> {
            mDrawerLayout.closeDrawers();
            switch (item.getItemId()){
                case R.id.userImage:{
                    mainActivityPresenter.startNewActivity(new Intent(context,UserActivity.class));
                    break;
                }
                case R.id.loginOut:{
                    mainActivityPresenter.LogOut(context);
                    mainActivityPresenter.startNewActivity(new Intent(context,StartActivity.class));
                    finish();
                    break;
                }
                case R.id.income:{
                    mainActivityPresenter.startNewActivity(new Intent(context,IncomeActivity.class));
                    break;
                }
                case R.id.updata:{
                    mainActivityPresenter.startNewActivity(new Intent(context,UploadActivity.class));
                    break;
                }
            }
            return false;
        });
        floatingActionButton.setOnClickListener(v -> {
            mainActivityPresenter.startNewActivity(new Intent(context,OrderActivity.class));
        });
    }
    private void setupViewPager() {
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        List<String> titles = new ArrayList<>();
        titles.add("全部订单");
        titles.add("未完成");
        titles.add("已结束");
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(2)));
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(fragmentAll);
        fragments.add(fragmentUnder);
        fragments.add(fragmentFinish);
        FragmentAdapter adapter =
                new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);
    }

    @Override
    public void setImageUri(Uri uri) {
        UsersimpleDraweeView.setImageURI(uri);
    }

    @Override
    public void startNewActivity(Intent intent) {
        startActivity(intent);
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void setAdapter(StickListTaskAdapter stickListTaskAdapter) {
        fragmentAll.setAdapter(stickListTaskAdapter);
    }

    public void onEventMainThread(UserEvent event) { //事件总线，UserActivity发送的事件，都在这里处理
        user = BmobUser.getCurrentUser(this,User.class);
        mainActivityPresenter.setImageUri(Uri.parse(user.getUser_image_url()));
        textView.setText(user.getName());
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
