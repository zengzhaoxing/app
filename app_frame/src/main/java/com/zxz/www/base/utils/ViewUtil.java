package com.zxz.www.base.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.zxz.www.base.app.BaseApp;

import java.util.concurrent.atomic.AtomicInteger;

public class ViewUtil {

    private static long mLastRequestTime = 0;

    private static final long GAP_TIME = 500;

    private static View mRequestView;

    public static boolean isDoubleRequest(View view) {
        if (mRequestView != view) {
            mRequestView = view;
            mLastRequestTime = System.currentTimeMillis();
            return false;
        } else {
            long currentTimeTime = System.currentTimeMillis();
            if (currentTimeTime - mLastRequestTime < GAP_TIME) {
                mLastRequestTime = currentTimeTime;
                return true;
            } else {
                mLastRequestTime = currentTimeTime;
                return false;
            }
        }
    }

    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    public static int getDecorViewHeight(Activity activity) {
        return activity.getWindow().getDecorView().getHeight();
    }

    public static int generateViewId() {
        if (DeviceInfoUtil.getApiLevel() > 16) {
            return View.generateViewId();
        } else {
            AtomicInteger sNextGeneratedId = new AtomicInteger(1);
            for (;;) {
                final int result = sNextGeneratedId.get();
                // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
                int newValue = result + 1;
                if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
                if (sNextGeneratedId.compareAndSet(result, newValue)) {
                    return result;
                }
            }
        }

    }

    public static void interceptTouchEvent(View v){
        if(v != null){
            v.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }
    }

    public static Bitmap getBackground(View v){
        if(v == null){
            return null;
        }
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();
        Bitmap bitmap = v.getDrawingCache();
        if (bitmap != null) {
            bitmap = Bitmap.createBitmap(bitmap);
        }
        v.setDrawingCacheEnabled(false);
        v.buildDrawingCache(false);
        return bitmap;
    }

    public static void setChildrenEnable(ViewGroup parent, boolean enable) {
        for(int i = 0;parent != null && i < parent.getChildCount();i++) {
            View view = parent.getChildAt(i);
            view.setEnabled(enable);
            if (view instanceof ViewGroup) {
                setChildrenEnable((ViewGroup) view,enable);
            }
        }
    }

    public static void setChildrenLongClickEnable(ViewGroup parent, boolean enable) {
        for(int i = 0;parent != null && i < parent.getChildCount();i++) {
            View view = parent.getChildAt(i);
            view.setLongClickable(enable);
            if (view instanceof ViewGroup) {
                setChildrenEnable((ViewGroup) view,enable);
            }
        }
    }

    public static void LoadImg(ImageView imageView, String imgUrl, @DrawableRes int id) {
        Picasso.with(BaseApp.getContext()).load(imgUrl).error(id).placeholder(id).into(imageView);
    }


}

