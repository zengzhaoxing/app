package com.zxz.www.base.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zxz.www.base.R;
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

    public void goBack() {
        if (mBaseActivity != null && isAdded()) {
            mBaseActivity.closeCurrentFragment();
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

}
