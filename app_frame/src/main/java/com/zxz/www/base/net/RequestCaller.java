package com.zxz.www.base.net;

import android.graphics.Bitmap;
import android.os.Handler;
import com.zxz.www.base.model.BaseModel;
import com.zxz.www.base.net.request.JsonRequester;
import com.zxz.www.base.net.request.RequesterFactory;
import com.zxz.www.base.net.upload.OkUploader;
import com.zxz.www.base.net.upload.Uploader;

import java.util.HashMap;
import java.util.List;

/**
 * Created by 曾宪梓 on 2018/1/10.
 */

public class RequestCaller {

    private static RequesterFactory mFactory = RequesterFactory.getDefaultRequesterFactory();

    private static void start(JsonRequester requester,JsonRequester.OnResponseListener listener) {
        if (mHeader == null) {
            mHeader = new HashMap<>();
        }
        requester.addHeader(mHeader);
        requester.setListener(listener);
        requester.startRequest();
    }

    public static <Resp extends BaseModel> void get(String url, BaseModel body, Class<Resp> responseClass,JsonRequester.OnResponseListener<Resp> listener){
        JsonRequester jsonRequest = mFactory.createGetRequester(url, body, responseClass);
        start(jsonRequest,listener);
    }

    public static <Resp extends BaseModel> void delete(String url, BaseModel body, Class<Resp> responseClass,JsonRequester.OnResponseListener<Resp> listener){
        JsonRequester jsonRequest = mFactory.createDeleteRequester(url, body, responseClass);
        start(jsonRequest,listener);
    }

    public static void withHeader(HashMap<String, String> header) {
        mHeader = header;
    }

    private static HashMap<String, String> mHeader;

    public static <Resp extends BaseModel> void post(String url, BaseModel body, Class<Resp> responseClass, JsonRequester.OnResponseListener<Resp> listener){
        JsonRequester jsonRequest = mFactory.createPostRequester(url, body, responseClass);
        start(jsonRequest,listener);
    }

    public static <Resp extends BaseModel> void postList(String url, BaseModel body, Class<Resp> responseClass, JsonRequester.OnResponseListener<List<Resp>> listener){
        JsonRequester jsonRequest = mFactory.createPostRequesterList(url, body, responseClass);
        start(jsonRequest,listener);
    }

    public static <Resp extends BaseModel> void getList(String url, BaseModel body, Class<Resp> responseClass,JsonRequester.OnResponseListener<List<Resp>> listener){
        JsonRequester jsonRequest = mFactory.createGetRequesterList(url, body, responseClass);
        start(jsonRequest,listener);
    }

    public static <T> void upload(final String url, final Uploader.UpLoadListener listener,final Class<T> resp,  final Bitmap... bitmap) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                Uploader uploader = new OkUploader(url, resp, bitmap);
                uploader.setUploadListener(listener);
                uploader.starUpload();
            }
        });
    }

}
