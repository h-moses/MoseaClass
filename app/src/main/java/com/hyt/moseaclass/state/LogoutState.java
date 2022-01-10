package com.hyt.moseaclass.state;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.hyt.moseaclass.ui.login.LoginActivity;

public class LogoutState implements UserState {

    @Override
    public void login(Context context) {
        goLoginActivity(context);
    }

    @Override
    public void joinCourse(Context context, int uid, int cid) {
        Toast.makeText(context, "用户未登录，请先完成登录", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void quitCourse(Context context, int uid, int cid) {
        // TODO,未登录状态，无需调用该方法
    }

    private void goLoginActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
}
