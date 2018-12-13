package com.example.admin.fastpay.test;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.fastpay.R;
import com.zxz.www.base.adapter.ViewPagerAdapter;
import com.zxz.www.base.utils.ResUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextSelector extends ViewPager{


    public TextSelector(Context context) {
        super(context);
    }

    public TextSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean b = super.onInterceptTouchEvent(ev);
        Log.i("zxz", "ViewPager onInterceptTouchEvent " + b + " " + ev.toString());
        return b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.i("zxz", "ViewPager onTouchEvent ");
        return super.onTouchEvent(ev);
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        boolean b = super.canScroll(v, checkV, dx, x, y);
        Log.i("zxz", "ViewPager canScroll " + b);
        return b;
    }


}
