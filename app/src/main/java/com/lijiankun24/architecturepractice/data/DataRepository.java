package com.lijiankun24.architecturepractice.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.lijiankun24.architecturepractice.data.local.db.entity.Girl;
import com.lijiankun24.architecturepractice.data.remote.model.ZhihuStory;
import com.lijiankun24.architecturepractice.data.remote.model.ZhihuStoryDetail;
import com.lijiankun24.architecturepractice.utils.Util;

import java.util.List;

/**
 * DataRepository.java
 * <p>
 * Created by lijiankun on 17/7/7.
 */

public class DataRepository {

    private static DataRepository INSTANCE = null;

    private final DataSource mGirlRemoteDataSource;

    private final DataSource mGirlLocalDataSource;

    private static Application sApplication = null;

    private DataRepository(@NonNull DataSource girlsRemoteDataSource,
                           @NonNull DataSource girlsLocalDataSource) {
        mGirlRemoteDataSource = girlsRemoteDataSource;
        mGirlLocalDataSource = girlsLocalDataSource;
    }

    static DataRepository getInstance(@NonNull DataSource girlsRemoteDataSource,
                                      @NonNull DataSource girlsLocalDataSource,
                                      Application application) {
        if (INSTANCE == null) {
            synchronized (DataRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DataRepository(girlsRemoteDataSource, girlsLocalDataSource);
                    sApplication = application;
                }
            }
        }
        return INSTANCE;
    }

    public LiveData<List<Girl>> getGirlList(int index) {
        if (Util.isNetworkConnected(sApplication.getApplicationContext())) {
            return mGirlRemoteDataSource.getGirlList(index);
        } else {
            return mGirlLocalDataSource.getGirlList(index);
        }
    }

    public LiveData<Boolean> isLoadingGirlList() {
        return mGirlRemoteDataSource.isLoadingGirlList();
    }


    public LiveData<List<ZhihuStory>> getZhihuList(@NonNull String date) {
        if (date.equals("today")) {
            return mGirlRemoteDataSource.getLastZhihuList();
        } else {
            return mGirlRemoteDataSource.getMoreZhihuList(date);
        }
    }

    public LiveData<ZhihuStoryDetail> getZhihuDetail(String id) {
        return mGirlRemoteDataSource.getZhihuDetail(id);
    }

    public LiveData<Boolean> isLoadingZhihuList() {
        return mGirlRemoteDataSource.isLoadingZhihuList();
    }
}
