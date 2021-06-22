package com.example.southplatform.network;

/**
 * desc:
 * date:2021/6/21 14:17
 * author:bWang
 */
public class ApiWrapper extends RetrofitHttpUtils{
    private static volatile ApiWrapper apiWrapper;

    public ApiWrapper() {
        init();
    }

    public static ApiWrapper getInstance() {
        if (apiWrapper == null) {
            synchronized (ApiWrapper.class) {
                if (apiWrapper == null) {
                    apiWrapper = new ApiWrapper();
                }
            }
        }
        return apiWrapper;
    }

}
