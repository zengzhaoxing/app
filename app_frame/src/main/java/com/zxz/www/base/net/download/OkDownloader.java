package com.zxz.www.base.net.download;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 曾宪梓 on 2018/2/1.
 */

public class OkDownloader extends Downloader {

    private final OkHttpClient mOkHttpClient;

    private Call mCall;

    public OkDownloader(String downloadUrl, String fileName) {
        super(downloadUrl, fileName);
        mOkHttpClient = new OkHttpClient();
    }

    @Override
    public void starDownload() {
        Request request = new Request.Builder().url(mDownloadUrl).headers(Headers.of(mHeader)).build();
        mCall = mOkHttpClient.newCall(request);
        final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (mDownloadListener != null) {
                    mDownloadListener.onDownLoad(msg.what);
                }
                return false;
            }
        });
        mCall.enqueue(new Callback() {
            
            int mProgress;
            
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                InputStream is = response.body().byteStream();;
                FileOutputStream fos = new FileOutputStream(getFileUrl());
                byte[] buf = new byte[256];
                long total = response.body().contentLength();
                int size;
                int sum = 0;
                while ((size = is.read(buf)) != -1) {
                    sum += size;
                    fos.write(buf, 0, size);
                    int progress = (int) ((float)sum / (float)total * 100);
                    if (progress > mProgress) {
                        mProgress = progress;
                        handler.sendEmptyMessage(mProgress);
                    }
                }
                fos.flush();
                fos.close();
                is.close();
            }
        });

    }

    @Override
    public void stopDownload() {
        if (mCall != null) {
            mCall.cancel();
        }
    }
}
