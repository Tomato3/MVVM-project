package com.example.southplatform.base;

import android.os.Bundle;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aries.ui.view.title.TitleBarView;

import java.util.HashMap;
import java.util.Map;

/**
 * desc:
 * date:2021/6/21 10:23
 * author:bWang
 */
public class BaseViewModel extends ViewModel implements IViewModel {
    private UIChangeEvent mUIChangeEvent;

    public BaseViewModel() {
        this.mUIChangeEvent = new UIChangeEvent();
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Map<String, Object> params = new HashMap<>();
        params.put(ParameterField.CLASS, clz);
        if (bundle != null) {
            params.put(ParameterField.BUNDLE, bundle);
        }
        mUIChangeEvent.startActivityEvent.postValue(params);
    }

    /**
     * 关闭界面
     */
    public void finish() {
        mUIChangeEvent.getFinishEvent();
    }

    public UIChangeEvent getUiChangeEvent() {
        return mUIChangeEvent;
    }

    public class UIChangeEvent {
        private MutableLiveData<String> toastTxt;
        private MutableLiveData<Integer> toastType;
        private MutableLiveData<String> dialogShowEvent;
        private MutableLiveData<Void> dialogDismissEvent;
        private MutableLiveData<Map<String, Object>> startActivityEvent;
        private MutableLiveData<Void> finishEvent;


        public MutableLiveData<String> getToastTxt() {
            return toastTxt = createLiveData(toastTxt);
        }

        public void setToastTxt(MutableLiveData<String> toastTxt) {
            this.toastTxt = toastTxt;
        }

        public MutableLiveData<Map<String, Object>> getStartContainerActivityEvent() {
            return startContainerActivityEvent = createLiveData(startActivityEvent);
        }

        private MutableLiveData<Map<String, Object>> startContainerActivityEvent;

        public MutableLiveData<String> getDialogShowEvent() {
            return dialogShowEvent = createLiveData(dialogShowEvent);
        }

        public MutableLiveData<Void> getDialogDismissEvent() {
            return dialogDismissEvent = createLiveData(dialogDismissEvent);
        }

        public MutableLiveData<Map<String, Object>> getStartActivityEvent() {
            return startActivityEvent = createLiveData(startActivityEvent);
        }

        public MutableLiveData<Void> getFinishEvent() {
            return finishEvent = createLiveData(finishEvent);
        }

        public MutableLiveData<Integer> getToastType() {
            return toastType = createLiveData(toastType);
        }
    }
    public MutableLiveData createLiveData(MutableLiveData liveData) {
        if (liveData == null) {
            liveData = new MutableLiveData<>();
        }
        return liveData;
    }

    public static final class ParameterField {
        public static String CLASS = "CLASS";
        public static String CANONICAL_NAME = "CANONICAL_NAME";
        public static String BUNDLE = "BUNDLE";
    }

    public void showToast(String toastTxt, int toastType) {
        getUiChangeEvent().getToastTxt().setValue(toastTxt);
        getUiChangeEvent().getToastType().setValue(toastType);
    }

}
