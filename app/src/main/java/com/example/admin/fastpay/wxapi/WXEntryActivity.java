package com.example.admin.fastpay.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.admin.fastpay.thirdsdk.WeChatSDKConnector;

public class WXEntryActivity extends Activity {

    private static final String TAG = "WXEntryActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WeChatSDKConnector.getInstance().handleIntent(getIntent());
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        WeChatSDKConnector.getInstance().handleIntent(getIntent());
        finish();
    }

}