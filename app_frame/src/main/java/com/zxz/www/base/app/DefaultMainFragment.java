package com.zxz.www.base.app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zxz.www.base.R;
import com.zxz.www.base.view.MainTab;

import java.util.ArrayList;

/**
 * Created by 曾宪梓 on 2018/7/30.
 */

public class DefaultMainFragment extends MainFragment implements MainTab.OnItemSelectListener {

    private FragmentManager mFragmentManager;

    private int mTabTextColor = Color.BLACK;

    private int mDisableTabTextColor = Color.GRAY;

    public void setTabTextColor(int enable,int disable) {
        mTabTextColor = enable;
        mDisableTabTextColor = disable;
    }

    private MainChildFragment mCurrentFragment;

    private ArrayList<MainChildFragment> mFragments = new ArrayList<>();

    private ArrayList<Integer> imgIds = new ArrayList<>();

    private ArrayList<Integer> disableImgIds = new ArrayList<>();

    private ArrayList<Integer> textIds = new ArrayList<>();

    public void addFragment(MainChildFragment fragment, @DrawableRes int imgId, @DrawableRes int disableImgId, @StringRes int textId) {
        mFragments.add(fragment);
        imgIds.add(imgId);
        disableImgIds.add(disableImgId);
        textIds.add(textId);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = getChildFragmentManager();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_default, container, false);
        MainTab mainTab = (MainTab) view.findViewById(R.id.tab);
        mainTab.setOnItemSelectListener(this);
        for(int i = 0;i < mFragments.size();i++) {
            mainTab.addTab(textIds.get(i),imgIds.get(i),disableImgIds.get(i),mTabTextColor,mDisableTabTextColor);
        }
        switchFragment(mFragments.get(0).getClass());
        return view;
    }

    public void switchFragment(Class<? extends MainChildFragment> fragmentClass) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        for (MainChildFragment fragment : mFragments) {
            Fragment mainChildFragment = mFragmentManager.findFragmentByTag(fragment.getClass().getName());
            if (mainChildFragment != null && mainChildFragment.getClass() != fragmentClass) {
                ft.hide(mainChildFragment);
            } else if (mainChildFragment != null && mainChildFragment.getClass() == fragmentClass) {
                ft.show(mainChildFragment);
                mCurrentFragment = fragment;
            } else if (mainChildFragment == null && fragment.getClass() == fragmentClass) {
                ft.add(fragment, fragment.getClass().getName());
                mCurrentFragment = fragment;
            }
        }
        ft.commitAllowingStateLoss();
    }

    @Override
    protected boolean handleBackEvent() {
        if (mCurrentFragment != null) {
            return mCurrentFragment.handleBackEvent();
        }
        return false;
    }

    @Override
    protected void onTopFragmentExit(Class<? extends BaseFragment> topFragmentClass, Bundle params) {
        if (mCurrentFragment != null) {
            mCurrentFragment.onTopFragmentExit(topFragmentClass, params);
        }
    }

    public BaseFragment getCurrentFragment() {
        return mCurrentFragment;
    }

    @Override
    public void onItemSelect(int position, boolean isDoubleClick) {
        if (!isDoubleClick) {
            switchFragment(mFragments.get(position).getClass());
        }
    }

}
