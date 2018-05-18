package com.zxz.www.base.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.WindowManager;

/**
 * Created by 曾宪梓 on 2017/10/21.
 */

public class WindowUtil {

    public static void setLight(Activity activity, float progress) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.screenBrightness = progress;
        activity.getWindow().setAttributes(lp);
    }

    public static void upLight(Activity activity, float upProgress) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        upProgress += lp.screenBrightness;
        upProgress = Math.min(upProgress,1f);
        upProgress = Math.max(upProgress,0f);
        Log.i("zxz", upProgress + "");
        lp.screenBrightness = upProgress;
        activity.getWindow().setAttributes(lp);
    }

    public static float getLightProgress(Activity activity) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        return lp.screenBrightness;
    }



}
