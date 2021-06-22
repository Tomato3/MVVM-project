package com.example.southplatform.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

public abstract class BaseFragment<DB extends ViewDataBinding, VM extends BaseViewModel> extends Fragment implements IView {
    protected DB dataBinding;
    protected VM viewModel;
    protected Activity activity;
    protected Context context;
    private int viewModelId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = this.getContext();
        dataBinding = DataBindingUtil.inflate(inflater, initContentView(inflater, container, savedInstanceState), container, false);
        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = getActivity();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDataBindingAndViewModel(savedInstanceState);
        //私有的ViewModel与View的契约事件回调逻辑
        registerUIChangeLiveDataCallBack();
        initViewModel();
        initView();
        initData();
        initAdapter();
        initListener();
        initObserver();
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(getActivity(), clz));
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

//    /**
//     * 跳转容器页面
//     *
//     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
//     */
//    public void startContainerActivity(String canonicalName) {
//        startContainerActivity(canonicalName, null);
//    }
//
//    /**
//     * 跳转容器页面
//     *
//     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
//     * @param bundle        跳转所携带的信息
//     */
//    public void startContainerActivity(String canonicalName, Bundle bundle) {
//        Intent intent = new Intent(getContext(), ContainerActivity.class);
//        intent.putExtra(ContainerActivity.FRAGMENT, canonicalName);
//        if (bundle != null) {
//            intent.putExtra(ContainerActivity.BUNDLE, bundle);
//        }
//        startActivity(intent);
//    }

    /**
     * =====================================================================
     **/
    //注册ViewModel与View的契约UI回调事件
    protected void registerUIChangeLiveDataCallBack() {
//跳入新页面
        viewModel.getUiChangeEvent().getStartActivityEvent().observe(getActivity(), new Observer<Map<String,
                Object>>() {
            @Override
            public void onChanged(@Nullable Map<String, Object> params) {
                Class<?> clz = (Class<?>) params.get(ParameterField.CLASS);
                Bundle bundle = (Bundle) params.get(ParameterField.BUNDLE);
                startActivity(clz, bundle);
            }
        });

        viewModel.getUiChangeEvent().getToastTxt().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                //展示Toast
                Toast toast = Toast.makeText(getActivity(), s,
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
//        //跳入ContainerActivity
//        viewModel.getUiChangeEvent().getStartContainerActivityEvent().observe(this, new Observer<Map<String,
//                Object>>() {
//            @Override
//            public void onChanged(@Nullable Map<String, Object> params) {
//                String canonicalName = (String) params.get(ParameterField.CANONICAL_NAME);
//                Bundle bundle = (Bundle) params.get(ParameterField.BUNDLE);
//                startContainerActivity(canonicalName, bundle);
//            }
//        });
    }

    public boolean isBackPressed() {
        return false;
    }

    protected abstract void initData();

    protected abstract void initListener();

    protected abstract void initAdapter();

    protected abstract void initView();

    protected abstract void initViewModel();

    protected abstract void initObserver();

    public abstract int initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    @Override
    public void initDataBindingAndViewModel(Bundle savedInstanceState) {
        viewModelId = initVIewModelID();
        viewModel = initViewmodel();
        if (viewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseViewModel.class;
            }
            viewModel = (VM) createViewModel(this, modelClass);
        }
        dataBinding.setVariable(viewModelId, viewModel);
        dataBinding.setLifecycleOwner(this);
    }

    protected <T extends ViewModel> T createViewModel(Fragment fragment, Class<T> cls) {
        ViewModel viewModel = ViewModelProviders.of(fragment).get(cls);
        return (T) ViewModelProviders.of(fragment).get(cls);
    }

    protected abstract int initVIewModelID();

    public VM initViewmodel() {
        return null;
    }

    @Override
    public void initViewParameters() {
    }
    public static final class ParameterField {
        public static String CLASS = "CLASS";
        public static String CANONICAL_NAME = "CANONICAL_NAME";
        public static String BUNDLE = "BUNDLE";
    }
}
