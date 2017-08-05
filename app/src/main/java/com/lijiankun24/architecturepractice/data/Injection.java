package com.lijiankun24.architecturepractice.data;

import android.app.Application;

import com.lijiankun24.architecturepractice.data.local.LocalDataSource;
import com.lijiankun24.architecturepractice.data.remote.RemoteDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Injection.java
 * <p>
 * Created by lijiankun on 17/7/7.
 */

public class Injection {

    public static DataRepository getDataRepository(Application application) {
        return DataRepository.getInstance(RemoteDataSource.getInstance(),
                LocalDataSource.getInstance(), application);
    }
}
