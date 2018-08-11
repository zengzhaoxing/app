package com.example.admin.fastpay.presenter;

import android.graphics.Bitmap;

import com.example.admin.fastpay.R;
import com.example.admin.fastpay.model.CreditCard;
import com.example.admin.fastpay.model.request.GetCodeReq;
import com.example.admin.fastpay.model.response.Bank;
import com.example.admin.fastpay.model.response.BindCardResp;
import com.example.admin.fastpay.model.response.GetCodeResponseModel;
import com.example.admin.fastpay.model.response.UpLoadResp;
import com.example.admin.fastpay.net.RequestCaller;
import com.example.admin.fastpay.utils.UrlBase;
import com.zxz.www.base.net.request.JsonRequester;
import com.zxz.www.base.net.request.RequesterFactory;
import com.zxz.www.base.net.upload.Uploader;
import com.zxz.www.base.utils.ResUtil;
import com.zxz.www.base.utils.StringUtil;

/**
 * Created by 曾宪梓 on 2018/1/5.
 */

public class CreditCardPresenter {

    private Bitmap mBitmap;

    public void setBank(Bank bank) {
        mBank = bank;
    }

    private Bank mBank;

    public void setPic(String pic) {
        mPic = pic;
    }

    private String mPic;



    private CreditCard mCreditCard;

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

    public void bind(String code,String phone,String bankCardNo,String  cvv,String date) {
        if (mBank == null) {
            doResult(false, ResUtil.getString(R.string.bank_name_hint));
        } else if(StringUtil.isBlank(bankCardNo)){
            doResult(false, ResUtil.getString(R.string.bank_card_no_hint));
        }else if(StringUtil.isBlank(phone)){
            doResult(false, ResUtil.getString(R.string.bank_phone_hint));
        } else if (StringUtil.isBlank(code)) {
            doResult(false, ResUtil.getString(R.string.code_hint));
        } else if(mBitmap == null && mPic == null){
            doResult(false, ResUtil.getString(R.string.bank_photo_hint));
        } else if(StringUtil.isBlank(cvv)){
            doResult(false, ResUtil.getString(R.string.bank_cvv_hint));
        }else if(StringUtil.isBlank(date)){
            doResult(false, ResUtil.getString(R.string.id_card_date_hint));
        }else{
            isRequest = false;
            mCreditCard = new CreditCard();
            mCreditCard.setBank_card(bankCardNo);
            mCreditCard.setCode(code);
            mCreditCard.setBank_id(mBank.getBank_code());
            mCreditCard.setMobile(phone);
            mCreditCard.setCnn(cvv);
            mCreditCard.setBank_name(mBank.getBank_name());
            mCreditCard.setExpiry_date(date);
            if (mPic == null) {
                upLoadPhoto();
            }
            request();
        }
    }

    public void delete(String cardId) {
        RequestCaller.delete(UrlBase.CREDIT_CARD + "/cardId", null, BindCardResp.class, new JsonRequester.OnResponseListener<BindCardResp>() {
            @Override
            public void onResponse(BindCardResp response, int resCode) {

            }
        });
    }

    private void request() {
        if (mPic != null && !isRequest) {
            mCreditCard.setBank_pic(mPic);
            JsonRequester requester = RequesterFactory.getDefaultRequesterFactory().createPostRequester(UrlBase.CREDIT_CARD, mCreditCard, BindCardResp.class);
            requester.setListener(new JsonRequester.OnResponseListener<BindCardResp>() {
                @Override
                public void onResponse(BindCardResp response, int resCode) {
                    if (response == null) {
                        doResult(false,"提交失败");
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
                    mPic = ((UpLoadResp) resp[0]).getImg_url();
                }
                request();
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

    private void doResult(boolean isSuccess,String msg) {
        if (mBindCardListener != null) {
            mBindCardListener.onBindCard(isSuccess,msg);
        }
    }

}
