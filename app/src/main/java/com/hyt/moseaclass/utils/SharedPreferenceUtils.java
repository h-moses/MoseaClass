package com.hyt.moseaclass.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtils {

    public static final String COURSE_FILE = "course";

    public static final String LOGIN_STATE = "login_state";

    //    根据关键字获取文件内的布尔值，存在默认值
    public static boolean getBoolean(Context context, String fileName, String key, Boolean defValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defValue);
    }

    //    在SharedPreferences中添加布尔键值对
    public static void setBoolean(Context context, String fileName, String key, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    //    在SharedPreferences中添加字符串键值对
    public static void setString(Context context, String fileName, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    //    根据关键字获取文件内的字符串，存在默认值
    public static String getString(Context context, String fileName, String key, String defValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defValue);
    }

    //    在SharedPreferences中添加数值键值对
    public static void setInteger(Context context, String fileName, String key, int value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    //    根据关键字获取文件内的数值，存在默认值
    public static int getInteger(Context context, String fileName, String key, Integer defValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, defValue);
    }

    //    清空指定文件
    public static void clear(Context context, String fileName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
