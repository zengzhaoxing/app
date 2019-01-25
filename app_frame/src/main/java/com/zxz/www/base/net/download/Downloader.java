package com.zxz.www.base.net.download;

import android.app.Activity;
import android.util.Log;

import com.zxz.www.base.model.BaseModel;
import com.zxz.www.base.utils.FileUtil;
import com.zxz.www.base.utils.MyTask;

import java.util.HashMap;
import java.util.TimerTask;

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

    public void setContentLength(long contentLength) {
        mContentLength = contentLength;
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

    private Activity mActivity;

    private boolean mPublicProgress;

    Downloader(String downloadUrl, String fileName, boolean publicProgress, Activity activity) {
        mDownloadUrl = downloadUrl;
        mFileName = fileName;
        mActivity = activity;
        mPublicProgress = publicProgress;
    }

    public boolean isPause(){
        return !isStart && !isComplete();
    }

    private boolean isStart;

    public float getCurrProgress() {
        return (float)mDownLoadLength / (float)mContentLength;
    }

    public interface DownLoadListener {

        void onDownLoad(float progress,Downloader downloader);
    }

    public void setDownloadListener(DownLoadListener downloadListener) {
        mDownloadListener = downloadListener;
        if (mDownloadListener == null) {
            mMyTask.stop();
        } else {
            publicProgress();
        }
    }

    DownLoadListener mDownloadListener;

    protected MyTask mMyTask = new MyTask();

    private void publicProgress() {
        if (mDownloadListener != null && mActivity != null && mPublicProgress) {
            mMyTask.start(new TimerTask() {
                @Override
                public void run() {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mDownloadListener.onDownLoad(getCurrProgress(), Downloader.this);
                        }
                    });
                }
            }, 0, 1000);
        }
    }

    final public void starDownload(){
        if (!isStart) {
            isStart = true;
            starDownloadImpl();
            publicProgress();
        }
    }

    final public void stopDownload(){
        if (isStart) {
            isStart = false;
            stopDownloadImpl();
            mMyTask.stop();
        }
    }

    protected abstract void starDownloadImpl();

    protected abstract void stopDownloadImpl();


}
