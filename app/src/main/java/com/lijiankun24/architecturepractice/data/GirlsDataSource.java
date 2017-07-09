package com.lijiankun24.architecturepractice.data;

import android.support.annotation.NonNull;

import com.lijiankun24.architecturepractice.data.local.db.entity.Girl;

import java.util.List;

/**
 * GirlsDataSource.java
 * GirlsRepository
 * <p>
 * Created by lijiankun on 17/7/7.
 */

public interface GirlsDataSource {

    interface LoadGirlsCallback {
        void onGirlsLoaded(List<Girl> girls);

        void onGirlsNotAvailable();
    }

    void getGirls(@NonNull LoadGirlsCallback callback);

    Girl getGirl(@NonNull String id);

    void refreshTasks();
}
