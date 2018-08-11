package com.example.admin.fastpay.net;

import android.graphics.Bitmap;
import android.os.Handler;

import com.example.admin.fastpay.model.response.UpLoadResp;
import com.example.admin.fastpay.presenter.UserInfoManager;
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
        if (UserInfoManager.isLogin()) {
            mHeader.put("Authorization", "Bearer " + UserInfoManager.getToken());
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

    public static void upload(final String url, final Uploader.UpLoadListener listener, final Bitmap... bitmap) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                Uploader uploader = new OkUploader(url, UpLoadResp.class, bitmap);
                uploader.addHeader("Authorization","Bearer " + UserInfoManager.getToken());
                uploader.setUploadListener(listener);
                uploader.starUpload();
            }
        });
    }

}
