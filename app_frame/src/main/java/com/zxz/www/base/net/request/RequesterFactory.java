package com.zxz.www.base.net.request;


import com.zxz.www.base.model.BaseModel;
import com.zxz.www.base.net.request.okhttp.OkHttpRequesterFactory;

import java.lang.reflect.Type;


public abstract class RequesterFactory {

    public abstract JsonRequester createPostRequester(String url, BaseModel request, Class<? extends BaseModel> responseClass);

    public abstract JsonRequester createGetRequester(String url,BaseModel request, Class<? extends BaseModel> responseClass);

    public abstract JsonRequester createDeleteRequester(String url, BaseModel request, Class<? extends BaseModel> responseClass);

    public abstract JsonRequester createPutRequester(String url,BaseModel request, Class<? extends BaseModel> responseClass);

    public abstract JsonRequester createPostRequesterList(String url, BaseModel request, Class<? extends BaseModel> responseClass);

    public abstract JsonRequester createGetRequesterList(String url,BaseModel request, Class<? extends BaseModel> responseClass);

    public static RequesterFactory getDefaultRequesterFactory() {
        return OkHttpRequesterFactory.getInstance();
    }

}
