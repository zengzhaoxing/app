//package com.zxz.www.base.net.request.okhttp;
//
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.support.annotation.NonNull;
//
//import com.zxz.www.base.model.RequestModel;
//import com.zxz.www.base.net.request.JsonRequester;
//
//import java.io.IOException;
//import java.util.concurrent.TimeUnit;
//
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.Headers;
//import okhttp3.MediaType;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//
///**
// * Created by 10714 on 2017/12/2.
// */
//
//public class OkHttpJsonRequester extends JsonRequester {
//
//    OkHttpClient mClient;
//
//    Call mCall;
//
//    public OkHttpJsonRequester(String url, RequestModel request, Class responseClass, int requestMethod) {
//        super(url, request, responseClass, requestMethod);
//        mClient = OkHttpRequestManager.OK_HTTP_CLIENT.newBuilder().readTimeout(mTimeOutMs, TimeUnit.MILLISECONDS).build();
//    }
//
//    @Override
//    public void startRequest() {
//        final Request.Builder build = new Request.Builder().headers(Headers.of(mHeader));
//        build.url(getUrl());
//        if (mRequestMethod == JsonRequester.POST && getBody() != null) {
//            build.post(RequestBody.create(MediaType.parse(mHeader.get(CONTENT_TYPE) + ";charset=utf-8"), getBody()));
//        } else if (mRequestMethod == JsonRequester.DELETE) {
//            if (getBody() != null) {
//                build.delete(RequestBody.create(MediaType.parse(mHeader.get(CONTENT_TYPE) + ";charset=utf-8"), getBody()));
//            } else {
//                build.delete();
//            }
//        }
//        final Request request = build.build();
//        mCall = mClient.newCall(request);
//        final Handler handler = new Handler(new Handler.Callback() {
//            @Override
//            public boolean handleMessage(Message msg) {
//                Bundle bundle = msg.getData();
//                parseResponse(bundle.getString(RESP_JSON),bundle.getInt(RESP_CODE));
//                return false;
//            }
//        });
//        mCall.enqueue(new Callback() {
//            @Override
//            public void onFailure(@NonNull Call call,@NonNull IOException e) {
//                Message message = Message.obtain();
//                Bundle bundle = new Bundle();
//                bundle.putInt(RESP_CODE, 400);
//                bundle.putString(RESP_JSON,null);
//                message.setData(bundle);
//                handler.sendMessage(message);
//            }
//
//            @Override
//            public void onResponse(@NonNull Call call,@NonNull Response response) throws IOException {
//                String json = response.body() != null ?  response.body().string() : null;
//                int respCode = response.code();
//                response.close();
//                Message message = Message.obtain();
//                Bundle bundle = new Bundle();
//                bundle.putInt(RESP_CODE, respCode);
//                bundle.putString(RESP_JSON,json);
//                message.setData(bundle);
//                handler.sendMessage(message);
//            }
//        });
//    }
//
//    private String RESP_CODE = "RESP_CODE";
//
//    private String RESP_JSON = "RESP_JSON";
//
//    @Override
//    public void stopRequest() {
//        mCall.cancel();
//    }
//}
