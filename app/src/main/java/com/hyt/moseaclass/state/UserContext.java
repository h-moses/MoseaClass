package com.hyt.moseaclass.state;

import android.content.Context;

import com.hyt.moseaclass.utils.SharedPreferenceUtils;

public class UserContext {

    public static final String KEY_LOGIN = "IsLogin";
    public static final String KEY_JOIN = "IsJoin";
    public static final String KEY_UID = "UID";
    public static final String KEY_CID = "CID";
    private static final UserContext instance = new UserContext();
    private UserState mState = new LogoutState();

    private Boolean isLogin = false;

    private UserContext() {
    }

    public static UserContext getInstance() {
        return instance;
    }

    public UserState getmState() {
        return mState;
    }

    public Boolean getIsLogin(Context context) {
        return SharedPreferenceUtils.getBoolean(context, SharedPreferenceUtils.LOGIN_STATE, UserContext.KEY_LOGIN, false);
    }

    public void setLoginState(Context context, int uid) {
        SharedPreferenceUtils.setBoolean(context, SharedPreferenceUtils.LOGIN_STATE, KEY_LOGIN, true);
        SharedPreferenceUtils.setInteger(context, SharedPreferenceUtils.LOGIN_STATE, KEY_UID, uid);
        mState = new LoginState();
    }

    public void setLogoutState(Context context) {
        SharedPreferenceUtils.setBoolean(context, SharedPreferenceUtils.LOGIN_STATE, KEY_LOGIN, false);
        SharedPreferenceUtils.clear(context, SharedPreferenceUtils.LOGIN_STATE);
        mState = new LogoutState();
    }
}
