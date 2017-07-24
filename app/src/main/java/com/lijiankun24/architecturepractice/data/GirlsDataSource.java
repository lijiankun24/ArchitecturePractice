package com.lijiankun24.architecturepractice.data;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.lijiankun24.architecturepractice.data.local.db.entity.Girl;
import com.lijiankun24.architecturepractice.data.remote.model.ZhihuStory;

import java.util.List;

/**
 * GirlsDataSource.java
 * <p>
 * Created by lijiankun on 17/7/7.
 */

public interface GirlsDataSource {

    /**
     * Girl 相关方法
     */
    LiveData<List<Girl>> getGirlList(int index);

    LiveData<Girl> getGirl(@NonNull String id);

    LiveData<Boolean> isLoadingGirlList();


    /**
     * Zhihu 相关方法
     */
    LiveData<List<ZhihuStory>> getLastZhihuList();

    LiveData<List<ZhihuStory>> getMoreZhihuList(String date);

    LiveData<Boolean> isLoadingZhihuList();
}
