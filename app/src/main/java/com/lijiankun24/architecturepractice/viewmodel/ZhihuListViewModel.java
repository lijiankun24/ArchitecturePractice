package com.lijiankun24.architecturepractice.viewmodel;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lijiankun24.architecturepractice.data.DataRepository;
import com.lijiankun24.architecturepractice.data.remote.model.ZhihuStory;

import java.util.List;

/**
 * ZhihuListViewModel.java
 * <p>
 * Created by lijiankun on 17/7/30.
 */

public class ZhihuListViewModel extends AndroidViewModel {

    private MutableLiveData<String> mZhihuPageDate = new MutableLiveData<>();

    private final LiveData<List<ZhihuStory>> mZhihuList;

    private LoadHandler mLoadHandler = null;

    private DataRepository mDataRepository = null;

    public ZhihuListViewModel(Application application, DataRepository dataRepository) {
        super(application);
        mDataRepository = dataRepository;
        mLoadHandler = new LoadHandler(mDataRepository);
        mZhihuList = Transformations.switchMap(mZhihuPageDate, new Function<String, LiveData<List<ZhihuStory>>>() {
            @Override
            public LiveData<List<ZhihuStory>> apply(String input) {
                return mDataRepository.getZhihuList(input);
            }
        });
    }

    public LiveData<List<ZhihuStory>> getZhihuList() {
        return mZhihuList;
    }

    public void refreshZhihusData() {
        mLoadHandler.startLoadGirls();
        mZhihuPageDate.setValue("today");
    }

    public void loadNextPageZhihu() {
        mLoadHandler.startLoadGirls();
        mZhihuPageDate.setValue(String.valueOf(System.currentTimeMillis()));
    }

    public LiveData<Boolean> isLoadingZhihuList() {
        return mLoadHandler.getLoadMoreState();
    }

    private static class LoadHandler implements Observer<Boolean> {

        private final MutableLiveData<Boolean> mLoadMoreState;

        private LiveData<Boolean> mIsLoadingZhihuList = null;

        private DataRepository mDataRepository = null;

        private LoadHandler(DataRepository girlsDataRepository) {
            mDataRepository = girlsDataRepository;
            mLoadMoreState = new MutableLiveData<>();
        }

        private void startLoadGirls() {
            unregister();
            mIsLoadingZhihuList = mDataRepository.isLoadingZhihuList();
            mIsLoadingZhihuList.observeForever(this);
            mLoadMoreState.setValue(true);
        }

        @Override
        public void onChanged(@Nullable Boolean aBoolean) {
            mLoadMoreState.setValue(false);
        }

        private void unregister() {
            if (mIsLoadingZhihuList != null) {
                mIsLoadingZhihuList.removeObserver(this);
                mIsLoadingZhihuList = null;
            }
        }

        private MutableLiveData<Boolean> getLoadMoreState() {
            return mLoadMoreState;
        }
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
            return (T) new ZhihuListViewModel(mApplication, mGirlsDataRepository);
        }
    }
}
