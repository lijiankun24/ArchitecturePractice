package com.lijiankun24.architecturepractice.data.remote;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.lijiankun24.architecturepractice.data.DataSource;
import com.lijiankun24.architecturepractice.data.local.db.AppDatabaseManager;
import com.lijiankun24.architecturepractice.data.local.db.entity.Girl;
import com.lijiankun24.architecturepractice.data.local.db.entity.ZhihuStory;
import com.lijiankun24.architecturepractice.data.remote.api.ApiGirl;
import com.lijiankun24.architecturepractice.data.remote.api.ApiManager;
import com.lijiankun24.architecturepractice.data.remote.api.ApiZhihu;
import com.lijiankun24.architecturepractice.data.remote.model.GirlData;
import com.lijiankun24.architecturepractice.data.remote.model.ZhihuData;
import com.lijiankun24.architecturepractice.data.remote.model.ZhihuStoryDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * RemoteDataSource.java
 * <p>
 * Created by lijiankun on 17/7/7.
 */

public class RemoteDataSource implements DataSource {

    private static RemoteDataSource INSTANCE = null;

    private final MutableLiveData<Boolean> mIsLoadingGirlList;

    private final MutableLiveData<Boolean> mIsLoadingZhihuList;

    private final MutableLiveData<List<Girl>> mGirlList;

    private final MutableLiveData<List<ZhihuStory>> mZhihuList;

    private final MutableLiveData<ZhihuStoryDetail> mZhihuDetail;

    private final ApiGirl mApiGirl;

    private final ApiZhihu mApiZhihu;

    private String mZhihuPageDate;

    {
        mIsLoadingGirlList = new MutableLiveData<>();
        mGirlList = new MutableLiveData<>();

        mIsLoadingZhihuList = new MutableLiveData<>();
        mZhihuDetail = new MutableLiveData<>();
        mZhihuList = new MutableLiveData<>();
    }

    private RemoteDataSource() {
        mApiGirl = ApiManager.getInstance().getApiGirl();
        mApiZhihu = ApiManager.getInstance().getApiZhihu();
    }

    public static RemoteDataSource getInstance() {
        if (INSTANCE == null) {
            synchronized (RemoteDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RemoteDataSource();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public LiveData<List<Girl>> getGirlList(int index) {
        mIsLoadingGirlList.setValue(true);
        mApiGirl.getGirlsData(index)
                .enqueue(new Callback<GirlData>() {
                    @Override
                    public void onResponse(Call<GirlData> call, Response<GirlData> response) {
                        mIsLoadingGirlList.setValue(false);
                        if (response.isSuccessful() || !response.body().error) {
                            mGirlList.setValue(response.body().results);
                            refreshLocalGirlList(response.body().results);
                        }
                    }

                    @Override
                    public void onFailure(Call<GirlData> call, Throwable t) {
                        mIsLoadingGirlList.setValue(false);
                    }
                });
        return mGirlList;
    }

    @Override
    public MutableLiveData<Boolean> isLoadingGirlList() {
        return mIsLoadingGirlList;
    }

    @Override
    public LiveData<List<ZhihuStory>> getLastZhihuList() {
        mIsLoadingZhihuList.setValue(true);
        mApiZhihu.getLatestNews()
                .enqueue(new Callback<ZhihuData>() {
                    @Override
                    public void onResponse(Call<ZhihuData> call, Response<ZhihuData> response) {
                        if (response.isSuccessful()) {
                            mZhihuList.setValue(response.body().getStories());
                            refreshLocalZhihuList(response.body().getStories());
                            mZhihuPageDate = response.body().getDate();
                        }
                        mIsLoadingZhihuList.setValue(false);
                    }

                    @Override
                    public void onFailure(Call<ZhihuData> call, Throwable t) {
                        mIsLoadingZhihuList.setValue(false);
                    }
                });
        return mZhihuList;
    }

    @Override
    public LiveData<List<ZhihuStory>> getMoreZhihuList(String date) {
        mIsLoadingZhihuList.setValue(true);
        mApiZhihu.getTheDaily(mZhihuPageDate)
                .enqueue(new Callback<ZhihuData>() {
                    @Override
                    public void onResponse(Call<ZhihuData> call, Response<ZhihuData> response) {
                        if (response.isSuccessful()) {
                            mZhihuList.setValue(response.body().getStories());
                            refreshLocalZhihuList(response.body().getStories());
                            mZhihuPageDate = response.body().getDate();
                        }
                        mIsLoadingZhihuList.setValue(false);
                    }

                    @Override
                    public void onFailure(Call<ZhihuData> call, Throwable t) {
                        mIsLoadingZhihuList.setValue(false);
                    }
                });
        return mZhihuList;
    }

    @Override
    public LiveData<ZhihuStoryDetail> getZhihuDetail(String id) {
        mApiZhihu.getZhiHuStoryDetail(id)
                .enqueue(new Callback<ZhihuStoryDetail>() {
                    @Override
                    public void onResponse(Call<ZhihuStoryDetail> call, Response<ZhihuStoryDetail> response) {
                        if (response.isSuccessful()) {
                            mZhihuDetail.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<ZhihuStoryDetail> call, Throwable t) {

                    }
                });
        return mZhihuDetail;
    }

    @Override
    public MutableLiveData<Boolean> isLoadingZhihuList() {
        return mIsLoadingZhihuList;
    }

    private void refreshLocalGirlList(List<Girl> girlList) {
        if (girlList == null || girlList.isEmpty()) {
            return;
        }
        AppDatabaseManager.getInstance().insertGirlList(girlList);
    }

    private void refreshLocalZhihuList(List<ZhihuStory> zhihuStoryList) {
        if (zhihuStoryList == null || zhihuStoryList.isEmpty()) {
            return;
        }
        AppDatabaseManager.getInstance().insertZhihuList(zhihuStoryList);
    }
}
