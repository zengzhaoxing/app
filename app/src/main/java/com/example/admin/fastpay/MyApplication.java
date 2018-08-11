package com.example.admin.fastpay;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.admin.fastpay.thirdsdk.BuglySDKConnector;
import com.example.admin.fastpay.thirdsdk.KeFuSDKConnector;
import com.example.admin.fastpay.thirdsdk.WeChatSDKConnector;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.mob.MobSDK;
import com.zxz.www.base.app.BaseApp;
import com.zxz.www.base.app.SDKAgent;
import com.zxz.www.base.utils.GetNativeBitmapSDK;
import com.zxz.www.base.utils.SPUtil;

import java.util.Stack;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;


/**
 * Created by sun on 2017/5/17.
 */

public class MyApplication extends BaseApp {

    static {
        SDKAgent.getInstance().addSDKConnector(BuglySDKConnector.getInstance());
        SDKAgent.getInstance().addSDKConnector(WeChatSDKConnector.getInstance());
        SDKAgent.getInstance().addSDKConnector(KeFuSDKConnector.getInstance());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(false); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=591c244b");
        MobSDK.init(this);
    }


}
