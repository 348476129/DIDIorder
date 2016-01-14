package com.example.didiorder.activity;

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

import com.example.didiorder.R;
import com.example.didiorder.adapter.FragmentAdapter;
import com.example.didiorder.bean.User;
import com.example.didiorder.fragment.FragmentOrder;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;

public class MainActivity extends AppCompatActivity {
    private  User user;
    private SystemBarTintManager tintManager;
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private  TextView textView;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams. FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        initClick();
    }
    private void initData(){
        user = BmobUser.getCurrentUser(this,User.class);
    }
    private void initView(){
        tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
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
        navigationView = (NavigationView)findViewById(R.id.nv_main_navigation);
        textView = (TextView)navigationView.getHeaderView(0).findViewById(R.id.navigation_header_text);
        textView.setText("厨师A");
    }
    private void initClick(){
        navigationView.setNavigationItemSelectedListener(item -> {
            mDrawerLayout.closeDrawers();
            Snackbar.make(toolbar,item.getTitle()+"被点击",Snackbar.LENGTH_SHORT).show();
            return false;
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
        fragments.add(new FragmentOrder());
        fragments.add(new FragmentOrder());
        fragments.add(new FragmentOrder());
        FragmentAdapter adapter =
                new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);
    }
}
