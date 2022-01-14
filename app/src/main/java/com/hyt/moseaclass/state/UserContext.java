package com.hyt.moseaclass.state;

import android.content.Context;

import com.hyt.moseaclass.utils.SharedPreferenceUtils;

/*
 * 用户状态管理器
 * */
public class UserContext {
    public static final String KEY_LOGIN = "IsLogin";
    public static final String KEY_JOIN = "IsJoin";
    public static final String KEY_UID = "UID";
    public static final String KEY_CID = "CID";
    //    单例模式
    private static final UserContext instance = new UserContext();
    //    默认未登录状态
    private UserState mState = new LogoutState();

    private Boolean isLogin = false;

    private UserContext() {
    }

    /*
     * 返回实例
     * */
    public static UserContext getInstance() {
        return instance;
    }

    public UserState getmState() {
        return mState;
    }

    /*
     * 返回登录状态
     * */
    public Boolean getIsLogin(Context context) {
        return SharedPreferenceUtils.getBoolean(context, SharedPreferenceUtils.LOGIN_STATE, UserContext.KEY_LOGIN, false);
    }

    /*
     * 将mState设置为已登录状态
     * */
    public void setLoginState(Context context, int uid) {
//        共享状态为登录
        SharedPreferenceUtils.setBoolean(context, SharedPreferenceUtils.LOGIN_STATE, KEY_LOGIN, true);
//        添加用户id
        SharedPreferenceUtils.setInteger(context, SharedPreferenceUtils.LOGIN_STATE, KEY_UID, uid);
        mState = new LoginState();
    }

    /*
     * 将mState设置为未登录状态
     * */
    public void setLogoutState(Context context) {
//        共享状态为未登录
        SharedPreferenceUtils.setBoolean(context, SharedPreferenceUtils.LOGIN_STATE, KEY_LOGIN, false);
//        清空用户数据
        SharedPreferenceUtils.clear(context, SharedPreferenceUtils.LOGIN_STATE);
        mState = new LogoutState();
    }
}
