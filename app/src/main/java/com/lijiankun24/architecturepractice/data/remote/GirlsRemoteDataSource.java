package com.lijiankun24.architecturepractice.data.remote;

import android.content.Context;
import android.support.annotation.NonNull;

import com.lijiankun24.architecturepractice.data.GirlsDataSource;
import com.lijiankun24.architecturepractice.data.local.db.entity.Girl;

/**
 * GirlsRemoteDataSource.java
 * <p>
 * Created by lijiankun on 17/7/7.
 */

public class GirlsRemoteDataSource implements GirlsDataSource{

    private static GirlsRemoteDataSource INSTANCE = null;

    private GirlsRemoteDataSource(Context context) {
    }

    public static GirlsRemoteDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (GirlsRemoteDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new GirlsRemoteDataSource(context.getApplicationContext());
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void getGirls(@NonNull GirlsDataSource.LoadGirlsCallback callback) {

    }

    @Override
    public Girl getGirl(@NonNull String id) {
        return null;
    }
}
