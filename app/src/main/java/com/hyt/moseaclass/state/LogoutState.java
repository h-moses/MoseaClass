package com.hyt.moseaclass.state;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.hyt.moseaclass.ui.login.LoginActivity;

/*
 * 未登录状态
 * */
public class LogoutState implements UserState {

    @Override
    public void login(Context context) {
        goLoginActivity(context);
    }

    @Override
    public void joinCourse(Context context, int uid, int cid) {
//        消息提示未登录
        Toast.makeText(context, "用户未登录，请先完成登录", Toast.LENGTH_SHORT).show();
    }

    /*
     * 跳转至登录页面
     * */
    private void goLoginActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
}
