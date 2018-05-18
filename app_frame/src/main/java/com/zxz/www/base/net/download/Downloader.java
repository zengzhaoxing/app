package com.zxz.www.base.net.download;

import com.zxz.www.base.utils.FileUtil;

import java.util.HashMap;

/**
 * Created by igola on 2017/9/18.
 */

public abstract class Downloader {

    String mDownloadUrl;

    HashMap<String ,String> mHeader = new HashMap<>();

    public String getFileUrl() {
        return mFileDir + "/" + mFileName;
    }

    public void setFileDir(String fileDir) {
        this.mFileDir = fileDir;
    }

    public void addHeader(String key, String value) {
        if (!mHeader.containsKey(key)) {
            mHeader.put(key, value);
        }
    }

    String mFileDir = FileUtil.getInstance().getDownLoadPath();

    private String mFileName;

    Downloader(String downloadUrl, String fileName) {
        mDownloadUrl = downloadUrl;
        mFileName = fileName;
    }

    public abstract void starDownload();

    public abstract void stopDownload();

    public interface DownLoadListener {

        void onDownLoad(float progress);
    }

    public void setDownloadListener(DownLoadListener downloadListener) {
        mDownloadListener = downloadListener;
    }

    DownLoadListener mDownloadListener;



}
