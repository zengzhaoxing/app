package com.example.admin.fastpay.thirdsdk;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;
import com.zxz.www.base.BuildConfig;
import com.zxz.www.base.app.BaseApp;
import com.zxz.www.base.app.SDKConnector;

/**
 * Created by 曾宪梓 on 2018/1/6.
 */
public class BuglySDKConnector extends SDKConnector {


    private static BuglySDKConnector ourInstance = new BuglySDKConnector();

    public static BuglySDKConnector getInstance() {
        return ourInstance;
    }

    private BuglySDKConnector() {
    }
    @Override
    protected void onAppCreate(BaseApp application) {
        super.onAppCreate(application);
        // 设置是否开启热更新能力，默认为true
        Beta.enableHotfix = true;
        // 设置是否自动下载补丁
        Beta.canAutoDownloadPatch = true;
        // 设置是否提示用户重启
        Beta.canNotifyUserRestart = true;
        // 设置是否自动合成补丁
        Beta.canAutoPatch = true;
        Beta.autoCheckUpgrade = false;

        Bugly.init(application, "f42c4e1d82", true);
    }

    @Override
    protected void onMainActivityCreate(Bundle savedInstanceState, Activity activity) {
        super.onMainActivityCreate(savedInstanceState, activity);
        Beta.checkUpgrade(false,false);
    }

    @Override
    protected void onAttachBaseContext(Context b) {
        super.onAttachBaseContext(b);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(b);

        // 安装tinker
        Beta.installTinker();
    }
}
