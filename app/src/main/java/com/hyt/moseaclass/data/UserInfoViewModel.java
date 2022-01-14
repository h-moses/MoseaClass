package com.hyt.moseaclass.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.hyt.moseaclass.data.entity.UserInfo;
import com.hyt.moseaclass.data.repository.UserInfoRepository;

/*
 * 用户信息视图模型
 * */
public class UserInfoViewModel extends AndroidViewModel {

    private final UserInfoRepository userInfoRepository;

    public UserInfoViewModel(@NonNull Application application) {
        super(application);
        this.userInfoRepository = new UserInfoRepository(application);
    }

    /*
     * 查询用户信息
     * */
    public LiveData<UserInfo> getUserInfoLiveData(Integer uid) {
        LiveData<UserInfo> userInfo = userInfoRepository.queryUserInfo(uid);
        return userInfo;
    }

    public UserInfoRepository getUserInfoRepository() {
        return userInfoRepository;
    }
}
