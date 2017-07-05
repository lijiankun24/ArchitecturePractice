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

import com.lijiankun24.architecturepractice.db.AppDatabaseManager;
import com.lijiankun24.architecturepractice.db.entity.Girl;

/**
 * GirlViewModel.java
 * <p>
 * Created by lijiankun on 17/7/5.
 */

public class GirlViewModel extends AndroidViewModel {

    private static final MutableLiveData ABSENT = new MutableLiveData();

    {
        ABSENT.setValue(null);
    }

    private final LiveData<Girl> mGirlLiveData;

    public Girl mGirl;

    private final String mGirlId;


    public GirlViewModel(Application application, String girlId) {
        super(application);
        this.mGirlId = girlId;

        final AppDatabaseManager manager = AppDatabaseManager.getInstance();

        mGirlLiveData = Transformations.switchMap(manager.isDatabaseCreated(), new Function<Boolean, LiveData<Girl>>() {
            @Override
            public LiveData<Girl> apply(Boolean input) {
                if (!Boolean.TRUE.equals(input)) {
                    return ABSENT;
                } else {
                    return manager.getDB().girlDao().loadGirl(mGirlId);
                }
            }
        });

        manager.createDB(application);
    }

    public LiveData<Girl> getGirlLiveData() {
        return mGirlLiveData;
    }

    public void setGirl(Girl girl) {
        mGirl = girl;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final String mGirlId;

        public Factory(@NonNull Application application, String girlId) {
            mApplication = application;
            mGirlId = girlId;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new GirlViewModel(mApplication, mGirlId);
        }
    }
}
