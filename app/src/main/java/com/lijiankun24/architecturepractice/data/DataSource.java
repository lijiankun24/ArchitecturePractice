package com.lijiankun24.architecturepractice.data;

import android.arch.lifecycle.LiveData;

import com.lijiankun24.architecturepractice.data.local.db.entity.Girl;
import com.lijiankun24.architecturepractice.data.local.db.entity.ZhihuStory;
import com.lijiankun24.architecturepractice.data.remote.model.ZhihuStoryDetail;

import java.util.List;

/**
 * DataSource.java
 * <p>
 * Created by lijiankun on 17/7/7.
 */

public interface DataSource {

    /**
     * Girl 相关方法
     */
    LiveData<List<Girl>> getGirlList(int index);

    LiveData<Boolean> isLoadingGirlList();


    /**
     * Zhihu 相关方法
     */
    LiveData<List<ZhihuStory>> getLastZhihuList();

    LiveData<List<ZhihuStory>> getMoreZhihuList(String date);

    LiveData<ZhihuStoryDetail> getZhihuDetail(String id);

    LiveData<Boolean> isLoadingZhihuList();
}
