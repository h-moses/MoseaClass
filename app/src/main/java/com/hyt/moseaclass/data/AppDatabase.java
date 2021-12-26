package com.hyt.moseaclass.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.hyt.moseaclass.data.dao.UserInfoDao;
import com.hyt.moseaclass.data.entity.UserInfo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
@Database(entities = {UserInfo.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final int NUMBER_OF_THREAD = 2;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREAD);
    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "mosea_class.db").build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract UserInfoDao userInfoDao();
}
