package com.zxz.www.base.net.request;

import com.android.volley.toolbox.JsonRequest;
import com.zxz.www.base.model.BaseModel;
import com.zxz.www.base.model.RequestModel;
import com.zxz.www.base.model.ResponseModel;
//import com.zxz.www.base.net.request.okhttp.OkHttpJsonRequester;
import com.zxz.www.base.net.request.okhttp.OkHttpRequesterFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 曾宪梓 on 2018/5/18.
 */

public class RequestUtil {

    private static RequesterFactory mFactory = OkHttpRequesterFactory.getInstance();

    private static HashMap<String, String> mHeader;

    public static void init(RequesterFactory factory) {
        mFactory = factory;
    }

    public static void reSetHeader(HashMap<String, String> header) {
        mHeader = header;
    }

    public static <T extends ResponseModel> void get(String url, RequestModel body, Class<T> respCls, OnResponseListener<T> listener) {
        JsonRequester request = mFactory.createGetRequester(url, body, respCls);
        start(request);
    }

    public static <T extends ResponseModel> void getList(String url, RequestModel body,Class<T> respCls,OnResponseListener<ArrayList<T>> listener) {
        JsonRequester request = mFactory.createGetRequesterList(url, body, respCls);
        start(request);
    }

    public static <T extends ResponseModel> void post(String url, RequestModel body,Class<T> respCls,OnResponseListener<T> listener) {
        JsonRequester request = mFactory.createPostRequester(url, body, respCls);
        start(request);
    }

    public static <T extends ResponseModel> void postList(String url, RequestModel body,Class<T> respCls,OnResponseListener<ArrayList<T>> listener) {
        JsonRequester request = mFactory.createPostRequesterList(url, body, respCls);
        start(request);
    }

    public static <T extends ResponseModel> void delete(String url, RequestModel body, Class<T> respCls, OnResponseListener<T> listener) {
        JsonRequester request = mFactory.createDeleteRequester(url, body, respCls);
        start(request);
    }

    private static void start(JsonRequester requester) {
        if (mHeader != null) {
            for (Map.Entry<String, String> entry : mHeader.entrySet()) {
                requester.addHeader(entry.getKey(),entry.getValue());
            }
        }
        requester.startRequest();
    }

}
