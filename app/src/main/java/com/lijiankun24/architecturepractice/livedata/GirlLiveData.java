package com.lijiankun24.architecturepractice.livedata;

import android.arch.lifecycle.LiveData;

import com.lijiankun24.architecturepractice.utils.L;

/**
 * GirlLiveData.java
 * <p>
 * Created by lijiankun on 17/7/16.
 */

public class GirlLiveData<String> extends LiveData<String> {

    private String mString = null;

    @Override
    protected void onActive() {
        super.onActive();
        L.i("===== onActive ");
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        L.i("===== onInactive ");
    }

    @Override
    public void setValue(String value) {
        super.setValue(value);
        mString = value;
    }

    @Override
    protected void postValue(String value) {
        super.postValue(value);
        mString = value;
    }

    public String getString() {
        return mString;
    }
}
