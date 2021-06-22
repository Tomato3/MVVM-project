package com.example.southplatform.network;


import com.example.southplatform.BuildConfig;
import com.example.southplatform.utils.Constants;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHttpUtils {
    private static Retrofit retrofit;
    private static OkHttpClient okHttpClient;
    private static ApiServer apiServer;
    private static String BaseUrl = Constants.BASE_URL;
    private int isUseCatch;


    void init() {
        initOkHttpClient();
        initRetrofit();
        initApiServer();
    }

    private void initRetrofit() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //配合Rxjava
                .addConverterFactory(GsonConverterFactory.create()) //Gson 转换器
                .client(okHttpClient);
        retrofit = builder.build();
    }

    private void initOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(5, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {//添加日志拦截器
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//打印内容选择
            builder.addInterceptor(loggingInterceptor);
        }
        builder.retryOnConnectionFailure(true);
        okHttpClient = builder.build();
    }

    //获取接口实例
    private void initApiServer() {
        apiServer = retrofit.create(ApiServer.class);
    }

    protected static ApiServer getApiServer() {
        return apiServer;
    }
}
