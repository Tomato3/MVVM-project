package com.example.southplatform.network;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class RxHelper {
    private static String TAG = "RXHELPER";
    private static int maxretry = 5;
    private static int current = 0;

    public static <T> ObservableTransformer<T, T> to_mian1() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(throwableObservable -> {
                    return throwableObservable.flatMap(throwable -> {
                        Log.i(TAG, "apply: 重试次数");
                        return Observable.just(1).delay(2, TimeUnit.SECONDS);
                    });
                });
    }

    public static <T> ObservableTransformer<T, T> retry() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(3, new Predicate<Throwable>() {
                    @Override
                    public boolean test(Throwable throwable) throws Exception {
                        return false;
                    }
                });
    }

    public static <T> ObservableTransformer<T, T> to_mian() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
