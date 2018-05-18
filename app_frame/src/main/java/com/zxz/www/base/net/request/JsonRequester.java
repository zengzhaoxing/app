package com.zxz.www.base.net.request;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.zxz.www.base.model.BaseModel;
import com.zxz.www.base.model.RequestModel;
import com.zxz.www.base.model.ResponseModel;
import com.zxz.www.base.utils.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by igola on 2017/9/11.
 */

public abstract class JsonRequester<req extends RequestModel, resp extends ResponseModel> {

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

    protected req mRequestData;

    protected Class<resp> mResponseClass;

    protected HashMap<String ,String> mHeader;

    protected int mRequestMethod;

    protected String mUrl;

    private Gson mGson;

    public void setIsRequestList(boolean isRequestList) {
        this.mIsRequestList = isRequestList;
    }

    private boolean mIsRequestList;


    public JsonRequester(String url, req request, Class<resp> responseClass, int requestMethod) {
        mUrl = url;
        mRequestData = request;
        mHeader = new HashMap<>();
        mResponseClass = responseClass;
        mRequestMethod = requestMethod;
        mHeader.put("Content-Type", "application/json;charset=UTF-8");
        mHeader.put("Accept-Encoding", "gzip");
        mHeader.put("Accept", "application/json");
        mGson = new Gson();
    }

    public void setListener(OnResponseListener listener) {
        this.mListener = listener;
    }

    protected OnResponseListener mListener;

    public void setTimeOutMs(int timeOutMs) {
        this.mTimeOutMs = timeOutMs;
    }

    protected int mTimeOutMs = 15000;

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

    public abstract void startRequest();

    public abstract void stopRequest();

    protected final void parseResponse(String jsonResponse,int respCode) {
        Log.d(TAG, "JsonRequester url : " + mUrl);
        Log.d(TAG, "JsonRequester body : " + (mRequestData != null ? mRequestData.toJson() : "null"));
        Log.d(TAG, "JsonRequester header : " + mHeader.toString());
        Log.d(TAG, "JsonRequester response : " + jsonResponse);
        Log.d(TAG, "JsonRequester respCode : " + respCode);
        if (StringUtil.isBlank(jsonResponse)) {
            if (mListener != null) {
                mListener.onResponse(null, respCode);
            }
        } else {
            if (!mIsRequestList) {
                resp obj = null;
                try {
                    obj = mGson.fromJson(jsonResponse, mResponseClass);
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                    callResponse(null,1000);
                }
                callResponse(obj,respCode);
            } else {
                ArrayList<resp> lcs = null;
                try {
                    lcs = new ArrayList<>();
                    Gson gson = new Gson();
                    JsonParser parser = new JsonParser();
                    JsonArray Jarray = parser.parse(jsonResponse).getAsJsonArray();
                    for(JsonElement obj : Jarray ){
                        resp cse = gson.fromJson( obj , mResponseClass);
                        lcs.add(cse);
                    }
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                    callResponse(null,1000);
                }
                callResponse(lcs,respCode);
            }
        }
    }

    private  <T> void callResponse(T resp, int code) {
        if (mListener != null) {
            mListener.onResponse(resp,code);
        }
    }

}
