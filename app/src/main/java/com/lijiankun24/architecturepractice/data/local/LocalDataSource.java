package com.lijiankun24.architecturepractice.data.local;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.lijiankun24.architecturepractice.data.DataSource;
import com.lijiankun24.architecturepractice.data.local.db.AppDatabaseManager;
import com.lijiankun24.architecturepractice.data.local.db.entity.Girl;
import com.lijiankun24.architecturepractice.data.remote.model.ZhihuStory;

import java.util.List;

/**
 * LocalDataSource.java
 * <p>
 * Created by lijiankun on 17/7/7.
 */

public class LocalDataSource implements DataSource {

    private static LocalDataSource INSTANCE = null;

    private LocalDataSource() {
    }

    public static LocalDataSource getInstance() {
        if (INSTANCE == null) {
            synchronized (LocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LocalDataSource();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public LiveData<List<Girl>> getGirlList(int index) {
        return AppDatabaseManager.getInstance().loadGirlList();
    }

    @Override
    public LiveData<Girl> getGirl(@NonNull String id) {
        return null;
    }

    @Override
    public LiveData<Boolean> isLoadingGirlList() {
        return null;
    }


    @Override
    public LiveData<List<ZhihuStory>> getLastZhihuList() {
        return null;
    }

    @Override
    public LiveData<List<ZhihuStory>> getMoreZhihuList(String date) {
        return null;
    }

    @Override
    public LiveData<Boolean> isLoadingZhihuList() {
        return null;
    }
}
