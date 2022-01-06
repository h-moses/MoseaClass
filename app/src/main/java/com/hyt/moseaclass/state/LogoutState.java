package com.hyt.moseaclass.state;

import android.content.Context;
import android.content.Intent;

import com.hyt.moseaclass.ui.login.LoginActivity;

public class LogoutState implements UserState {

    @Override
    public void login(Context context) {
        goLoginActivity(context);
    }

    private void goLoginActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
}
