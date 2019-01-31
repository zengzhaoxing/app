package com.zxz.www.base.net.download;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.zxz.www.base.utils.AsyncTaskUtil;
import com.zxz.www.base.utils.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Executors;

/**
 * Created by igola on 2017/9/26.
 */

public class HttpDownloader extends Downloader {

    private static final String TAG = "HttpDownloader";

    private AsyncTask mTask;

    public HttpDownloader(String downloadUrl, String fileName, boolean publicProgress, Activity activity) {
        super(downloadUrl, fileName, publicProgress);
    }


    @Override
    public void starDownloadImpl() {
        mTask = new MyTask();
        mTask.execute(Executors.newCachedThreadPool(),getFileUrl());
    }

    @Override
    public void stopDownloadImpl() {
        mTask.cancel(true);
        mTask = null;
    }

    public class MyTask extends AsyncTask<Object, Float, Object> {

        @Override
        protected String doInBackground(Object... params) {
            try {
                URL url = new URL(mDownloadUrl);
                File file = new File(getFileUrl());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                for (Object o : mHeader.entrySet()) {
                    Map.Entry entry = (Map.Entry) o;
                    String key = (String) entry.getKey();
                    String val = (String) entry.getValue();
                    conn.setRequestProperty(key, val);
                }
                if (file.exists() && file.length() > 0) {
                    mDownLoadLength = file.length();
                    conn.setRequestProperty("Range", "bytes=" + mDownLoadLength + "-" + (mContentLength - 1));
                } else {
                    mDownLoadLength = 0;
                }
                conn.connect();
                InputStream is = conn.getInputStream();
                byte[] buf = new byte[256];
                int size;
                conn.connect();
                RandomAccessFile out = new RandomAccessFile(getFileUrl(),"rw");
                out.seek(mDownLoadLength);
                while (!isCancelled()) {
                    size = is.read(buf);
                    if (size != -1) {
                        mDownLoadLength += size;
                        out.write(buf, 0, size);
                    } else {
                        break;
                    }
                }
                out.close();
                is.close();
                Log.i(TAG, conn.getResponseMessage());
                conn.disconnect();
            } catch (Exception e) {
                Log.i(TAG, e.toString());
                return null;
            }
            return getFileUrl();
        }

        @Override
        protected void onPostExecute(Object url) {
            mMyTask.stop();
            if (mDownloadListener != null) {
                mDownloadListener.onDownLoad(getCurrProgress(),HttpDownloader.this);
            }
        }
    }

}
