package com.hyt.moseaclass.state;

import android.content.Context;

import com.hyt.moseaclass.utils.OkHttpUtils;

import org.json.JSONObject;

import okhttp3.FormBody;

public class LoginState implements UserState {

    @Override
    public void login(Context context) {}

    @Override
    public void joinCourse(Context context, int uid, int cid) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("uid", String.valueOf(uid));
        builder.add("cid", String.valueOf(cid));
        OkHttpUtils.postObj("http://101.133.173.40:8090/edusys/course/joinCourse", builder.build());
    }

    @Override
    public void quitCourse(Context context, int uid, int cid) {

    }
}
