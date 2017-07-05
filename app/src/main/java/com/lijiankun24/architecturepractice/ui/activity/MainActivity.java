package com.lijiankun24.architecturepractice.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lijiankun24.architecturepractice.R;
import com.lijiankun24.architecturepractice.db.entity.Girl;
import com.lijiankun24.architecturepractice.ui.fragment.GirlFragment;
import com.lijiankun24.architecturepractice.ui.fragment.GirlListFragment;


/**
 * MainActivity.java
 * <p>
 * Created by lijiankun on 17/7/4.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            GirlListFragment girlListFragment = new GirlListFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_main, girlListFragment, GirlListFragment.TAG)
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
                .add(R.id.fl_main, girlFragment, GirlFragment.TAG)
                .commit();
    }
}
