package com.zxz.www.base.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.zxz.www.base.utils.ProcessUtil;

import java.util.ArrayList;


final public class SDKAgent {

    private static SDKAgent mSDKAgent;

    public void addSDKConnector(SDKConnector connector) {
        mSDKConnectorList.add(connector);
    }

    public static SDKAgent getInstance() {
        if (mSDKAgent == null) {
            mSDKAgent = new SDKAgent();
        }
        return mSDKAgent;
    }

    private ArrayList<SDKConnector> mSDKConnectorList = new ArrayList<>();

    void doInAppCreate(BaseApp app) {
        for (SDKConnector sdkConnector : mSDKConnectorList) {
            if (ProcessUtil.isMainProcess() || !sdkConnector.mainProcessOnly()) {
                sdkConnector.onAppCreate(app);
            }
        }
    }

    void doAttachBaseContext(Context app) {
        for (SDKConnector sdkConnector : mSDKConnectorList) {
            if (ProcessUtil.isMainProcess() || !sdkConnector.mainProcessOnly()) {
                sdkConnector.attachBaseContext(app);
            }
        }
    }

    void doInMainActivityCreate(Bundle savedInstanceState, Activity activity) {
        for (SDKConnector sdkConnector : mSDKConnectorList) {
            sdkConnector.onMainActivityCreate(savedInstanceState, activity);
        }
    }

    void doInMainActivityOnStart() {
        for (SDKConnector sdkConnector : mSDKConnectorList) {
            sdkConnector.onMainActivityStart();
        }
    }

    void doInMainActivityResume() {
        for (SDKConnector sdkConnector : mSDKConnectorList) {
            sdkConnector.onMainActivityResume();
        }
    }

    void doInMainActivityPause() {
        for (SDKConnector sdkConnector : mSDKConnectorList) {
            sdkConnector.onMainActivityPause();
        }
    }

    void doInMainActivityStop() {
        for (SDKConnector sdkConnector : mSDKConnectorList) {
            sdkConnector.onMainActivityStop();
        }
    }

    void doInMainActivityDestroy() {
        for (SDKConnector sdkConnector : mSDKConnectorList) {
            sdkConnector.onMainActivityDestroy();
        }
    }

    void doInMainActivityResult(int requestCode, int resultCode, Intent data) {
        for (SDKConnector sdkConnector : mSDKConnectorList) {
            sdkConnector.onMainActivityResult(requestCode, resultCode, data);
        }
    }

    void doInMainActivityNewIntent(Intent intent) {
        for (SDKConnector sdkConnector : mSDKConnectorList) {
            sdkConnector.onMainActivityNewIntent(intent);
        }
    }

}
