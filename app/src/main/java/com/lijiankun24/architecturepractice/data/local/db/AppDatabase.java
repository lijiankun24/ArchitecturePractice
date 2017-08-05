package com.lijiankun24.architecturepractice.data.local.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.lijiankun24.architecturepractice.data.local.db.dao.GirlDao;
import com.lijiankun24.architecturepractice.data.local.db.dao.ZhihuDao;
import com.lijiankun24.architecturepractice.data.local.db.entity.Girl;
import com.lijiankun24.architecturepractice.data.local.db.entity.ZhihuStory;

/**
 * AppDatabase.java
 * <p>
 * Created by lijiankun on 17/7/5.
 */

@Database(entities = {Girl.class, ZhihuStory.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract GirlDao girlDao();

    public abstract ZhihuDao zhihuDao();
}
