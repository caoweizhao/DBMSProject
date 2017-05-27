package com.example.administrator.dbmsproject.model.helper;

import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Administrator on 2017-3-26.
 */

public class OkHttpHelper {

    private static class OkHttpHelperHolder {
        private static final OkHttpHelper instance = new OkHttpHelper();
    }

    public static OkHttpHelper getInstance() {
        return OkHttpHelperHolder.instance;
    }

    private OkHttpClient mOkHttpClient = new OkHttpClient.Builder().connectTimeout(8, TimeUnit.SECONDS).build();

    public void execute(Request request, Callback callback) {
        mOkHttpClient.newCall(request).enqueue(callback);
    }
}
