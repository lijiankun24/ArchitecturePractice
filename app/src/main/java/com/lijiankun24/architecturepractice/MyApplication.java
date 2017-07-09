package com.lijiankun24.architecturepractice;

import android.app.Application;

import com.lijiankun24.architecturepractice.data.local.db.AppDatabaseManager;

/**
 * MyApplication.java
 * <p>
 * Created by lijiankun on 17/7/9.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppDatabaseManager.getInstance().createDB(this);
    }
}
