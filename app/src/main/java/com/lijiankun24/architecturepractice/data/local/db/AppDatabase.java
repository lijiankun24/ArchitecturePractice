package com.lijiankun24.architecturepractice.data.local.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.lijiankun24.architecturepractice.data.local.db.dao.GirlDao;
import com.lijiankun24.architecturepractice.data.local.db.entity.Girl;

/**
 * AppDatabase.java
 * <p>
 * Created by lijiankun on 17/7/5.
 */

@Database(entities = {Girl.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract GirlDao girlDao();
}
