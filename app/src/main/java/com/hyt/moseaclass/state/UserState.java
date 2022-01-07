package com.hyt.moseaclass.state;

import android.content.Context;

public interface UserState {

    void login(Context context);

    void joinCourse(Context context, int uid, int cid);

    void quitCourse(Context context, int uid, int cid);
}
