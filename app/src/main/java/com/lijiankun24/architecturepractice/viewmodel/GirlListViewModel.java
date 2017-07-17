package com.lijiankun24.architecturepractice.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.lijiankun24.architecturepractice.data.GirlsDataRepository;
import com.lijiankun24.architecturepractice.data.local.db.entity.Girl;

import java.util.List;

/**
 * GirlListViewModel.java
 * <p>
 * Created by lijiankun on 17/7/4.
 */

public class GirlListViewModel extends AndroidViewModel {

    private GirlsDataRepository mGirlsDataRepository = null;

    private LiveData<Boolean> mBooleanLiveData = null;

    private GirlListViewModel(Application application, GirlsDataRepository girlsDataRepository) {
        super(application);
        mGirlsDataRepository = girlsDataRepository;
        mBooleanLiveData = new MediatorLiveData<Boolean>(){
            @Override
            public <S> void addSource(LiveData<S> source, Observer<S> onChanged) {
                super.addSource(source, onChanged);
            }
        };
    }

    public LiveData<List<Girl>> getLiveData() {
        return mGirlsDataRepository.getGirls();
    }

    public LiveData<Boolean> isLoadingGirlListData() {
        return mGirlsDataRepository.isLoadingGirlListData();
    }

    public void refreshGrilsData() {
        mGirlsDataRepository.refreshTasks();
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
