package com.lijiankun24.architecturepractice.data.remote;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.lijiankun24.architecturepractice.data.GirlsDataSource;
import com.lijiankun24.architecturepractice.data.local.db.entity.Girl;
import com.lijiankun24.architecturepractice.data.remote.api.ApiGirl;
import com.lijiankun24.architecturepractice.data.remote.api.ApiManager;
import com.lijiankun24.architecturepractice.data.remote.model.GirlData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * GirlsRemoteDataSource.java
 * <p>
 * Created by lijiankun on 17/7/7.
 */

public class GirlsRemoteDataSource implements GirlsDataSource {

    private static GirlsRemoteDataSource INSTANCE = null;

    private final MutableLiveData<Boolean> mIsGirlGetSucceed;

    private final MutableLiveData<List<Girl>> mGirls;

    private final ApiGirl mApiGirl;

    {
        mIsGirlGetSucceed = new MutableLiveData<>();
        mIsGirlGetSucceed.setValue(false);
        mGirls = new MutableLiveData<>();
        mGirls.postValue(null);
    }

    private GirlsRemoteDataSource() {
        mApiGirl = ApiManager.getInstance().getApiGirl();
    }

    public static GirlsRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            synchronized (GirlsRemoteDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new GirlsRemoteDataSource();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public LiveData<List<Girl>> getGirls(int index) {
        mApiGirl.getGirlsData(index)
                .enqueue(new Callback<GirlData>() {
                    @Override
                    public void onResponse(Call<GirlData> call, Response<GirlData> response) {
                        mIsGirlGetSucceed.setValue(false);
                        if (response.isSuccessful() || !response.body().error) {
                            mGirls.setValue(response.body().results);
                        }
                    }

                    @Override
                    public void onFailure(Call<GirlData> call, Throwable t) {
                        mGirls.setValue(new ArrayList<Girl>());
                        mIsGirlGetSucceed.setValue(false);
                    }
                });
        return mGirls;
    }

    @Override
    public LiveData<Girl> getGirl(@NonNull String id) {
        return null;
    }

    public LiveData<Boolean> isGirlsLoadSucceed() {
        return mIsGirlGetSucceed;
    }
}
