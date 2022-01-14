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
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtils {

    //    定义json格式
    public static final MediaType JSON = MediaType.parse("application/json");
    //    返回格式类型为数组
    public static JSONArray result;
    //    返回格式类型为对象
    public static JSONObject resultObj;
    //    okHttp实例
    private static OkHttpClient INSTANCE;

    private OkHttpUtils() {
    }

    /*
     * 单例模式
     * */
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

    /*
     * 发送json数据，返回对象格式数据
     * */
    public static JSONObject postjson(String url, String json) {
//        异步请求转阻塞式同步请求
        final CountDownLatch latch = new CountDownLatch(1);
//        创建请求body
        RequestBody body = RequestBody.create(JSON, json);
//        创建请求
        Request request = new Request.Builder().url(url).post(body).build();
//        异步执行
        OkHttpUtils.new_instance().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                latch.countDown();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) { // 成功响应
//                    获取响应
                    String string = Objects.requireNonNull(response.body()).string();
                    if (!TextUtils.isEmpty(string)) {
                        try {
//                            获取数据
                            JSONObject jsonObject = new JSONObject(string);
                            resultObj = jsonObject.getJSONObject("data");
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
        return resultObj;
    }

    /*
     * 返回格式为数组
     * */
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

    public static JSONArray get(String url) {
//        异步请求转阻塞式同步请求
        final CountDownLatch latch = new CountDownLatch(1);
        Request request = new Request.Builder().url(url).get().build();
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

    public static JSONObject postObj(String url, FormBody json) {
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
                            resultObj = jsonObject.getJSONObject("data");
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
        return resultObj;
    }
}
