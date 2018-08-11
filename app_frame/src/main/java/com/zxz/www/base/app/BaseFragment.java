package com.zxz.www.base.app;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.zxz.www.base.utils.ViewUtil;


public abstract class BaseFragment extends Fragment {

    protected BaseActivity mBaseActivity;

    BaseFragment mPreFragment;

    protected  boolean handleBackEvent(){
        return false;
    }

    protected  void onTopFragmentExit(Class<? extends BaseFragment> topFragmentClass, Bundle params){

    }

    protected  Bundle onExit(){
        return null;
    }

    public final void goBack() {
        if (isAdded() && isCurrentFragment()) {
            mBaseActivity.doGoBack();
        }
    }

    public void refreshUi() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            mBaseActivity = (BaseActivity) context;
        }
    }


    protected boolean interceptTouchEvent() {
        return true;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (interceptTouchEvent()) {
            ViewUtil.interceptTouchEvent(view);
        }
        super.onViewCreated(view, savedInstanceState);
    }

    public final boolean isCurrentFragment() {
        return mBaseActivity != null && this == mBaseActivity.mCurrentFragment;
    }

}
