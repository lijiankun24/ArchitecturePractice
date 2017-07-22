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
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lijiankun24.architecturepractice.data.GirlsDataRepository;
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

    private final LoadHandler mLoadHandler;

    private GirlsDataRepository mGirlsDataRepository = null;

    private Context mContext = null;

    private GirlListViewModel(Application application, GirlsDataRepository girlsDataRepository) {
        super(application);
        mContext = application.getApplicationContext();
        mGirlsDataRepository = girlsDataRepository;
        mLoadHandler = new LoadHandler(girlsDataRepository);
        mGirls = Transformations.switchMap(mGirlPageIndex, new Function<Integer, LiveData<List<Girl>>>() {
            @Override
            public LiveData<List<Girl>> apply(Integer input) {
                if (Util.isNetworkConnected(mContext)) {
                    return mGirlsDataRepository.getGirlsFromRemote(input);
                } else {
                    return mGirlsDataRepository.getGirlsFromLocal(input);
                }
            }
        });
    }

    public LiveData<List<Girl>> getGilrsLiveData() {
        return mGirls;
    }

    public void refreshGrilsData() {
        mGirlPageIndex.setValue(1);
        mLoadHandler.mLoadMoreState.setValue(new LoadMoreState(true));
        mLoadHandler.startLoadGirls();
    }

    public void loadNextPageGirls() {
        mGirlPageIndex.setValue((mGirlPageIndex.getValue() == null ? 1 : mGirlPageIndex.getValue() + 1));
        mLoadHandler.mLoadMoreState.setValue(new LoadMoreState(true));
        mLoadHandler.startLoadGirls();
    }

    public MutableLiveData<LoadMoreState> getLoadMoreState() {
        return mLoadHandler.getLoadMoreState();
    }

    private static class LoadHandler implements Observer<Boolean> {

        private final MutableLiveData<LoadMoreState> mLoadMoreState = new MutableLiveData<>();

        private LiveData<Boolean> mLoadStatue = null;

        private GirlsDataRepository mGirlsDataRepository = null;

        private LoadHandler(GirlsDataRepository girlsDataRepository) {
            mGirlsDataRepository = girlsDataRepository;
        }

        private void startLoadGirls() {
            unregister();
            mLoadStatue = mGirlsDataRepository.isLoadingGirlListData();
            mLoadMoreState.setValue(new LoadMoreState(true));
            mLoadStatue.observeForever(this);
        }

        @Override
        public void onChanged(@Nullable Boolean aBoolean) {
            mLoadMoreState.setValue(new LoadMoreState(false));
        }

        private void unregister() {
            if (mLoadStatue != null) {
                mLoadStatue.removeObserver(this);
                mLoadStatue = null;
            }
        }

        private MutableLiveData<LoadMoreState> getLoadMoreState() {
            return mLoadMoreState;
        }
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

    public static class LoadMoreState {

        private final boolean running;

        LoadMoreState(boolean running) {
            this.running = running;
        }

        public boolean isRunning() {
            return running;
        }
    }
}
