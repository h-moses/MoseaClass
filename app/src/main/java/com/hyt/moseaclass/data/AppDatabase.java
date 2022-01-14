package com.hyt.moseaclass.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.hyt.moseaclass.data.dao.UserInfoDao;
import com.hyt.moseaclass.data.entity.UserInfo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 传入对应的实体
@Database(entities = {UserInfo.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    //    线程数
    private static final int NUMBER_OF_THREAD = 2;
    //    线程池
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREAD);
    //    实例
    private static volatile AppDatabase INSTANCE;

    //    单例模式
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
