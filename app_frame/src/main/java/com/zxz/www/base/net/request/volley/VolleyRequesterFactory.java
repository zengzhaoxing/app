package com.zxz.www.base.net.request.volley;

import com.zxz.www.base.model.BaseModel;
import com.zxz.www.base.net.request.JsonRequester;
import com.zxz.www.base.net.request.RequesterFactory;

/**
 * Created by igola on 2017/9/22.
 */

public class VolleyRequesterFactory extends RequesterFactory{

    @Override
    public JsonRequester createPostRequester(String url, BaseModel request, Class<? extends BaseModel> responseClass) {
        return new VolleyJsonRequester(url, request, responseClass, JsonRequester.POST);
    }

    @Override
    public JsonRequester createGetRequester(String url, BaseModel request, Class<? extends BaseModel> responseClass) {
        return new VolleyJsonRequester(url, request, responseClass, JsonRequester.GET);
    }

    @Override
    public JsonRequester createDeleteRequester(String url, BaseModel request, Class<? extends BaseModel> responseClass) {
        return new VolleyJsonRequester(url, request, responseClass, JsonRequester.DELETE);
    }

    @Override
    public JsonRequester createPutRequester(String url, BaseModel request, Class<? extends BaseModel> responseClass) {
        return new VolleyJsonRequester(url, request, responseClass, JsonRequester.PUT);
    }

    @Override
    public JsonRequester createPostRequesterList(String url, BaseModel request, Class<? extends BaseModel> responseClass) {
        VolleyJsonRequester volleyJsonRequester =  new VolleyJsonRequester(url, request, responseClass, JsonRequester.POST);
        volleyJsonRequester.setIsRequestList(true);
        return volleyJsonRequester;
    }

    @Override
    public JsonRequester createGetRequesterList(String url, BaseModel request, Class<? extends BaseModel> responseClass) {
        VolleyJsonRequester volleyJsonRequester =  new VolleyJsonRequester(url, request, responseClass, JsonRequester.GET);
        volleyJsonRequester.setIsRequestList(true);
        return volleyJsonRequester;
    }

    private static VolleyRequesterFactory factory = new VolleyRequesterFactory();

    public static VolleyRequesterFactory getInstance() {
        return factory;
    }

}
