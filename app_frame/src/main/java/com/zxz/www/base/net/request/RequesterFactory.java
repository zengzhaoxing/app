package com.zxz.www.base.net.request;

import com.zxz.www.base.model.BaseModel;
import com.zxz.www.base.model.RequestModel;
import com.zxz.www.base.model.ResponseModel;
import com.zxz.www.base.net.request.okhttp.OkHttpRequesterFactory;

import java.lang.reflect.Type;


public abstract class RequesterFactory {

    public abstract JsonRequester createPostRequester(String url, RequestModel request, Class<? extends ResponseModel> responseClass);

    public abstract JsonRequester createGetRequester(String url,RequestModel request, Class<? extends ResponseModel> responseClass);

    public abstract JsonRequester createDeleteRequester(String url, RequestModel request, Class<? extends ResponseModel> responseClass);

    public abstract JsonRequester createPutRequester(String url,RequestModel request, Class<? extends ResponseModel> responseClass);

    public abstract JsonRequester createPostRequesterList(String url, RequestModel request, Class<? extends ResponseModel> responseClass);

    public abstract JsonRequester createGetRequesterList(String url,RequestModel request, Class<? extends ResponseModel> responseClass);


}
