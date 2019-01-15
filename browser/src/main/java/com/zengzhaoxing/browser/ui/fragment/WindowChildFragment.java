package com.zengzhaoxing.browser.ui.fragment;

import android.content.Context;
import android.os.Bundle;

import com.zxz.www.base.app.BaseFragment;
public abstract class WindowChildFragment extends BaseFragment {

    WindowChildFragment mPreFragment;

    WindowFragment mWindowFragment;

    @Override
    protected boolean handleBackEvent() {
        return super.handleBackEvent();
    }

    @Override
    protected void onTopFragmentExit(Class<? extends BaseFragment> topFragmentClass, final Bundle params) {
        if (topFragmentClass == SearchFragment.class && params != null) {
            mWindowFragment.openNew(params);
        }
    }

    @Override
    protected Bundle onExit() {
        return super.onExit();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mWindowFragment = (WindowFragment) getParentFragment();
    }
}
