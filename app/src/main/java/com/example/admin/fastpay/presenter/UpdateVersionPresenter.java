package com.example.admin.fastpay.presenter;



import android.app.Activity;

import com.example.admin.fastpay.model.response.UpdateApkMod;
import com.example.admin.fastpay.utils.UrlBase;
import com.zxz.www.base.net.download.Downloader;
import com.zxz.www.base.net.download.HttpDownloader;
import com.zxz.www.base.net.request.JsonRequester;
import com.zxz.www.base.net.request.RequesterFactory;
import com.zxz.www.base.utils.AppInfoUtil;
import com.zxz.www.base.utils.IntentUtil;

import java.io.File;

/**
 * Created by 曾宪梓 on 2017/12/31.
 */

public class UpdateVersionPresenter implements JsonRequester.OnResponseListener<UpdateApkMod> {

    private static   UpdateApkMod mUpdateApkMod;

    private static final String APK_NAME = "miaodao.apk";

    private Activity mActivity;

    private Downloader mDownloader;

    public UpdateVersionPresenter(Activity activity) {
        mActivity = activity;
    }

    public static boolean haveNewVersion() {
        return mUpdateApkMod != null &&
                mUpdateApkMod.getData() != null
                && mUpdateApkMod.getData().getVersion() > AppInfoUtil.getVersionDouble();
    }

    public void getUpdateInfo() {
        RequesterFactory factory = RequesterFactory.getDefaultRequesterFactory();
        JsonRequester request = factory.createPostRequester(UrlBase.UPDATABANBEN, null, UpdateApkMod.class);
        request.setListener(this);
        request.startRequest();
    }

    public void updateApk() {
        if (mDownloader == null) {
            return;
        }
        mDownloader.setDownloadListener(new Downloader.DownLoadListener() {
            @Override
            public void onDownLoad(float progress) {
                if (mUpdateApkListener != null) {
                    mUpdateApkListener.onUpdating(progress);
                }
                if (progress == 100) {
                    IntentUtil.installApk(new File(mDownloader.getFileUrl()),mActivity);
                }
            }
        });
        mDownloader.starDownload();
    }

    @Override
    public void onResponse(UpdateApkMod response, int resCode) {
        mUpdateApkMod = response;
        mDownloader = new HttpDownloader(mUpdateApkMod != null ? mUpdateApkMod.getData().getUpdateUrl() : "https://www.miaodaochina.cn/app/mdsk.apk",APK_NAME);
        if (mUpdateApkListener != null) {
            mUpdateApkListener.onUpdateInfo();
        }
    }

    public void setUpdateApkListener(UpdateApkListener updateApkListener) {
        mUpdateApkListener = updateApkListener;
    }

    private UpdateApkListener mUpdateApkListener;

    public interface UpdateApkListener {
        void onUpdateInfo();

        void onUpdating(float progress);
    }

    public void cancelUpdateApk() {
        if (mDownloader != null) {
            mDownloader.stopDownload();
        }
    }




}
