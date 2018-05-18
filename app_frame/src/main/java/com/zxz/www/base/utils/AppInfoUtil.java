package com.zxz.www.base.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.zxz.www.base.app.BaseApp;

public class AppInfoUtil {

    public static String getPackageName() {
        return BaseApp.getContext().getPackageName();
    }

    public static int getVersionCode() {
        int versionCode = -1;
        try {
            versionCode = BaseApp.getContext().getPackageManager().getPackageInfo(BaseApp.getContext().getPackageName
                    (), 0).versionCode;
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

    public static String getChannel() {
        // TODO: 2017/9/11
        return null;
    }

    public static boolean havePermission(String permission) {
        return ContextCompat.checkSelfPermission(BaseApp.getContext(), permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static int getTargetSdkVersion() {
        return BaseApp.getContext().getApplicationInfo().targetSdkVersion;
    }

    public static void requestPermission(Activity activity ,String permission, int code) {
        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, code);
        }
    }



}
