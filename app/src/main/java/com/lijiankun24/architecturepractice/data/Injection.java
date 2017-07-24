package com.lijiankun24.architecturepractice.data;

import android.app.Application;
import android.content.Context;

import com.lijiankun24.architecturepractice.data.local.GirlsLocalDataSource;
import com.lijiankun24.architecturepractice.data.remote.GirlsRemoteDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Injection.java
 * <p>
 * Created by lijiankun on 17/7/7.
 */

public class Injection {

    public static GirlsDataRepository getGirlsDataRepository(Application application) {
        return GirlsDataRepository.getInstance(GirlsRemoteDataSource.getInstance(),
                GirlsLocalDataSource.getInstance(), application);
    }
}
