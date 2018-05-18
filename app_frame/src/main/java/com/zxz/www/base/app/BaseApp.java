package com.zxz.www.base.app;

import android.app.Application;
import android.content.Context;

import com.zxz.www.base.utils.SPUtil;

public class BaseApp extends Application {

    public static Context getContext() {
        return _context;
    }

    private static Context _context;

    private static final String FIRST_INSTALL = "FIRST_INSTALL";

    @Override
    public void onCreate() {
        super.onCreate();
        _context = getApplicationContext();
        SDKAgent.getInstance().doInAppCreate(this);
    }

    public static boolean isFirstInstall() {
        return (boolean) SPUtil.get(FIRST_INSTALL, true);
    }

    public static void setUnFirstInstall() {
        SPUtil.put(FIRST_INSTALL,false);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        SDKAgent.getInstance().doAttachBaseContext(base);
    }
}
