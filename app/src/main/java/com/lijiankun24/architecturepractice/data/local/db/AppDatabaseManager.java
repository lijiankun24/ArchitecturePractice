package com.lijiankun24.architecturepractice.data.local.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;

/**
 * AppDatabaseManager.java
 * <p>
 * Created by lijiankun on 17/7/5.
 */

public class AppDatabaseManager {

    private static final String DATABASE_NAME = "architecture-practice-db";

    private static AppDatabaseManager INSTANCE = null;

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    private AppDatabase mDB = null;

    private AppDatabaseManager() {
    }

    public static AppDatabaseManager getInstance() {
        if (INSTANCE == null) {
            synchronized (AppDatabaseManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppDatabaseManager();
                }
            }
        }
        return INSTANCE;
    }

    public LiveData<Boolean> isDatabaseCreated() {
        return mIsDatabaseCreated;
    }

    @Nullable
    public AppDatabase getDB() {
        return mDB;
    }

    public void createDB(Context context) {
        mIsDatabaseCreated.setValue(false);
        new AsyncTask<Context, Void, Void>() {
            @Override
            protected Void doInBackground(Context... params) {
                Context context = params[0].getApplicationContext();
                context.deleteDatabase(DATABASE_NAME);
                AppDatabase db = Room.databaseBuilder(context,
                        AppDatabase.class, DATABASE_NAME).build();

                DatabseUtil.initializeDb(db);

                mDB = db;
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                mIsDatabaseCreated.setValue(true);
            }
        }.execute(context.getApplicationContext());

    }
}
