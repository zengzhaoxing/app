package com.example.admin.fastpay.presenter;

import android.graphics.Bitmap;

import com.example.admin.fastpay.model.AuthInfo;
import com.example.admin.fastpay.model.response.RespModel;
import com.example.admin.fastpay.model.response.UpLoadResp;
import com.example.admin.fastpay.net.RequestCaller;
import com.example.admin.fastpay.utils.UrlBase;
import com.zxz.www.base.net.request.JsonRequester;
import com.zxz.www.base.net.request.RequesterFactory;
import com.zxz.www.base.net.upload.Uploader;
import com.zxz.www.base.utils.StringUtil;

import java.util.List;

/**
 * Created by 曾宪梓 on 2018/1/3.
 */

public class AuthPresenter implements JsonRequester.OnResponseListener<RespModel> {

    public void setHalf(Bitmap half) {
        mHalf = half;
    }

    public void setFront(Bitmap front) {
        mFront = front;
    }

    public void setBack(Bitmap back) {
        mBack = back;
    }

    private Bitmap mHalf;

    private Bitmap mFront;

    private Bitmap mBack;

    public void setPic(String pic) {
        mPic = pic;
    }

    private String mPic;

    private AuthInfo mAuthReqMod;

    public void auth(String cardNo, String name, String organ, String date) {
        if (!StringUtil.isIdCardNo(cardNo)) {
            doResult(false, "身份证号不合法");
        } else if ((mHalf == null || mFront == null || mBack == null) && mPic== null) {
            doResult(false, "请选择身份证照");
        } else {
            isRequest = false;
            mAuthReqMod = new AuthInfo();
            mAuthReqMod.setId_card(cardNo);
            mAuthReqMod.setAcc_name(name);
            mAuthReqMod.setLicence_organ(organ);
            mAuthReqMod.setEffective_date(date);
            if (mPic == null) {
                upLoadCardPicture();
            }
            request();
        }
    }

    private boolean isRequest;

    private void request() {
        if (mPic != null && !isRequest) {
            mAuthReqMod.setId_card_pic(mPic);
            JsonRequester jsonRequester = RequesterFactory.getDefaultRequesterFactory().createPostRequester(UrlBase.AUTH_URL, mAuthReqMod, RespModel.class);
            jsonRequester.addHeader("Authorization","Bearer " + UserInfoManager.getToken());
            jsonRequester.setListener(this);
            jsonRequester.startRequest();
            isRequest = true;
        }
    }

    private void upLoadCardPicture() {
        RequestCaller.upload(UrlBase.UP_LOAD_FILE, new Uploader.UpLoadListener() {
            @Override
            public void onUpLoad(Object[] resp) {
                if (resp != null && resp.length == 3 && resp[0] != null && resp[1] != null && resp[2] != null) {
                    mPic = ((UpLoadResp)resp[0]).getImg_url() + "," + ((UpLoadResp)resp[1]).getImg_url() + "," + ((UpLoadResp)resp[2]).getImg_url();
                }
                request();
            }
        },mHalf,mFront,mBack);
    }


    public void setAuthListener(AuthListener authListener) {
        mAuthListener = authListener;
    }

    private AuthPresenter.AuthListener mAuthListener;

    @Override
    public void onResponse(RespModel response, int resCode) {
        if (response == null) {
            doResult(false,"提交失败");
        } else {
            doResult(response.isSuccess(), response.message);
        }
    }

    public interface AuthListener {
        void onAuth(boolean isSuccess,String msg);
    }

    private void doResult(boolean isSuccess,String msg) {
        if (isSuccess) {
            AuthInfo authInfo = UserInfoManager.getAuthInfo();
            authInfo.setId_card_pic(mAuthReqMod.getId_card_pic());
            authInfo.setAcc_name(mAuthReqMod.getAcc_name());
            authInfo.setLicence_organ(mAuthReqMod.getLicence_organ());
            authInfo.setEffective_date(mAuthReqMod.getEffective_date());
            authInfo.setId_card(mAuthReqMod.getId_card());
        }
        if (mAuthListener != null) {
            mAuthListener.onAuth(isSuccess,msg);
        }
    }

}
