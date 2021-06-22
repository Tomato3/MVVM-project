package com.example.southplatform.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.aries.library.fast.util.ToastUtil;
import com.aries.ui.view.title.TitleBarView;
import com.example.southplatform.R;
import com.example.southplatform.utils.DialogUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * desc:
 * date:2021/6/21 10:18
 * author:bWang
 */
public abstract class BaseActivity<DB extends ViewDataBinding,VM extends BaseViewModel> extends AppCompatActivity implements IView{
    protected DB dataBinding;
    protected VM viewModel;
    protected int viewModelId;
    protected TitleBarView titleBar;
    protected int type = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //传入数据
        initViewParameters();
        //初始化DataBinding和viewModel
        initDataBindingAndViewModel(savedInstanceState);
        //监听
        initViewListener();

        //初始化数据
        initData();

        //标题栏设置
        initTitle();

        //注册观察者，观察UIChange事件，以便做出响应
        registerUIChangeEventObserver();

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //设置强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    protected void initTitle() {
        titleBar = findViewById(R.id.titleBar);
        if (titleBar == null) {
            return;
        }
        type = titleBar.getStatusBarModeType();
        //无法设置白底黑字
        if (type <= 0) {
            //5.0 半透明模式alpha-102
            titleBar.setStatusAlpha(60);
        }
        titleBar.setTitleMainText(getTitle());
        titleBar.setOnLeftTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setTitleLine(isShowLine());
        setTitleBar();
    }

    protected abstract void setTitleBar();

    protected boolean isShowLine() {
        return true;
    }

    public void setTitleLine(boolean enable) {
        titleBar.setDividerVisible(enable);
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(this, clz));
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void registerUIChangeEventObserver() {
        viewModel.getUiChangeEvent().getToastTxt().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast toast = Toast.makeText(BaseActivity.this, s,
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
        viewModel.getUiChangeEvent().getDialogShowEvent().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                DialogUtil.showDialogWait1(BaseActivity.this, s);
            }
        });

        viewModel.getUiChangeEvent().getDialogDismissEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                DialogUtil.stopProgressDlg();
            }
        });

        viewModel.getUiChangeEvent().getStartActivityEvent().observe(this, new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(Map<String, Object> stringObjectMap) {
                Class<?> clz = (Class<?>) stringObjectMap.get(BaseViewModel.ParameterField.CLASS);
                Bundle bundle = (Bundle) stringObjectMap.get(BaseViewModel.ParameterField.BUNDLE);
                startActivity(clz, bundle);
            }
        });

        viewModel.getUiChangeEvent().getToastType().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch (integer) {
                    case 0:
                        ToastUtil.showSuccess(viewModel.getUiChangeEvent().getToastTxt().getValue());
                        break;
                    case 1:
                        ToastUtil.showFailed(viewModel.getUiChangeEvent().getToastTxt().getValue());
                        break;
                    case 10000:
                        ToastUtil.showWarning(
                                viewModel.getUiChangeEvent().getToastTxt().getValue());
                        break;
                }
            }
        });


    }


    protected abstract void initData();

    public void initViewListener() {

    }

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    protected abstract int initVariableId();

    protected VM initViewModel() {
        return null;
    }

    /**
     * 初始化根布局
     *
     * @param savedInstanceState
     * @return 布局layout的Id
     */
    protected abstract @LayoutRes
    int setContentViewSrc(Bundle savedInstanceState);

    @Override
    public void initViewParameters() {
    }

    @Override
    public void initDataBindingAndViewModel(Bundle savedInstanceState) {
        //初始化DataBinding
        dataBinding = DataBindingUtil.setContentView(this, setContentViewSrc(savedInstanceState));
        viewModelId = initVariableId();
        //初始化LiveData
        viewModel = initViewModel();
        if (viewModel == null) {
            Class classType;
            Type genericSuperclass = getClass().getGenericSuperclass();
            if (genericSuperclass instanceof ParameterizedType) {
                classType = (Class) ((ParameterizedType) genericSuperclass).getActualTypeArguments()[1];
            } else {
                classType = BaseViewModel.class;
            }
            viewModel = (VM) ViewModelProviders.of(this).get(classType);
        }
        //dataBinding与viewModel关联，使dataBinding与viewModel关联，dataBinding能不通过反射
        // 访问到ViewModel中的值
        dataBinding.setVariable(viewModelId, viewModel);
        //将当前当前activity设为生命周期的拥有者
        dataBinding.setLifecycleOwner(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        titleBar = null;
    }
}
