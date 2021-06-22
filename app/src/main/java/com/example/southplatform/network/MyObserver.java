package com.example.southplatform.network;


import com.example.southplatform.R;
import com.example.southplatform.base.BaseApplication;
import com.example.southplatform.network.exception.ExceptionHandle;

import io.reactivex.Observer;


public abstract class MyObserver<T> implements Observer<T> {

    public static final String CONNECT_EXCEPTION =
            BaseApplication.getInstance().getString(R.string.exp_network_exception);
    public static final String SOCKET_TIMEOUT_EXCEPTION = BaseApplication.getInstance().getString(R.string.exp_network_timeout);
    public static final String MALFORMED_JSON_EXCEPTION = BaseApplication.getInstance().getString(R.string.exp_json_error);
    public static final String SERVER_TIMEOUT_EXCEPTION = BaseApplication.getInstance().getString(R.string.exp_server_timeout);

    @Override
    public void onError(Throwable e) {
//        String messageStr = "";//提示信息
//        if (e instanceof CompositeException) {
//
//            CompositeException compositeE = (CompositeException) e;
//            for (Throwable throwable : compositeE.getExceptions()) {
//                //超时 异常
//                if (throwable instanceof SocketTimeoutException) {
//                    //设置异常信息
//                    messageStr = SOCKET_TIMEOUT_EXCEPTION;
//                }
//                //连接异常
//                else if (throwable instanceof ConnectException) {
//                    //设置异常信息
//                    messageStr = CONNECT_EXCEPTION;
//                }
//                //连接异常 位置的host
//                else if (throwable instanceof UnknownHostException) {
//                    //设置异常信息
//                    messageStr = CONNECT_EXCEPTION;
//                }
//                //json  解析异常
//                else if (throwable instanceof MalformedJsonException) {
//                    //设置异常信息
//                    messageStr = MALFORMED_JSON_EXCEPTION;
//                }
//            }
//        } else if (e instanceof HttpException) {//服务器 错误 连接超时
//            ResponseBody responseBody = ((HttpException) e).response().errorBody();
//            try {
//                assert responseBody != null;
//                GeneralResponse generalResponse = new Gson().fromJson(responseBody.string(),
//                        GeneralResponse.class);
//                if (null == generalResponse) {//为空则返回
//                    return;
//                }
//                if (!TextUtils.isEmpty(generalResponse.getMessage())) {
//                    messageStr = generalResponse.getMessage();
//                }
//                if (!TextUtils.isEmpty(generalResponse.getData())) {
//                    messageStr = generalResponse.getData();
//                }
//                Log.e(HTAG, "打印输出错误代码httpResultBean---->" + generalResponse.getMessage());
//            } catch (Exception e1) {
//                messageStr = SERVER_TIMEOUT_EXCEPTION;
//            }
//
//        } else if (e instanceof UnknownHostException) {// 测试到时再没网的情况下
//            messageStr = CONNECT_EXCEPTION;
//        } else if (e instanceof ExceptionHandle.ResponseException) {
//            /**
//             * 弹出提示 所有的后台返回的提示
//             */
//            messageStr = e.getMessage();
//        } else if (e instanceof SocketTimeoutException) {
//            messageStr = SOCKET_TIMEOUT_EXCEPTION;
//        } else if (e instanceof ConnectException) {
//            messageStr = SOCKET_TIMEOUT_EXCEPTION;
//        }
//
//
//        ToastUtil.showFailedTipToast(BaseApplication.getInstance(),
//                messageStr);
//        Log.e(HTAG, "onError: " + messageStr);
        if (e instanceof Exception) {
            onError(ExceptionHandle.handleException(e));
        } else {
            onError(new ExceptionHandle.ResponseException(e, ExceptionHandle.ERROR.UNKNOWN));
        }
    }

    public abstract void onError(ExceptionHandle.ResponseException responseException);
}
