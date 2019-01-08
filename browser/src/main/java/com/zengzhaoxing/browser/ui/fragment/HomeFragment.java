package com.zengzhaoxing.browser.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zengzhaoxing.browser.R;
import com.zxz.www.base.app.BaseFragment;
import com.zxz.www.base.app.MainFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends MainFragment {

    private List<WindowFragment> mWindowFragments = new ArrayList<>();

    private ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mViewPager = new ViewPager(getActivity());
        mViewPager.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        if (mWindowFragments.isEmpty()) {
            mWindowFragments.add(new WindowFragment());
        }
        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mWindowFragments.get(position);
            }

            @Override
            public int getCount() {
                return mWindowFragments.size();
            }
        });
        return mViewPager;
    }

    @Override
    protected boolean handleBackEvent() {
        return mWindowFragments.get(mViewPager.getCurrentItem()).handleBackEvent();
    }

    @Override
    protected void onTopFragmentExit(Class<? extends BaseFragment> topFragmentClass, Bundle params) {
        mWindowFragments.get(mViewPager.getCurrentItem()).onTopFragmentExit(topFragmentClass,params);
    }

}
