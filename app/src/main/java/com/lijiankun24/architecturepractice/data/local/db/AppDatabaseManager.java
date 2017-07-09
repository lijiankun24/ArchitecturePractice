package com.lijiankun24.architecturepractice.data.local.db;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.lijiankun24.architecturepractice.data.GirlsDataSource;
import com.lijiankun24.architecturepractice.data.local.db.entity.Girl;

import java.util.List;

/**
 * AppDatabaseManager.java
 * <p>
 * Created by lijiankun on 17/7/5.
 */

public class AppDatabaseManager {

    private static final String DATABASE_NAME = "architecture-practice-db";

    private static AppDatabaseManager INSTANCE = null;

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

    public void createDB(Context context) {
        new AsyncTask<Context, Void, Void>() {
            @Override
            protected Void doInBackground(Context... params) {
                Context context = params[0].getApplicationContext();
                context.deleteDatabase(DATABASE_NAME);
                AppDatabase db = Room.databaseBuilder(context,
                        AppDatabase.class, DATABASE_NAME).build();
                mDB = db;
                return null;
            }
        }.execute(context.getApplicationContext());
    }

    public void insertGirls(final List<Girl> girls) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                mDB.beginTransaction();
                try {
                    mDB.girlDao().insertGirls(girls);
                    mDB.setTransactionSuccessful();
                } finally {
                    mDB.endTransaction();
                }
                return null;
            }
        }.execute();
    }

    public void loadGirls(@NonNull final GirlsDataSource.LoadGirlsCallback callback) {
        new AsyncTask<Void, Void, List<Girl>>() {
            @Override
            protected List<Girl> doInBackground(Void... voids) {
                List<Girl> mAllGirls = null;
                mDB.beginTransaction();
                try {
                    mAllGirls =  mDB.girlDao().loadAllGirls();
                    mDB.setTransactionSuccessful();
                } finally {
                    mDB.endTransaction();
                }
                return mAllGirls;
            }

            @Override
            protected void onPostExecute(List<Girl> aVoid) {
                if(aVoid == null || aVoid.size() == 0){
                    callback.onGirlsNotAvailable();
                } else {
                    callback.onGirlsLoaded(aVoid);
                }
                return;
            }
        }.execute();
    }
}
