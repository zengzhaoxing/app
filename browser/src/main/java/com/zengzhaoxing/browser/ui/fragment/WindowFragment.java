package com.zengzhaoxing.browser.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zengzhaoxing.browser.Constants;
import com.zengzhaoxing.browser.R;
import com.zxz.www.base.app.BaseFragment;
import com.zxz.www.base.utils.KeyBoardUtil;

import java.util.Stack;

public class WindowFragment extends BaseFragment {

    private Stack<BaseFragment> mFragmentStack = new Stack<>();

    BrowserFragment mCurrBrowserFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        openNew(Constants.DEFAULT_WEB);
        return view;
    }

    @Override
    protected boolean handleBackEvent() {
        return mCurrBrowserFragment != null && mCurrBrowserFragment.handleBackEvent();
    }

    @Override
    protected void onTopFragmentExit(Class<? extends BaseFragment> topFragmentClass, Bundle params) {
        if (mCurrBrowserFragment != null) {
            mCurrBrowserFragment.onTopFragmentExit(topFragmentClass,params);
        }
    }

    @Override
    protected Bundle onExit() {
        return super.onExit();
    }

    public void openNew(String url) {
        mFragmentStack.removeAllElements();
        BrowserFragment browserFragment = new BrowserFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.DEFAULT_WEB,url);
        browserFragment.setArguments(bundle);
        openNew(browserFragment);
    }

    private void openNew(BrowserFragment fragment) {
        fragment.mPreBrowserFragment = mCurrBrowserFragment;
        mCurrBrowserFragment = fragment;
        getChildFragmentManager().beginTransaction().add(R.id.frame_layout, mCurrBrowserFragment).commitAllowingStateLoss();
    }

    public void goAhead() {
        BaseFragment fragment = mFragmentStack.pop();
        mBaseActivity.openNewFragment(fragment);
    }

    public boolean canGoAhead() {
        return !mFragmentStack.isEmpty();
    }

    public void closeCurr() {
        if (mCurrBrowserFragment != null) {
            mFragmentStack.add(this);
            KeyBoardUtil.closeKeyboard(getActivity().getWindow().getDecorView());
            Bundle bundle = mCurrBrowserFragment.onExit();
            Class cl = mCurrBrowserFragment.getClass();
            mCurrBrowserFragment =  mCurrBrowserFragment.mPreBrowserFragment;
            mCurrBrowserFragment.onTopFragmentExit(cl,bundle);;
            getChildFragmentManager().popBackStack();
        }
    }

    public final void goHome() {
        while (mCurrBrowserFragment.mPreBrowserFragment != null) {
            closeCurr();
        }
        mFragmentStack.removeAllElements();
    }


}
