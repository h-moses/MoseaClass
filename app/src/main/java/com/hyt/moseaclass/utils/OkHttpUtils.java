package com.hyt.moseaclass.utils;

import android.text.TextUtils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpUtils {

    public static final MediaType FORM_DATA = MediaType.get("application/x-www-form-urlencoded; charset=utf-8");
    private static final String TAG = OkHttpUtils.class.getSimpleName();
    public static JSONArray result;
    private static OkHttpClient INSTANCE;

    private OkHttpUtils() {
    }

    public static OkHttpClient new_instance() {
        if (INSTANCE == null) {
            synchronized (OkHttpClient.class) {
                if (INSTANCE == null) {
                    INSTANCE = new OkHttpClient();
                }
                return INSTANCE;
            }
        }
        return INSTANCE;
    }

    public static JSONArray post(String url, FormBody json) {
//        异步请求转阻塞式同步请求
        final CountDownLatch latch = new CountDownLatch(1);
        Request request = new Request.Builder().url(url).post(json).build();
        OkHttpUtils.new_instance().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                latch.countDown();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String string = Objects.requireNonNull(response.body()).string();
                    if (!TextUtils.isEmpty(string)) {
                        try {
                            JSONObject jsonObject = new JSONObject(string);
                            result = jsonObject.getJSONArray("data");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                latch.countDown();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
}
