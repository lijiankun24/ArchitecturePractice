package com.lijiankun24.architecturepractice.data.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.lijiankun24.architecturepractice.data.GirlsDataSource;
import com.lijiankun24.architecturepractice.data.local.db.AppDatabaseManager;
import com.lijiankun24.architecturepractice.data.local.db.entity.Girl;

/**
 * GirlsLocalDataSource.java
 * <p>
 * Created by lijiankun on 17/7/7.
 */

public class GirlsLocalDataSource implements GirlsDataSource {

    private static GirlsLocalDataSource INSTANCE = null;

    private GirlsLocalDataSource(Context context) {
    }

    public static GirlsLocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (GirlsLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new GirlsLocalDataSource(context.getApplicationContext());
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void getGirls(@NonNull LoadGirlsCallback callback) {
        AppDatabaseManager.getInstance().loadGirls(callback);
    }

    @Override
    public Girl getGirl(@NonNull String id) {
        return null;
    }

    @Override
    public void refreshTasks() {
    }
}
