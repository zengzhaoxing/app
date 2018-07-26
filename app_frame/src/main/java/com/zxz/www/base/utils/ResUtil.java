package com.zxz.www.base.utils;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.zxz.www.base.app.BaseApp;


public class ResUtil {

    public static int getColor(int resId) {
        if (BaseApp.getContext() == null) {
            return 0;
        } else {
            return BaseApp.getContext().getResources().getColor(resId);
        }
    }

    public static Drawable getDrawable(int resId) {
        if (BaseApp.getContext() == null) {
            return new ColorDrawable(0x00000000);
        } else {
            return BaseApp.getContext().getResources().getDrawable(resId);
        }
    }

    public static String getString(int resId) {
        if (BaseApp.getContext() == null) {
            return "";
        } else {
            return BaseApp.getContext().getString(resId);
        }
    }

    public static float getDimension(int resId) {
        if (BaseApp.getContext() == null) {
            return 0.0f;
        } else {
            return BaseApp.getContext().getResources().getDimension(resId);
        }
    }
}
