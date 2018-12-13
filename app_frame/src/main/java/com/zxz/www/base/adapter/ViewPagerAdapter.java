package com.zxz.www.base.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import java.util.ArrayList;
import java.util.List;


public class ViewPagerAdapter extends PagerAdapter {
    private List<View> mViewList;
    private int mCount;

    public ViewPagerAdapter(List<View> viewList, int count) {
        this.mViewList = viewList;
        this.mCount = mViewList == null ? 0 : count;
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0==arg1;
    }
    @Override
    public void destroyItem(ViewGroup container, int position,
                            Object object) {
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        position %= mViewList.size();
        if (position<0){
            position = mViewList.size()+position;
        }
        View view = mViewList.get(position);
        ViewParent vp =view.getParent();
        if (vp!=null){
            ViewGroup parent = (ViewGroup)vp;
            parent.removeView(view);
        }
        container.addView(view);
        return view;
    }
}
