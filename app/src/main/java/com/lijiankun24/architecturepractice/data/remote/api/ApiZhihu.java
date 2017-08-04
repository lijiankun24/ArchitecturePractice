package com.lijiankun24.architecturepractice.data.remote.api;


import android.database.Observable;

import com.lijiankun24.architecturepractice.data.remote.model.ZhihuData;
import com.lijiankun24.architecturepractice.data.remote.model.ZhihuStoryDetail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * ApiZhihu.java
 * <p>
 * Created by lijiankun on 17/7/23.
 */

public interface ApiZhihu {

    @GET("api/4/news/latest")
    Call<ZhihuData> getLatestNews();

    @GET("/api/4/news/before/{date}")
    Call<ZhihuData> getTheDaily(@Path("date") String date);

    @GET("api/4/news/{id}")
    Call<ZhihuStoryDetail> getZhiHuStoryDetail(@Path("id") String id);
}
