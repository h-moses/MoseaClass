package com.hyt.moseaclass.state;

import android.content.Context;

import com.hyt.moseaclass.utils.OkHttpUtils;

import okhttp3.FormBody;

/*
 * 已登录状态
 * */
public class LoginState implements UserState {

    @Override
    public void login(Context context) {
        // TODO，无需实现任何操作
    }

    /*
     * 指定用户加入指定课程
     * */
    @Override
    public void joinCourse(Context context, int uid, int cid) {
//        将用户id和课程id携带
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("uid", String.valueOf(uid));
        builder.add("cid", String.valueOf(cid));
//        发送请求
        OkHttpUtils.postObj("http://101.133.173.40:8090/edusys/course/joinCourse", builder.build());
    }

}
