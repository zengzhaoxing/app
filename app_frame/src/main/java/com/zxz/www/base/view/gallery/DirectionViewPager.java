package com.zxz.www.base.view.gallery;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.zxz.www.base.R;


public class DirectionViewPager extends ViewPager {

    private boolean mIsVertical;

    private boolean mAllowManual;

    public DirectionViewPager(Context context, boolean isVertical, boolean allowManual) {
        super(context);
        init(context,null,isVertical,allowManual);
    }

    public DirectionViewPager(Context context, AttributeSet attrs, boolean isVertical, boolean allowManual) {
        super(context, attrs);
        init(context,attrs,isVertical,allowManual);
    }

    private void init(Context context,AttributeSet attrs,boolean isVertical,boolean allowManual) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DirectionViewPager, 0, 0);
        mIsVertical = a.getBoolean(R.styleable.DirectionViewPager_vertical,isVertical);
        mAllowManual = a.getBoolean(R.styleable.DirectionViewPager_manual,allowManual);
        if(mIsVertical){
            setPageTransformer(true, new VerticalPageTransformer());
            setOverScrollMode(OVER_SCROLL_NEVER);
        }
        a.recycle();
    }

    private class VerticalPageTransformer implements ViewPager.PageTransformer {

        @Override
        public void transformPage(View view, float position) {
            if (position < -1) {
                view.setAlpha(0);

            } else if (position <= 1) {
                view.setAlpha(1);

                view.setTranslationX(view.getWidth() * -position);

                float yPosition = position * view.getHeight();
                view.setTranslationY(yPosition);

            } else {
                view.setAlpha(0);
            }
        }
    }

    private MotionEvent swapXY(MotionEvent ev) {
        float width = getWidth();
        float height = getHeight();

        float newX = (ev.getY() / height) * width;
        float newY = (ev.getX() / width) * height;

        ev.setLocation(newX, newY);

        return ev;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(!mAllowManual){
            return false;
        }else if(!mIsVertical){
            return super.onInterceptTouchEvent(ev);
        }else{
            boolean intercepted = super.onInterceptTouchEvent(swapXY(ev));
            swapXY(ev);
            return intercepted;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(!mAllowManual){
            return false;
        }else if(!mIsVertical){
            return super.onTouchEvent(ev);
        }else{
            return super.onTouchEvent(swapXY(ev));
        }
    }
}
