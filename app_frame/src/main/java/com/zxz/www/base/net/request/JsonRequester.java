package com.zxz.www.base.net.request;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.zxz.www.base.model.BaseModel;
import com.zxz.www.base.net.request.parser.DefaultListModelParser;
import com.zxz.www.base.net.request.parser.DefaultModelParser;
import com.zxz.www.base.net.request.parser.IParser;
import com.zxz.www.base.utils.StringUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by igola on 2017/9/11.
 */

public abstract class JsonRequester<RequestModel extends BaseModel, ResponseModel extends BaseModel> {

    public static final String CONTENT_TYPE_URLENCODED = "application/x-www-form-urlencoded";

    public static final String CONTENT_TYPE_MULTIPART = "multipart/form-data";

    public static final String CONTENT_TYPE_JSON = "application/json";

    public static final String CONTENT_TYPE = "Content-Type";

    private static final String TAG = "JsonRequester";
    public static int GET = 0;
    public static int POST = 1;
    public static int PUT = 2;
    public static int DELETE = 3;
    public static int HEAD = 4;
    public static int OPTIONS = 5;
    public static int TRACE = 6;
    public static int PATCH = 7;

    private RequestModel mRequestData;

    protected HashMap<String, String> mHeader;

    protected int mRequestMethod;

    private String mUrl;

    public void setIParser(IParser IParser) {
        mIParser = IParser;
    }

    private IParser mIParser;

    public void setIsRequestList(boolean isRequestList) {
        this.mIsRequestList = isRequestList;
    }

    private boolean mIsRequestList;

    public JsonRequester(String url, RequestModel request, Class<ResponseModel> responseClass, int requestMethod) {
        mUrl = url;
        mRequestData = request;
        mHeader = new HashMap<>();
        mRequestMethod = requestMethod;
    }

    public void setListener(OnResponseListener listener) {
        this.mListener = listener;
    }

    private OnResponseListener mListener;

    public void setTimeOutMs(int timeOutMs) {
        this.mTimeOutMs = timeOutMs;
    }

    protected int mTimeOutMs = 25000;

    public void setRepeatTime(int mRepeatTime) {
        this.mRepeatTime = mRepeatTime;
    }

    protected int mRepeatTime = 1;

    protected String getUrl() {
        if (mRequestMethod == GET && mRequestData != null) {
            return mUrl + "?" + mRequestData.toGetJson();
        } else {
            return mUrl;
        }
    }

    public void addHeader(String key, String value) {
        if (!mHeader.containsKey(key)) {
            mHeader.put(key, value);
        }
    }

    public void addHeader(HashMap<String, String> header) {
        if (header == null) {
            return;
        }
        for(Map.Entry<String, String> entry: header.entrySet()) {
            mHeader.put(entry.getKey(),entry.getValue());
        }
    }

    public abstract void startRequest();

    public abstract void stopRequest();

    protected final String getBody() {
        if (!mHeader.containsKey(CONTENT_TYPE)) {
            mHeader.put(CONTENT_TYPE, CONTENT_TYPE_JSON);
        }
        String body = null;
        String contentType = mHeader.get(CONTENT_TYPE);
        if (mRequestData == null) {
            body = null;
        }
        else if(CONTENT_TYPE_JSON.equals(contentType)) {
            body = mRequestData.toJson();
        } else if (CONTENT_TYPE_URLENCODED.equals(contentType)) {
            body = mRequestData.toGetJson();
        }
        return body;
    }

    public interface OnResponseListener<T> {
        void onResponse(T response, int resCode);
    }

    protected final void parseResponse(byte[] bytes, int respCode) {
//        Log.d(TAG, " url : " + getUrl());
//        Log.d(TAG, " body : " + getBody());
//        Log.d(TAG, " header : " + mHeader.toString());
////        Log.d(TAG, " response : " + jsonResponse);
//        Log.d(TAG, " respCode : " + respCode);
        if (mIParser == null) {
            if (mIsRequestList) {
                mIParser = new DefaultListModelParser<ResponseModel>();
            } else {
                mIParser = new DefaultModelParser<ResponseModel>();
            }
        }
        Object obj = null;
        try {
            obj = mIParser.parser(bytes);
        } catch (Exception ignored) {

        }finally {
            callListener(obj,respCode);
        }
    }

    private void callListener(Object o, int code) {
        if (mListener != null) {
            mListener.onResponse(o, code);
        }
    }


}
