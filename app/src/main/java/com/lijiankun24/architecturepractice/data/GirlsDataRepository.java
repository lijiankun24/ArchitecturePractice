package com.lijiankun24.architecturepractice.data;

import android.support.annotation.NonNull;

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

    public GirlsDataRepository(@NonNull GirlsDataSource girlsRemoteDataSource,
                               @NonNull GirlsDataSource girlsLocalDataSource) {
        mGirlsRemoteDataSource = girlsRemoteDataSource;
        mGirlsLocalDataSource = girlsLocalDataSource;
    }

    public static GirlsDataRepository getInstance(@NonNull GirlsDataSource girlsRemoteDataSource,
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
        mGirlsLocalDataSource.getGirls(new LoadGirlsCallback() {
            @Override
            public void onGirlsLoaded(List<Girl> girls) {
                callback.onGirlsLoaded(girls);
            }

            @Override
            public void onGirlsNotAvailable() {
                mGirlsRemoteDataSource.getGirls(callback);
            }
        });
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
}
