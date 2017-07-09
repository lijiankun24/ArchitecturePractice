package com.lijiankun24.architecturepractice.data.remote.model;

import com.lijiankun24.architecturepractice.data.local.db.entity.Girl;

import java.util.List;

/**
 * GirlData.java
 * <p>
 * Created by lijiankun on 17/7/9.
 */

public class GirlData {

    public final boolean error;

    public final List<Girl> results;

    public GirlData(boolean error, List<Girl> results) {
        this.error = error;
        this.results = results;
    }
}
