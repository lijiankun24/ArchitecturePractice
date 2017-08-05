package com.lijiankun24.architecturepractice.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.lijiankun24.architecturepractice.data.local.db.entity.Girl;
import com.lijiankun24.architecturepractice.data.local.db.entity.ZhihuStory;
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

    private final DataSource mRemoteDataSource;

    private final DataSource mLocalDataSource;

    private static Application sApplication = null;

    private DataRepository(@NonNull DataSource remoteDataSource,
                           @NonNull DataSource localDataSource) {
        mRemoteDataSource = remoteDataSource;
        mLocalDataSource = localDataSource;
    }

    static DataRepository getInstance(@NonNull DataSource remoteDataSource,
                                      @NonNull DataSource localDataSource,
                                      Application application) {
        if (INSTANCE == null) {
            synchronized (DataRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DataRepository(remoteDataSource, localDataSource);
                    sApplication = application;
                }
            }
        }
        return INSTANCE;
    }

    public LiveData<List<Girl>> getGirlList(int index) {
        if (Util.isNetworkConnected(sApplication.getApplicationContext())) {
            return mRemoteDataSource.getGirlList(index);
        } else {
            return mLocalDataSource.getGirlList(index);
        }
    }

    public LiveData<Boolean> isLoadingGirlList() {
        if (Util.isNetworkConnected(sApplication.getApplicationContext())) {
            return mRemoteDataSource.isLoadingGirlList();
        } else {
            return mLocalDataSource.isLoadingGirlList();
        }
    }


    public LiveData<List<ZhihuStory>> getZhihuList(@NonNull String date) {
        if (Util.isNetworkConnected(sApplication.getApplicationContext())) {
            if (date.equals("today")) {
                return mRemoteDataSource.getLastZhihuList();
            } else {
                return mRemoteDataSource.getMoreZhihuList(date);
            }
        } else {
            if (date.equals("today")) {
                return mLocalDataSource.getLastZhihuList();
            } else {
                return mLocalDataSource.getMoreZhihuList(date);
            }
        }
    }

    public LiveData<ZhihuStoryDetail> getZhihuDetail(String id) {
        return mRemoteDataSource.getZhihuDetail(id);
    }

    public LiveData<Boolean> isLoadingZhihuList() {
        if (Util.isNetworkConnected(sApplication.getApplicationContext())) {
            return mRemoteDataSource.isLoadingZhihuList();
        } else {
            return mLocalDataSource.isLoadingZhihuList();
        }
    }
}
