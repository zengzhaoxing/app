package com.zxz.www.base.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


public abstract class SDKConnector {

    protected boolean mEnable = true;

    protected BaseApp mApp;

    public  void setEnable(boolean enable){
        mEnable = enable;
    }

    protected Activity mActivity;

    protected  void onAppCreate(BaseApp application){
        mApp = application;
    }

    protected  void onMainActivityCreate(Bundle savedInstanceState,Activity activity){
        mActivity = activity;
    }

    protected  void onMainActivityStart(){}

    protected  void onMainActivityResume(){}

    protected  void onMainActivityPause(){}

    protected  void onMainActivityStop(){}

    protected  void onMainActivityDestroy(){}

    protected  void onMainActivityResult(int requestCode, int resultCode, Intent data){}

    protected  void onMainActivityNewIntent(Intent intent){}

    protected boolean mainProcessOnly() {
        return true;
    }

    protected void onAttachBaseContext(Context b) {

    }

}
