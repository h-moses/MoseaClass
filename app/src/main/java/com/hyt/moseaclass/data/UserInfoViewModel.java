package com.hyt.moseaclass.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.hyt.moseaclass.data.entity.UserInfo;
import com.hyt.moseaclass.data.repository.UserInfoRepository;

public class UserInfoViewModel extends AndroidViewModel {

    private static final String TAG = UserInfoViewModel.class.getSimpleName();
    private final UserInfoRepository userInfoRepository;
    private LiveData<UserInfo> userInfo;
    private LiveData<Integer> loginState;

    public UserInfoViewModel(@NonNull Application application) {
        super(application);
        this.userInfoRepository = new UserInfoRepository(application);
    }

    public LiveData<UserInfo> getUserInfoLiveData(Integer uid) {
        this.userInfo = userInfoRepository.queryUserInfo(uid);
        return userInfo;
    }

    public LiveData<Integer> getLoginState(Integer uid) {
        this.loginState = userInfoRepository.queryLoginState(uid);
        return loginState;
    }

    public void insertUserInfo(UserInfo userInfo) {
        userInfoRepository.insertUser(userInfo);
    }

    public UserInfoRepository getUserInfoRepository() {
        return userInfoRepository;
    }
}
