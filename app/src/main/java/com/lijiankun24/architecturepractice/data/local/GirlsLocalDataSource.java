package com.lijiankun24.architecturepractice.data.local;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.lijiankun24.architecturepractice.data.GirlsDataSource;
import com.lijiankun24.architecturepractice.data.local.db.AppDatabaseManager;
import com.lijiankun24.architecturepractice.data.local.db.entity.Girl;

import java.util.List;

/**
 * GirlsLocalDataSource.java
 * <p>
 * Created by lijiankun on 17/7/7.
 */

public class GirlsLocalDataSource implements GirlsDataSource {

    private static GirlsLocalDataSource INSTANCE = null;

    private GirlsLocalDataSource() {
    }

    public static GirlsLocalDataSource getInstance() {
        if (INSTANCE == null) {
            synchronized (GirlsLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new GirlsLocalDataSource();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public LiveData<List<Girl>> getGirls() {
        return AppDatabaseManager.getInstance().loadGirls();
    }

    @Override
    public LiveData<Girl> getGirl(@NonNull String id) {
        return null;
    }

    @Override
    public void refreshTasks() {
    }
}
