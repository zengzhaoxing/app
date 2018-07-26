package com.zxz.www.base.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.zxz.www.base.app.BaseApp;

public class AppInfoUtil {

    public static String getPackageName() {
        return BaseApp.getContext().getPackageName();
    }

    public static int getVersionCode() {
        int versionCode = -1;
        try {
            versionCode = BaseApp.getContext().getPackageManager().getPackageInfo(BaseApp.getContext().getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    public static String getVersionName() {
        String versionName = "";
        try {
            versionName = BaseApp.getContext().getPackageManager().getPackageInfo(BaseApp.getContext().getPackageName
                    (), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public static double getVersionDouble() {
        return Double.parseDouble(getVersionName());
    }

    public static String getChannel() {
        // TODO: 2017/9/11
        return null;
    }

    public static int getTargetSdkVersion() {
        return BaseApp.getContext().getApplicationInfo().targetSdkVersion;
    }

}
