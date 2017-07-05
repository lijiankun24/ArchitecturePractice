package com.lijiankun24.architecturepractice.viewmodel;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import com.lijiankun24.architecturepractice.db.AppDatabaseManager;
import com.lijiankun24.architecturepractice.db.entity.Girl;

import java.util.List;

/**
 * GirlListViewModel.java
 * <p>
 * Created by lijiankun on 17/7/4.
 */

public class GirlListViewModel extends AndroidViewModel {

    private static final MutableLiveData ABSENT = new MutableLiveData();

    {
        ABSENT.setValue(null);
    }

    private final LiveData<List<Girl>> mLiveData;

    public GirlListViewModel(Application application) {
        super(application);

        final AppDatabaseManager manager = AppDatabaseManager.getInstance();
        LiveData<Boolean> isDatabaseCreate = manager.isDatabaseCreated();
        mLiveData = Transformations.switchMap(isDatabaseCreate, new Function<Boolean, LiveData<List<Girl>>>() {
            @Override
            public LiveData<List<Girl>> apply(Boolean input) {
                if (!Boolean.TRUE.equals(input)) {
                    return ABSENT;
                } else {
                    return manager.getDB().girlDao().loadAllGirls();
                }
            }
        });

        manager.createDB(application);
    }

    public LiveData<List<Girl>> getLiveData() {
        return mLiveData;
    }
}
