package com.hyt.moseaclass.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.hyt.moseaclass.data.AppDatabase;
import com.hyt.moseaclass.data.dao.UserInfoDao;
import com.hyt.moseaclass.data.entity.UserInfo;

public class UserInfoRepository {

    private final UserInfoDao userInfoDao;
    private LiveData<UserInfo> userInfoLiveData;
    private LiveData<Integer> loginState;


    public UserInfoRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        this.userInfoDao = database.userInfoDao();
    }

    public void insertUser(UserInfo userInfo) {
        AppDatabase.databaseWriteExecutor.execute(() -> this.userInfoDao.insert(userInfo));
    }

    public LiveData<Integer> queryLoginState(Integer uid) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
             loginState = this.userInfoDao.queryLogin(uid);
        });
        return loginState;
    }

    public LiveData<UserInfo> queryUserInfo(Integer uid) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            userInfoLiveData = this.userInfoDao.query(uid);
        });
        return userInfoLiveData;
    }
}
