package com.lijiankun24.architecturepractice.data;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
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

    private boolean mIsLocalDataDirty = true;

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
    public LiveData<List<Girl>> getGirls() {
        if (mIsLocalDataDirty) {
            return getGirlsDataFromRemote();
        } else {
            return mGirlsLocalDataSource.getGirls();
        }
    }

    @Override
    public LiveData<Girl> getGirl(@NonNull String id) {
        return null;
    }

    @Override
    public void refreshTasks() {
        mIsLocalDataDirty = true;
    }

    private LiveData<List<Girl>> getGirlsDataFromRemote() {
        LiveData<Boolean> isGirlsGetSucceed = ((GirlsRemoteDataSource) mGirlsRemoteDataSource).isGirlsLoadSucceed();
        return Transformations.switchMap(isGirlsGetSucceed, new Function<Boolean, LiveData<List<Girl>>>() {
            @Override
            public LiveData<List<Girl>> apply(Boolean input) {
                if (Boolean.TRUE.equals(input)) {
                    refreshGirlsLocalDataSource(mGirlsRemoteDataSource.getGirls().getValue());
                    return mGirlsRemoteDataSource.getGirls();
                } else {
                    return mGirlsLocalDataSource.getGirls();
                }
            }
        });
    }

    private void refreshGirlsLocalDataSource(List<Girl> girls) {
        AppDatabaseManager.getInstance().insertGirls(girls);
        mIsLocalDataDirty = false;
    }
}