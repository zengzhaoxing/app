package com.zxz.www.base.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.zxz.www.base.app.BaseApp;

public class Util {


    public static Drawable getApkIcon(String absPath) {
        Context context = BaseApp.getContext();
        PackageManager pm = context.getPackageManager();
        PackageInfo pkgInfo = pm.getPackageArchiveInfo(absPath,PackageManager.GET_ACTIVITIES);
        if (pkgInfo != null) {
            ApplicationInfo appInfo = pkgInfo.applicationInfo;
            /* 必须加这两句，不然下面icon获取是default icon而不是应用包的icon */
            appInfo.sourceDir = absPath;
            appInfo.publicSourceDir = absPath;
            /* icon1和icon2其实是一样的 */
           return pm.getApplicationIcon(appInfo);// 得到图标信息
//            Drawable icon2 = appInfo.loadIcon(pm);
        }
        return null;
    }

}
