package com.zxz.www.base.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.zxz.www.base.utils.ViewUtil;


public abstract class BaseFragment extends Fragment {

    protected BaseActivity mBaseActivity;

    BaseFragment mPreFragment;

    protected abstract boolean handleBackEvent();

    protected abstract void onTopFragmentExit(Class<? extends BaseFragment> topFragmentClass, Bundle params);

    protected abstract Bundle onExit();

    public void close() {
        mBaseActivity.closeCurrentFragment();
    }

    public void refreshUi(Bundle param) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mBaseActivity = (BaseActivity) context;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ViewUtil.interceptTouchEvent(view);
        super.onViewCreated(view, savedInstanceState);
    }


    protected void backToFragment(Class fragmentClass) {
        while (mBaseActivity.getCurrentFragment() != null && !mBaseActivity.getCurrentFragment().getTag().equals(fragmentClass.getName())) {
            mBaseActivity.closeCurrentFragment();
        }
    }

}
