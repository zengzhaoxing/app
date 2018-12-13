package com.zxz.www.base.app;

import android.os.Bundle;

public class FragmentRouter extends UIRouter {

    private BaseActivity mActivity;

    public FragmentRouter(BaseActivity activity) {
        mActivity = activity;
    }

    @Override
    public void openPage(Class<? extends UIPage> clz, Bundle bundle) {
        try {
            BaseFragment fragment = (BaseFragment) clz.newInstance();
            mActivity.openNewFragmentWithDefaultAnim(fragment);
            mUIPages.push(fragment);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void closeCurrentPageImpl() {
        mActivity.closeCurrentFragment();
    }


}
