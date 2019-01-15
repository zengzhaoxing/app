package com.zengzhaoxing.browser.ui.fragment;

import android.content.Context;
import android.os.Bundle;

import com.zxz.www.base.app.BaseFragment;

import static com.zengzhaoxing.browser.Constants.DEFAULT_WEB;

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
            getView().post(new Runnable() {
                @Override
                public void run() {
                    mWindowFragment.openNew(params.getStringArray(DEFAULT_WEB));
                }
            });
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
