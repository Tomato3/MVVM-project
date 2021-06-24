package com.example.southplatform.ui.activity.main;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.example.southplatform.BR;
import com.example.southplatform.R;
import com.example.southplatform.base.BaseActivity;
import com.example.southplatform.databinding.ActivityMainBinding;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import org.jetbrains.annotations.NotNull;


public class MainActivity extends BaseActivity<ActivityMainBinding,MainViewModel> {
    private Context mContext;

    @Override
    protected void setTitleBar() {
        titleBar.setTitleMainText("南智云平台").setTitleMainTextColor(R.color.white)
                .setLeftTextDrawable(0)
                .setBackgroundColor(getResources().getColor(R.color.teal_200));
        titleBar.setStatusAlpha(0);
    }

    @Override
    protected void initData() {
        mContext = MainActivity.this;
        Glide.with(mContext).load("https://scpic.chinaz.net/files/pic/pic9/202106/apic33387.jpg").into(dataBinding.image);
    }

    @Override
    protected void registerUIChangeEventObserver() {
        super.registerUIChangeEventObserver();
        dataBinding.smartLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull @NotNull RefreshLayout refreshLayout) {
                Glide.with(mContext).load("https://scpic.chinaz.net/files/pic/pic9/202106/apic33387.jpg").into(dataBinding.image);
                dataBinding.smartLayout.finishLoadMore(200);
            }

            @Override
            public void onRefresh(@NonNull @NotNull RefreshLayout refreshLayout) {
                Glide.with(mContext).load("https://scpic.chinaz.net/files/pic/pic9/202106/apic33323.jpg").into(dataBinding.image);
                dataBinding.smartLayout.finishRefresh(200);
            }
        });
    }

    @Override
    protected int initVariableId() {
        return BR.ViewModel;
    }

    @Override
    protected int setContentViewSrc(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}