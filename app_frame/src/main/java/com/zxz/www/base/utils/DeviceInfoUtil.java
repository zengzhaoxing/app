package com.zxz.www.base.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.zxz.www.base.app.BaseApp;

public class DeviceInfoUtil {

    public static int getApiLevel() {
        return Build.VERSION.SDK_INT;
    }

    public static String getDeviceName() {
        return android.os.Build.MODEL;
    }

    public static String getAndroidVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    public static String getDeviceSerial() {
        return android.os.Build.SERIAL;
    }

    public static int getScreenHeight(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);//当前activity
        return dm.heightPixels;
    }

    public static int getScreenWidth() {
        WindowManager wm = (WindowManager) BaseApp.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        return point.x;
    }

}
