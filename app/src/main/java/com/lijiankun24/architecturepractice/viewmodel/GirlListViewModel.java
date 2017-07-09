package com.lijiankun24.architecturepractice.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.lijiankun24.architecturepractice.data.GirlsDataRepository;
import com.lijiankun24.architecturepractice.data.GirlsDataSource;
import com.lijiankun24.architecturepractice.data.local.db.entity.Girl;

import java.util.List;

/**
 * GirlListViewModel.java
 * <p>
 * Created by lijiankun on 17/7/4.
 */

public class GirlListViewModel extends AndroidViewModel {

    private static final MutableLiveData ABSENT = new MutableLiveData();

    private GirlsDataRepository mGirlsDataRepository = null;

    {
        ABSENT.setValue(null);
    }

    private final MutableLiveData<List<Girl>> mLiveData = new MutableLiveData<>();

    public GirlListViewModel(Application application, GirlsDataRepository girlsDataRepository) {
        super(application);
        mGirlsDataRepository = girlsDataRepository;
    }

    public LiveData<List<Girl>> getLiveData() {
        mGirlsDataRepository.getGirls(new GirlsDataSource.LoadGirlsCallback() {
            @Override
            public void onGirlsLoaded(List<Girl> girls) {
                mLiveData.setValue(girls);
            }

            @Override
            public void onGirlsNotAvailable() {

            }
        });
        return mLiveData;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final GirlsDataRepository mGirlsDataRepository;

        public Factory(@NonNull Application application, GirlsDataRepository girlsDataRepository) {
            mApplication = application;
            mGirlsDataRepository = girlsDataRepository;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new GirlListViewModel(mApplication, mGirlsDataRepository);
        }
    }
}
