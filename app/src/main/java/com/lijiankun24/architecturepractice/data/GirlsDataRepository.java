package com.lijiankun24.architecturepractice.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.lijiankun24.architecturepractice.data.local.db.entity.Girl;
import com.lijiankun24.architecturepractice.data.remote.model.ZhihuStory;
import com.lijiankun24.architecturepractice.utils.Util;

import java.util.List;

/**
 * GirlsDataRepository.java
 * <p>
 * Created by lijiankun on 17/7/7.
 */

public class GirlsDataRepository {

    private static GirlsDataRepository INSTANCE = null;

    private final GirlsDataSource mGirlRemoteDataSource;

    private final GirlsDataSource mGirlLocalDataSource;

    private static Application sApplication = null;

    private GirlsDataRepository(@NonNull GirlsDataSource girlsRemoteDataSource,
                                @NonNull GirlsDataSource girlsLocalDataSource) {
        mGirlRemoteDataSource = girlsRemoteDataSource;
        mGirlLocalDataSource = girlsLocalDataSource;
    }

    static GirlsDataRepository getInstance(@NonNull GirlsDataSource girlsRemoteDataSource,
                                           @NonNull GirlsDataSource girlsLocalDataSource,
                                           Application application) {
        if (INSTANCE == null) {
            synchronized (GirlsDataRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new GirlsDataRepository(girlsRemoteDataSource, girlsLocalDataSource);
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

    public LiveData<Boolean> isLoadingZhihuList() {
        return mGirlRemoteDataSource.isLoadingZhihuList();
    }
}
