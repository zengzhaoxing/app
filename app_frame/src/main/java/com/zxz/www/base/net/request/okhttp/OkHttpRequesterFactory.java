package com.zxz.www.base.net.request.okhttp;

import com.zxz.www.base.model.BaseModel;
import com.zxz.www.base.model.RequestModel;
import com.zxz.www.base.model.ResponseModel;
import com.zxz.www.base.net.request.JsonRequester;
import com.zxz.www.base.net.request.RequesterFactory;

/**
 * Created by igola on 2017/9/22.
 */

public class OkHttpRequesterFactory extends RequesterFactory{

    @Override
    public JsonRequester createPostRequester(String url, RequestModel request, Class<? extends ResponseModel> responseClass) {
        return new OkHttpJsonRequester(url, request, responseClass, JsonRequester.POST);
    }

    @Override
    public JsonRequester createGetRequester(String url, RequestModel request, Class<? extends ResponseModel> responseClass) {
        return new OkHttpJsonRequester(url, request, responseClass, JsonRequester.GET);
    }

    @Override
    public JsonRequester createDeleteRequester(String url, RequestModel request, Class<? extends ResponseModel> responseClass) {
        return new OkHttpJsonRequester(url, request, responseClass, JsonRequester.DELETE);
    }

    @Override
    public JsonRequester createPutRequester(String url, RequestModel request, Class<? extends ResponseModel> responseClass) {
        return new OkHttpJsonRequester(url, request, responseClass, JsonRequester.PUT);
    }

    @Override
    public JsonRequester createPostRequesterList(String url, RequestModel request, Class<? extends ResponseModel> responseClass) {
        OkHttpJsonRequester volleyJsonRequester =  new OkHttpJsonRequester(url, request, responseClass, JsonRequester.POST);
        volleyJsonRequester.setIsRequestList(true);
        return volleyJsonRequester;
    }

    @Override
    public JsonRequester createGetRequesterList(String url, RequestModel request, Class<? extends ResponseModel> responseClass) {
        OkHttpJsonRequester volleyJsonRequester =  new OkHttpJsonRequester(url, request, responseClass, JsonRequester.GET);
        volleyJsonRequester.setIsRequestList(true);
        return volleyJsonRequester;
    }

    private static OkHttpRequesterFactory factory = new OkHttpRequesterFactory();

    public static OkHttpRequesterFactory getInstance() {
        return factory;
    }

}
