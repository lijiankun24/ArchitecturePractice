package com.lijiankun24.architecturepractice;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.lijiankun24.architecturepractice.data.local.db.AppDatabaseManager;
import com.lijiankun24.architecturepractice.utils.Consts;

/**
 * MyApplication.java
 * <p>
 * Created by lijiankun on 17/7/9.
 */

public class MyApplication extends Application {

    private static MyApplication INSTANCE = null;

    public static MyApplication getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        AppDatabaseManager.getInstance().createDB(this);
        if (Consts.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
    }
}
