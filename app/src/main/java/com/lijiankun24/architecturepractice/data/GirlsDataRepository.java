package com.lijiankun24.architecturepractice.data;

import android.support.annotation.NonNull;

import com.lijiankun24.architecturepractice.data.local.db.AppDatabaseManager;
import com.lijiankun24.architecturepractice.data.local.db.entity.Girl;

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

    private boolean mIsLocalDataGirty = false;

    private GirlsDataRepository(@NonNull GirlsDataSource girlsRemoteDataSource,
                               @NonNull GirlsDataSource girlsLocalDataSource) {
        mGirlsRemoteDataSource = girlsRemoteDataSource;
        mGirlsLocalDataSource = girlsLocalDataSource;
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
    public void getGirls(@NonNull final LoadGirlsCallback callback) {
        if (mIsLocalDataGirty) {
            getGirlsDataFromRemote(callback);
        } else {
            mGirlsLocalDataSource.getGirls(new LoadGirlsCallback() {
                @Override
                public void onGirlsLoaded(List<Girl> girls) {
                    callback.onGirlsLoaded(girls);
                }

                @Override
                public void onGirlsNotAvailable() {
                    getGirlsDataFromRemote(callback);
                }
            });
        }
    }

    @Override
    public Girl getGirl(@NonNull String id) {
        Girl girl = mGirlsLocalDataSource.getGirl(id);
        if (girl != null) {
            return girl;
        }
        girl = mGirlsRemoteDataSource.getGirl(id);
        return girl;
    }

    @Override
    public void refreshTasks() {
        mIsLocalDataGirty = true;
    }

    private void getGirlsDataFromRemote(@NonNull final LoadGirlsCallback callback) {
        mGirlsRemoteDataSource.getGirls(new LoadGirlsCallback() {
            @Override
            public void onGirlsLoaded(List<Girl> girls) {
                refreshGirlsLocalDataSource(girls);
                callback.onGirlsLoaded(girls);
            }

            @Override
            public void onGirlsNotAvailable() {
                callback.onGirlsNotAvailable();
            }
        });
    }

    private void refreshGirlsLocalDataSource(List<Girl> girls) {
        AppDatabaseManager.getInstance().insertGirls(girls);
    }
}
