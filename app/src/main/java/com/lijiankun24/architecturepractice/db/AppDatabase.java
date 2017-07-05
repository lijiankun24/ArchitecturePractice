package com.lijiankun24.architecturepractice.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.lijiankun24.architecturepractice.db.dao.GirlDao;
import com.lijiankun24.architecturepractice.db.entity.Girl;

/**
 * AppDatabase.java
 * <p>
 * Created by lijiankun on 17/7/5.
 */

@Database(entities = {Girl.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    static final String DATABASE_NAME = "architecture-practice-db";

    public abstract GirlDao girlDao();
}
