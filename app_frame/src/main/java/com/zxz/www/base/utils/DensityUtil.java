package com.zxz.www.base.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import com.zxz.www.base.app.BaseApp;

public class DensityUtil {

    private static DisplayMetrics metric = new DisplayMetrics();

    public static int dip2px(float dpValue) {
        Context context = BaseApp.getContext();
        float scale = 0f;
        if(context != null && context.getResources() != null && context.getResources().getDisplayMetrics() != null){
            scale = context.getResources().getDisplayMetrics().density;
        }
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2sp(float pxValue) {
        final float fontScale = BaseApp.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int sp2px(float dpValue) {
        final float scale = BaseApp.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(float pxValue) {
        final float scale = BaseApp.getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
