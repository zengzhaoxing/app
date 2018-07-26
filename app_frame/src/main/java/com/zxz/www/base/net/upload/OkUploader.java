package com.zxz.www.base.net.upload;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;

import com.zxz.www.base.model.BaseModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 曾宪梓 on 2018/1/26.
 */

public class OkUploader<Resp extends BaseModel> extends Uploader<Resp> {

    private OkHttpClient mClient = new OkHttpClient();

    private Call[] mCall;

    public OkUploader(String url, Class cls, Bitmap... bitmap) {
        super(url, cls, bitmap);
    }

    @Override
    public void starUpload() {
        if (mCanUpLoadTogether || mBitmap.length == 1) {
            mCall = new Call[1];
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            for (int i = 0; mBitmap != null && i < mBitmap.length; i++) {
                Bitmap bitmap = mBitmap[i];
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                String name = "img" + i + ".png";
                builder.addFormDataPart(mDefaultName, name, RequestBody.create(MediaType.parse("image/png"), bos.toByteArray()));
            }
            MultipartBody body = builder.build();
            Request request = new Request.Builder()
                    .headers(Headers.of(mHeader))
                    .url(mUrl)
                    .post(body)
                    .build();
            mCall[0] = mClient.newCall(request);
            final Handler handler = new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    parseResponse((String) msg.obj,0);
                    return false;
                }
            });
            mCall[0].enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.i(TAG, "onFailure");
                    handler.sendEmptyMessage(0);
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    Log.i(TAG, "onResponse " + response);
                    Message message = Message.obtain();
                    message.obj = response.body() != null ? response.body().string() : null;
                    handler.sendMessage(message);
                }
            });
        } else {
            mCall = new Call[mBitmap.length];
            for (int i = 0; mBitmap != null && i < mBitmap.length; i++) {
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                Bitmap bitmap = mBitmap[i];
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos);
                String name = "img" + i + ".png";
                builder.addFormDataPart(mDefaultName, name, RequestBody.create(MediaType.parse("image/png"), bos.toByteArray()));
                MultipartBody body = builder.build();
                Request request = new Request.Builder()
                        .headers(Headers.of(mHeader))
                        .url(mUrl)
                        .post(body)
                        .build();
                mCall[i] = mClient.newCall(request);
                final int finalI = i;
                final Handler handler = new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        parseResponse((String) msg.obj, finalI);
                        return false;
                    }
                });
                    mCall[i].enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        handler.sendEmptyMessage(0);
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        Message message = Message.obtain();
                        message.obj = response.body() != null ? response.body().string() : null;
                        handler.sendMessage(message);
                    }
                });
            }
        }

    }

    @Override
    public void stopUpload() {
        if (mCall == null || mCall[0] == null) {
            return;
        }
        if (mCanUpLoadTogether) {
            mCall[0].cancel();
        } else {
            for (Call call : mCall) {
                call.cancel();
            }
        }
    }


}
