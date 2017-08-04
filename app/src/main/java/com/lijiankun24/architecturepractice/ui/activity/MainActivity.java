package com.lijiankun24.architecturepractice.ui.activity;

import android.arch.lifecycle.LifecycleActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.jaeger.library.StatusBarUtil;
import com.lijiankun24.architecturepractice.R;
import com.lijiankun24.architecturepractice.ui.fragment.GirlListFragment;
import com.lijiankun24.architecturepractice.ui.fragment.ZhihuListFragment;
import com.lijiankun24.architecturepractice.utils.Util;

import java.util.ArrayList;
import java.util.List;


/**
 * MainActivity.java
 * <p>
 * Created by lijiankun on 17/7/4.
 */
public class MainActivity extends LifecycleActivity {

    private ViewPager mViewPager = null;

    private List<Fragment> mFragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        initStatusBar();

        mFragmentList.add(new GirlListFragment());
        mFragmentList.add(new ZhihuListFragment());

        mViewPager = findViewById(R.id.vp_home);
        mViewPager.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager()));

        BottomNavigationBar navigationBar = findViewById(R.id.bottom_navigation_bar);
        navigationBar.setTabSelectedListener(new MainOnTabSelectedListener());
        navigationBar.addItem(new BottomNavigationItem(R.drawable.ic_favorite, "Girl"))
                .addItem(new BottomNavigationItem(R.drawable.ic_grade, "Zhihu"))
                .initialise();
    }

    private void initStatusBar() {
        int statusBarHeight = Util.getStatusBarHeight(MainActivity.this);
        if (statusBarHeight == -1) {
            return;
        }
        View fakeStatusBar = findViewById(R.id.fake_status_bar);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) fakeStatusBar.getLayoutParams();
        params.height = statusBarHeight;
        fakeStatusBar.setLayoutParams(params);
        StatusBarUtil.setTranslucentForImageViewInFragment(MainActivity.this, null);
    }

    private class MainOnTabSelectedListener implements BottomNavigationBar.OnTabSelectedListener {

        @Override
        public void onTabSelected(int position) {
            mViewPager.setCurrentItem(position);
        }

        @Override
        public void onTabUnselected(int position) {
        }

        @Override
        public void onTabReselected(int position) {
        }
    }

    private class MainFragmentPagerAdapter extends FragmentPagerAdapter {

        private MainFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }
}
