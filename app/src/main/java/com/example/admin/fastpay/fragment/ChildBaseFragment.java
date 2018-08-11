package com.example.admin.fastpay.fragment;

import android.os.Bundle;

import com.zxz.www.base.app.BaseFragment;

/**
 * Created by 曾宪梓 on 2017/12/30.
 */

abstract public class ChildBaseFragment extends BaseFragment {

    @Override
    public boolean handleBackEvent() {
        return super.handleBackEvent();
    }

    @Override
    public void onTopFragmentExit(Class<? extends BaseFragment> topFragmentClass, Bundle params) {
        super.onTopFragmentExit(topFragmentClass, params);
    }

    @Override
    public Bundle onExit() {
        return super.onExit();
    }
}
