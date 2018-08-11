package com.example.admin.fastpay.presenter;

import android.graphics.Bitmap;

import com.example.admin.fastpay.R;
import com.example.admin.fastpay.model.AuthInfo;
import com.example.admin.fastpay.model.request.GetCodeReq;
import com.example.admin.fastpay.model.response.Bank;
import com.example.admin.fastpay.model.response.BindCardResp;
import com.example.admin.fastpay.model.response.City;
import com.example.admin.fastpay.model.response.GetCodeResponseModel;
import com.example.admin.fastpay.model.response.Province;
import com.example.admin.fastpay.model.response.UpLoadResp;
import com.example.admin.fastpay.net.RequestCaller;
import com.example.admin.fastpay.utils.UrlBase;
import com.zxz.www.base.net.request.JsonRequester;
import com.zxz.www.base.net.request.RequesterFactory;
import com.zxz.www.base.net.upload.Uploader;
import com.zxz.www.base.utils.ResUtil;
import com.zxz.www.base.utils.StringUtil;

/**
 * Created by 曾宪梓 on 2018/1/1.
 */

public class BindCardPresenter {

    private Bitmap mBitmap;

    public void setProvince(Province province) {
        mProvince = province;
    }

    public void setCity(City city) {
        mCity = city;
    }

    public void setBank(Bank bank) {
        mBank = bank;
    }

    private Province mProvince;

    private City mCity;

    private Bank mBank;

    public void setPic(String pic) {
        mPic = pic;
    }

    private String mPic;

    private AuthInfo mAuthInfo;

    public void getCode(String phone,String captcha,String rand) {
        GetCodeReq model = new GetCodeReq(phone,captcha,rand);
        JsonRequester getCodeRequest = RequesterFactory.getDefaultRequesterFactory().createPostRequester(UrlBase.SEND_CODE, model, GetCodeResponseModel.class);
        getCodeRequest.setListener(new JsonRequester.OnResponseListener<GetCodeResponseModel>() {
            @Override
            public void onResponse(GetCodeResponseModel response, int resCode) {

            }
        });
        getCodeRequest.startRequest();
    }

    private boolean isRequest;

    public void bind(String code,String phone,String bankCardNo) {
        if (mCity == null || mProvince == null) {
            doResult(false, ResUtil.getString(R.string.location_hint));
        } else if (mBank == null) {
            doResult(false, ResUtil.getString(R.string.bank_name_hint));
        } else if(StringUtil.isBlank(bankCardNo)){
            doResult(false, ResUtil.getString(R.string.bank_card_no_hint));
        }else if(StringUtil.isBlank(phone)){
            doResult(false, ResUtil.getString(R.string.bank_phone_hint));
        } else if (StringUtil.isBlank(code)) {
            doResult(false, ResUtil.getString(R.string.code_hint));
        } else if(mBitmap == null && mPic == null){
            doResult(false, ResUtil.getString(R.string.bank_photo_hint));
        }else {
            isRequest = false;
            mAuthInfo = new AuthInfo();
            mAuthInfo.setBank_card(bankCardNo);
            mAuthInfo.setCode(code);
            mAuthInfo.setBank_code(mBank.getBank_code());
            mAuthInfo.setMobile(phone);
            mAuthInfo.setBank_name(mBank.getBank_name());
            mAuthInfo.setCity_code(mCity.getAreaCode());
            mAuthInfo.setCity_name(mCity.getAreaName());
            mAuthInfo.setProvince_code(mProvince.getAreaCode());
            mAuthInfo.setProvince_name(mProvince.getAreaName());
            if (mPic == null) {
                upLoadPhoto();
            }
            request();
        }
    }

    private void request() {
        if (mPic != null && !isRequest) {
            mAuthInfo.setBank_pic(mPic);
            JsonRequester requester = RequesterFactory.getDefaultRequesterFactory().createPostRequester(UrlBase.BINK_BINDING_URL, mAuthInfo, BindCardResp.class);
            requester.setListener(new JsonRequester.OnResponseListener<BindCardResp>() {
                @Override
                public void onResponse(BindCardResp response, int resCode) {
                    if (response == null) {
                        doResult(false,"绑定失败");
                    } else {
                        doResult(response.isSuccess(), response.getMessage());
                    }
                }
            });
            requester.addHeader("Authorization","Bearer " + UserInfoManager.getToken());
            requester.startRequest();
            isRequest = true;
        }
    }

    private void upLoadPhoto() {
        RequestCaller.upload(UrlBase.UP_LOAD_FILE, new Uploader.UpLoadListener() {
            @Override
            public void onUpLoad(Object[] resp) {
                if (resp != null && resp[0] != null) {
                    mPic = ((UpLoadResp)resp[0]).getImg_url();
                }
                if (mPic != null) {
                    request();
                } else {
                    doResult(false,"图片上传失败");
                }
            }
        },mBitmap);
    }

    public void setBindCardListener(BindCardListener bindCardListener) {
        mBindCardListener = bindCardListener;
    }

    private BindCardListener mBindCardListener;

    public interface BindCardListener {
        void onBindCard(boolean isSuccess,String msg);
    }

    public void setBandPhoto(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    private void doResult(boolean isSuccess, String msg) {
        if (isSuccess) {
            AuthInfo authInfo = UserInfoManager.getAuthInfo();
            authInfo.setBank_card(mAuthInfo.getBank_card());
            authInfo.setBank_code(mAuthInfo.getBank_code());
            authInfo.setMobile(mAuthInfo.getMobile());
            authInfo.setBank_name(mAuthInfo.getBank_name());
            authInfo.setCity_code(mCity.getAreaCode());
            authInfo.setCity_name(mCity.getAreaName());
            authInfo.setProvince_code(mProvince.getAreaCode());
            authInfo.setProvince_name(mProvince.getAreaName());
            authInfo.setBank_pic(mAuthInfo.getBank_pic());
        }
        if (mBindCardListener != null) {
            mBindCardListener.onBindCard(isSuccess,msg);
        }
    }

}
