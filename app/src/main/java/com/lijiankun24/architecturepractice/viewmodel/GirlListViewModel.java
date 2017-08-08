package com.lijiankun24.architecturepractice.viewmodel;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.lijiankun24.architecturepractice.MyApplication;
import com.lijiankun24.architecturepractice.data.DataRepository;
import com.lijiankun24.architecturepractice.data.local.db.entity.Girl;
import com.lijiankun24.architecturepractice.utils.Util;

import java.util.List;

/**
 * GirlListViewModel.java
 * <p>
 * Created by lijiankun on 17/7/4.
 */

public class GirlListViewModel extends AndroidViewModel {

    private final MutableLiveData<Integer> mGirlPageIndex = new MutableLiveData<>();

    private final LiveData<List<Girl>> mGirls;

    private DataRepository mGirlsDataRepository = null;

    private GirlListViewModel(Application application, DataRepository girlsDataRepository) {
        super(application);
        mGirlsDataRepository = girlsDataRepository;
        mGirls = Transformations.switchMap(mGirlPageIndex, new Function<Integer, LiveData<List<Girl>>>() {
            @Override
            public LiveData<List<Girl>> apply(Integer input) {
                return mGirlsDataRepository.getGirlList(input);
            }
        });
    }

    public LiveData<List<Girl>> getGilrsLiveData() {
        return mGirls;
    }

    public void refreshGrilsData() {
        mGirlPageIndex.setValue(1);
    }

    public void loadNextPageGirls() {
        if (!Util.isNetworkConnected(MyApplication.getInstance())) {
            return;
        }
        mGirlPageIndex.setValue((mGirlPageIndex.getValue() == null ? 1 : mGirlPageIndex.getValue() + 1));
    }

    public LiveData<Boolean> getLoadMoreState() {
        return mGirlsDataRepository.isLoadingGirlList();
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final DataRepository mGirlsDataRepository;

        public Factory(@NonNull Application application, DataRepository girlsDataRepository) {
            mApplication = application;
            mGirlsDataRepository = girlsDataRepository;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new GirlListViewModel(mApplication, mGirlsDataRepository);
        }
    }
}
