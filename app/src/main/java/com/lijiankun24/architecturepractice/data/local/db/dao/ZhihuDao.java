package com.lijiankun24.architecturepractice.data.local.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.lijiankun24.architecturepractice.data.local.db.entity.ZhihuStory;

import java.util.List;

/**
 * GirlDao.java
 * <p>
 * Created by lijiankun on 17/7/5.
 */

@Dao
public interface ZhihuDao {

    @Query("SELECT * FROM zhihustorys")
    List<ZhihuStory> loadAllZhihus();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertZhihuList(List<ZhihuStory> girls);
}
