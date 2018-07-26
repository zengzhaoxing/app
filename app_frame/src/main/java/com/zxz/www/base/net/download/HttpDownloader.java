package com.zxz.www.base.net.download;

import android.os.AsyncTask;
import android.util.Log;

import com.zxz.www.base.utils.AsyncTaskUtil;
import com.zxz.www.base.utils.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by igola on 2017/9/26.
 */

public class HttpDownloader extends Downloader {

    private static final String TAG = "HttpDownloader";

    private AsyncTask mTask;

    public HttpDownloader(String downloadUrl, String fileName) {
        super(downloadUrl, fileName);
        mTask = new AsyncTask<Object, Float, String>() {

            int mProgress = 0;

            @Override
            protected String doInBackground(Object... params) {
                try {
                    URL url = new URL(mDownloadUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    for (Object o : mHeader.entrySet()) {
                        Map.Entry entry = (Map.Entry) o;
                        String key = (String) entry.getKey();
                        String val = (String) entry.getValue();
                        conn.setRequestProperty(key, val);
                    }
                    InputStream is = conn.getInputStream();
                    FileOutputStream fos = new FileOutputStream(getFileUrl());
                    byte[] buf = new byte[256];
                    conn.connect();
                    int total = conn.getContentLength();
                    int size;
                    int sum = 0;
                    while ((size = is.read(buf)) != -1) {
                        sum += size;
                        fos.write(buf, 0, size);
                        int progress = (int) ((float)sum / (float)total * 100);
                        if (progress > mProgress) {
                            mProgress = progress;
                            publishProgress();
                        }
                    }
                    fos.close();
                    is.close();
                    Log.i(TAG, conn.getResponseMessage());
                    conn.disconnect();
                } catch (Exception e) {
                    Log.i(TAG, e.toString());
                    if (mDownloadListener != null) {
                        mDownloadListener.onDownLoad(-1);
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(String url) {
                if (mDownloadListener != null) {
                    mDownloadListener.onDownLoad(100);
                }
            }

            @Override
            protected void onProgressUpdate(Float... values) {
                if (mDownloadListener != null) {
                    mDownloadListener.onDownLoad((float) mProgress / 100);
                }
            }
        };
    }

    @Override
    public void starDownload() {
        mTask.execute(getFileUrl());
    }

    @Override
    public void stopDownload() {
        mTask.cancel(true);
    }
}
