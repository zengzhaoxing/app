package com.zxz.www.base.net.download;

import com.zxz.www.base.model.BaseModel;
import com.zxz.www.base.utils.FileUtil;

import java.util.HashMap;

/**
 * Created by igola on 2017/9/18.
 */

public abstract class Downloader{

    public static final int DOWNLOAD_FAIL = -1;

    String mDownloadUrl;

    HashMap<String, String> mHeader = new HashMap<>();

    public long getContentLength() {
        return mContentLength;
    }

    public long getDownLoadLength() {
        return mDownLoadLength;
    }

    long mContentLength = -100;

    long mDownLoadLength;

    public boolean isComplete() {
        return mContentLength == mDownLoadLength;
    }

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

    public String getFileDir() {
        return mFileDir;
    }

    private String mFileDir = FileUtil.getInstance().getDownLoadPath();

    public String getFileName() {
        return mFileName;
    }

    private String mFileName;

    Downloader(String downloadUrl, String fileName) {
        mDownloadUrl = downloadUrl;
        mFileName = fileName;
    }

    public abstract void starDownload();

    public abstract void stopDownload();

    public abstract boolean isPause();

    public int getCurrProgress() {
        return (int) (mDownLoadLength / mContentLength * 100);
    }

    public interface DownLoadListener {

        void onDownLoad(int progress,Downloader downloader);
    }

    public void setDownloadListener(DownLoadListener downloadListener) {
        mDownloadListener = downloadListener;
    }

    DownLoadListener mDownloadListener;


}
