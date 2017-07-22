package com.lijiankun24.architecturepractice.data;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.lijiankun24.architecturepractice.data.local.db.AppDatabaseManager;
import com.lijiankun24.architecturepractice.data.local.db.entity.Girl;
import com.lijiankun24.architecturepractice.data.remote.GirlsRemoteDataSource;

import java.util.List;

/**
 * GirlsDataRepository.java
 * <p>
 * Created by lijiankun on 17/7/7.
 */

public class GirlsDataRepository implements GirlsDataSource {

    private static GirlsDataRepository INSTANCE = null;

    private final GirlsDataSource mGirlsRemoteDataSource;

    private final GirlsDataSource mGirlsLocalDataSource;

    private final LiveData<Boolean> mIsLoadingGirlListData;

    private GirlsDataRepository(@NonNull GirlsDataSource girlsRemoteDataSource,
                                @NonNull GirlsDataSource girlsLocalDataSource) {
        mGirlsRemoteDataSource = girlsRemoteDataSource;
        mGirlsLocalDataSource = girlsLocalDataSource;
        mIsLoadingGirlListData = ((GirlsRemoteDataSource) mGirlsRemoteDataSource).isGirlsLoadSucceed();
    }

    static GirlsDataRepository getInstance(@NonNull GirlsDataSource girlsRemoteDataSource,
                                           @NonNull GirlsDataSource girlsLocalDataSource) {
        if (INSTANCE == null) {
            synchronized (GirlsDataRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new GirlsDataRepository(girlsRemoteDataSource, girlsLocalDataSource);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public LiveData<List<Girl>> getGirls(int index) {
        return null;
    }

    @Override
    public LiveData<Girl> getGirl(@NonNull String id) {
        return null;
    }

    public LiveData<List<Girl>> getGirlsFromLocal(int index) {
        return mGirlsLocalDataSource.getGirls(index);
    }

    public LiveData<List<Girl>> getGirlsFromRemote(int index) {
        return mGirlsRemoteDataSource.getGirls(index);
    }

    public LiveData<Boolean> isLoadingGirlListData() {
        return mIsLoadingGirlListData;
    }

    private void refreshGirlsLocalDataSource(List<Girl> girls) {
        AppDatabaseManager.getInstance().insertGirls(girls);
    }
}
