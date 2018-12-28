package com.zengzhaoxing.browser.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zengzhaoxing.browser.R;
import com.zxz.www.base.app.MainFragment;

public class HomeFragment extends MainFragment {

    BrowserFragment mBrowserFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mBrowserFragment = new BrowserFragment();
        getChildFragmentManager().beginTransaction().add(R.id.frame_layout, mBrowserFragment).commitAllowingStateLoss();
        return view;
    }

    @Override
    protected boolean handleBackEvent() {
        return mBrowserFragment.handleBackEvent();
    }
}
