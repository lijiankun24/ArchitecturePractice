package com.lijiankun24.architecturepractice.ui.activity;

import android.arch.core.util.Function;
import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;

import com.lijiankun24.architecturepractice.R;
import com.lijiankun24.architecturepractice.data.local.db.entity.Girl;
import com.lijiankun24.architecturepractice.ui.fragment.GirlFragment;
import com.lijiankun24.architecturepractice.ui.fragment.GirlListFragment;
import com.lijiankun24.architecturepractice.utils.L;


/**
 * MainActivity.java
 * <p>
 * Created by lijiankun on 17/7/4.
 */
public class MainActivity extends LifecycleActivity {

    private MutableLiveData<Boolean> mLiveData = null;

    private Handler mHandler = new Handler();

    private Runnable mRunnable = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLiveData = new MutableLiveData<>();
        mLiveData.setValue(false);
        setContentView(R.layout.activity_main);
        showGirlList(savedInstanceState);
//        initLiveData();
        initTest();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLiveData.setValue(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLiveData.setValue(false);
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
        GirlFragment girlFragment = GirlFragment.newInstance("");
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_fragment_container, girlFragment, GirlFragment.TAG)
                .commit();
    }

    private void initLiveData() {
        mLiveData.setValue(false);
        Transformations.switchMap(mLiveData, new Function<Boolean, LiveData<Object>>() {
            @Override
            public LiveData<Object> apply(Boolean input) {
                L.i("input " + input);
                return null;
            }
        });
        mRunnable = new Runnable() {
            @Override
            public void run() {
                mLiveData.setValue(!mLiveData.getValue());
                mHandler.postDelayed(mRunnable, 5000);
            }
        };
        mHandler.postDelayed(mRunnable, 5000);
    }

    private void initTest() {
        mLiveData.observe(MainActivity.this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                L.i("aBoolean " + aBoolean);
            }
        });
//        mRunnable = new Runnable() {
//            @Override
//            public void run() {
//                mLiveData.setValue(!mLiveData.getValue());
//                mHandler.postDelayed(mRunnable, 5000);
//            }
//        };
//        mHandler.postDelayed(mRunnable, 5000);

        findViewById(R.id.tv_new_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TestActivity.class));
            }
        });
    }
}
