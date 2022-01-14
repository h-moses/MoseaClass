package com.hyt.moseaclass.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.hyt.moseaclass.data.AppDatabase;
import com.hyt.moseaclass.data.dao.UserInfoDao;
import com.hyt.moseaclass.data.entity.UserInfo;

import java.util.concurrent.CountDownLatch;

public class UserInfoRepository {

    //    Dao层实例
    private final UserInfoDao userInfoDao;
    private LiveData<UserInfo> userInfoLiveData;
    private LiveData<Integer> loginState;


    public UserInfoRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        this.userInfoDao = database.userInfoDao();
    }

    /*
     * 增加用户数据
     * */
    public void insertUser(UserInfo userInfo) {
        AppDatabase.databaseWriteExecutor.execute(() -> this.userInfoDao.insert(userInfo));
    }

    /*
     * 同步查询
     * */
    public LiveData<UserInfo> queryUserInfo(Integer uid) {
        CountDownLatch latch = new CountDownLatch(1);
        LiveData<UserInfo> query = userInfoDao.query(uid);
        latch.countDown();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return query;
    }
}
