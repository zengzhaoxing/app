package com.zxz.www.base.net.upload;

import android.content.pm.ProviderInfo;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.zxz.www.base.model.BaseModel;
import com.zxz.www.base.utils.FileUtil;
import com.zxz.www.base.utils.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by igola on 2017/9/18.
 */

public abstract class Uploader<Resp extends BaseModel> {

    static final String TAG = "Uploader";

    private Class<Resp> mRespClass;

    String mDefaultName = "file";

    String mUrl;

    HashMap<String ,String> mHeader = new HashMap<>();

    Bitmap mBitmap[];

    public void addHeader(String key, String value) {
        if (!mHeader.containsKey(key)) {
            mHeader.put(key, value);
        }
    }

    Uploader(String url, Class<Resp> cls, Bitmap... bitmap) {
        mUrl = url;
        mRespClass = cls;
        mBitmap = bitmap;
        mResp = new Object[bitmap.length];
        mIsFinish = new boolean[bitmap.length];
    }

    Uploader(String url, Class<Resp> cls,String defaultName, Bitmap... bitmap) {
        this(url, cls, bitmap);
        mDefaultName = defaultName;
    }

    public abstract void starUpload();

    public abstract void stopUpload();

    private boolean isAllFinish(){
        for (boolean b : mIsFinish) {
            if (!b) {
                return false;
            }
        }
        return true;
    }


    public void setCanUpLoadTogether(boolean canUpLoadTogether) {
        mCanUpLoadTogether = canUpLoadTogether;
    }

    /**
     * 后端接口是否支持同时上传多张图片
     */
    boolean mCanUpLoadTogether;

    public interface UpLoadListener{

        void onUpLoad(Object[] resp);
    }

    public void setUploadListener(UpLoadListener uploadListener) {
        mUploadListener = uploadListener;
    }

    private UpLoadListener mUploadListener;

    private Object[] mResp;

    private boolean[] mIsFinish;

    private void cleanFinishMark() {
        for (int i = 0; mIsFinish != null && i < mIsFinish.length; i++) {
            mIsFinish[i] = false;
        }
    }

    final void parseResponse(String jsonResponse,int index) {
        Log.d(TAG, " url : " + mUrl);
        Log.d(TAG, " body : " + Arrays.toString(mBitmap));
        Log.d(TAG, " header : " + mHeader.toString());
        Log.d(TAG, " response : " + jsonResponse);
        mIsFinish[index] = true;
        try {
            Resp obj = new Gson().fromJson(jsonResponse, mRespClass);
            mResp[index] = obj;
        } catch (Exception e) {
            if (mUploadListener != null) {
                mUploadListener.onUpLoad(null);
            }
            cleanFinishMark();
        }
        if (mCanUpLoadTogether || mBitmap.length == 1 || isAllFinish()) {
            if (mUploadListener != null) {
                mUploadListener.onUpLoad(mResp);
            }
            cleanFinishMark();
        }
    }


}
