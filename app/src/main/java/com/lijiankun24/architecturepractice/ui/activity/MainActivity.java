package com.lijiankun24.architecturepractice.ui.activity;

import android.arch.lifecycle.LifecycleActivity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentManager;

import com.jaeger.library.StatusBarUtil;
import com.lijiankun24.architecturepractice.R;
import com.lijiankun24.architecturepractice.data.local.db.entity.Girl;
import com.lijiankun24.architecturepractice.ui.fragment.GirlFragment;
import com.lijiankun24.architecturepractice.ui.fragment.GirlListFragment;


/**
 * MainActivity.java
 * <p>
 * Created by lijiankun on 17/7/4.
 */
public class MainActivity extends LifecycleActivity implements FragmentManager.OnBackStackChangedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showGirlList(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        StatusBarUtil.setTranslucentForImageViewInFragment(MainActivity.this, null);
    }

    @Override
    public void onBackStackChanged() {

    }

    private void showGirlList(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            GirlListFragment girlListFragment = new GirlListFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_fragment_container, girlListFragment, GirlListFragment.TAG)
                    .commit();
        }
    }

    public void showGirl(Girl girl) {
        if (girl == null) {
            return;
        }
        GirlFragment girlFragment = GirlFragment.newInstance(girl.getUrl());
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_fragment_container, girlFragment, GirlFragment.TAG)
                .addToBackStack(null)
                .commit();
    }

    private void shouldDisplayHomeUp() {
        //Enable Up button only  if there are entries in the back stack
        boolean canback = getSupportFragmentManager().getBackStackEntryCount() > 0;
//        getSupportActionBar().setDisplayHomeAsUpEnabled(canback);
    }
}
