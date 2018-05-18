package com.zxz.www.base.utils;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import com.zxz.www.base.app.BaseApp;

import java.util.ArrayList;


/**
 * Created by igola on 2017/9/27.
 */

public class IntentUtil {

    public static void openPermissionSetting() {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", AppInfoUtil.getPackageName(), null));
        BaseApp.getContext().startActivity(intent);
    }

    public static void send(Uri uri) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("video/*");
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            BaseApp.getContext().startActivity(intent);
        } catch (Exception e) {
        }
    }

    public static void send(ArrayList<Uri> uris) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
            intent.setType("video/*");
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.putExtra(Intent.EXTRA_STREAM, uris);
            BaseApp.getContext().startActivity(intent);
        } catch (Exception e) {
        }

    }

}
