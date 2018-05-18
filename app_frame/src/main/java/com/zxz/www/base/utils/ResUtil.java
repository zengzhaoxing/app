package com.zxz.www.base.utils;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.zxz.www.base.app.BaseApp;


public class ResUtil {

    public static int getColor(int resId) {
        return ContextCompat.getColor(BaseApp.getContext(), resId);
    }

    public static Drawable getDrawable(int resId) {
        return ContextCompat.getDrawable(BaseApp.getContext(), resId);
    }

    public static String getString(int resId) {
        return BaseApp.getContext().getString(resId);
    }

    public static float getDimension(int resId) {
        return BaseApp.getContext().getResources().getDimension(resId);
    }
}
