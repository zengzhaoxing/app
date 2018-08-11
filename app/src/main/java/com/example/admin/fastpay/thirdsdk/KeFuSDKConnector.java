package com.example.admin.fastpay.thirdsdk;

import android.util.Log;

import com.appkefu.lib.interfaces.KFAPIs;
import com.example.admin.fastpay.presenter.UserInfoManager;
import com.zxz.www.base.app.BaseApp;
import com.zxz.www.base.app.SDKConnector;

/**
 * Created by 曾宪梓 on 2018/3/11.
 */

public class KeFuSDKConnector extends SDKConnector {

    private static KeFuSDKConnector ourInstance = new KeFuSDKConnector();

    public static KeFuSDKConnector getInstance() {
        return ourInstance;
    }

    private KeFuSDKConnector() {
    }

    public void login(String userName) {
        KFAPIs.loginWithUserID(userName,mApp);
    }

    public void logout() {
       KFAPIs.Logout(mActivity);
    }

    public void startChat() {
        KFAPIs.startChat(mActivity, "miaodao1", "客服小秘书", "",true,5,null,null,false,false,null);
    }

}
