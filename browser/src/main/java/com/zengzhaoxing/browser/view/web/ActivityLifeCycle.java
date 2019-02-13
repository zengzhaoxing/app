package com.zengzhaoxing.browser.view.web;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.zxz.www.base.app.SDKConnector;

public class ActivityLifeCycle extends SDKConnector {

    private static ActivityLifeCycle ourInstance;

    public static ActivityLifeCycle getInstance() {
        if (ourInstance == null) {
            ourInstance = new ActivityLifeCycle();
        }
        return ourInstance;
    }

    private static final int CHOOSE_REQUEST_CODE = 0x9001;
    private ValueCallback<Uri> uploadFile;//定义接受返回值
    private ValueCallback<Uri[]> uploadFiles;

    @Override
    protected void onMainActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CHOOSE_REQUEST_CODE:
                    if (null != uploadFile) {
                        Uri result = data == null ? null : data.getData();
                        uploadFile.onReceiveValue(result);
                        uploadFile = null;
                    }
                    if (null != uploadFiles) {
                        Uri result = data == null ? null : data.getData();
                        uploadFiles.onReceiveValue(new Uri[]{result});
                        uploadFiles = null;
                    }
                    break;
                default:
                    break;
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            if (null != uploadFile) {
                uploadFile.onReceiveValue(null);
                uploadFile = null;
            }
            if (null != uploadFiles) {
                uploadFiles.onReceiveValue(null);
                uploadFiles = null;
            }
        }
    }


    // For Android 3.0+
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
        this.uploadFile = uploadMsg;
        openFileChooseProcess();
    }

    // For Android < 3.0
    public void openFileChooser(ValueCallback<Uri> uploadMsgs) {
        this.uploadFile = uploadMsgs;
        openFileChooseProcess();
    }

    // For Android  > 4.1.1
    //    @Override
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        this.uploadFile = uploadMsg;
        openFileChooseProcess();
    }

    // For Android  >= 5.0
    public boolean onShowFileChooser(WebView webView,
                                     ValueCallback<Uri[]> filePathCallback,
                                     WebChromeClient.FileChooserParams fileChooserParams) {
        this.uploadFiles = filePathCallback;
        openFileChooseProcess();
        return true;
    }

    private void openFileChooseProcess() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("*/*");
        mActivity.startActivityForResult(Intent.createChooser(i, "选择文件"), CHOOSE_REQUEST_CODE);
    }


}
