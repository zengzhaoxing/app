package com.zxz.www.base.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.zxz.www.base.app.BaseActivity;
import com.zxz.www.base.app.BaseApp;

import rx.Observer;

/**
 * Created by 曾宪梓 on 2017/12/31.
 */

public class PermissionUtil {

    public static boolean havePermission(String permission) {
        return ContextCompat.checkSelfPermission(BaseApp.getContext(), permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermission(Activity activity, String permission, final OnPermissionRequest onPermissionRequest) {
        if (havePermission(permission)) {
            if (onPermissionRequest != null) {
                onPermissionRequest.onResult(true);
            }
            return;
        }
        RxPermissions mRxPermissions = new RxPermissions(activity);
        mRxPermissions.requestEach(permission).subscribe(new Observer<Permission>() {
            @Override
            public void onCompleted() {
                if (onPermissionRequest != null) {
                    onPermissionRequest.onResult(true);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (onPermissionRequest != null) {
                    onPermissionRequest.onResult(false);
                }
            }

            @Override
            public void onNext(Permission permission) {

            }
        });
    }

    public interface OnPermissionRequest {
        void onResult(boolean isAllow);
    }


}
