package com.lijiankun24.architecturepractice.data;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.lijiankun24.architecturepractice.data.local.db.entity.Girl;

import java.util.List;

/**
 * GirlsDataSource.java
 * <p>
 * Created by lijiankun on 17/7/7.
 */

public interface GirlsDataSource {

    LiveData<List<Girl>> getGirls();

    LiveData<Girl> getGirl(@NonNull String id);

    void refreshTasks();
}
