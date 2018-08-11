package com.example.admin.fastpay.presenter;

import android.graphics.Bitmap;

import com.example.admin.fastpay.R;
import com.example.admin.fastpay.model.ShopInfo;
import com.example.admin.fastpay.model.response.RespModel;
import com.example.admin.fastpay.model.response.UpLoadResp;
import com.example.admin.fastpay.net.RequestCaller;
import com.example.admin.fastpay.utils.UrlBase;
import com.zxz.www.base.net.request.JsonRequester;
import com.zxz.www.base.net.request.RequesterFactory;
import com.zxz.www.base.net.upload.Uploader;
import com.zxz.www.base.utils.ResUtil;
import com.zxz.www.base.utils.StringUtil;

import java.util.List;

/**
 * Created by 曾宪梓 on 2017/12/31.
 */

public class ShopPresenter implements JsonRequester.OnResponseListener<RespModel> {

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

    private ShopInfo mShopInfo;

    public void setProvince(String province) {
        mProvince = province;
    }

    private String mProvince;

    public void setCity(String city) {
        mCity = city;
    }

    private String mCity;

    private boolean isRequest = false;

    public void submit(String shipName, String shopCode, String type, String address) {
        if (StringUtil.isBlank(shipName)) {
            doResult(false, ResUtil.getString(R.string.shop_name_hint));
        } /*else if (StringUtil.isBlank(shopCode)) {
            doResult(false, ResUtil.getString(R.string.shop_code_hint));
        }  */ /*else if (StringUtil.isBlank(type)) {
            doResult(false, ResUtil.getString(R.string.shop_type_hint));
        }  */ else if (StringUtil.isBlank(address)) {
            doResult(false, ResUtil.getString(R.string.shop_address_hint));
        }   else if (mCity == null || mProvince == null) {
            doResult(false, ResUtil.getString(R.string.location_hint));
        }   else if ((mHalf == null || mFront == null || mBack == null) && mPic == null) {
            doResult(false, ResUtil.getString(R.string.shop_photo_hint));
        }  else {
            isRequest = false;
            mShopInfo = new ShopInfo();
            mShopInfo.setShop_name(shipName);
            mShopInfo.setStore_id(shopCode);
            mShopInfo.setPhone(UserInfoManager.getUserName());
            mShopInfo.setType(type);
            mShopInfo.setAddress(address);
            mShopInfo.setProvince(mProvince);
            mShopInfo.setCity(mCity);
            if (mPic == null) {
                upLoadCardPicture();
            }
            request();
        }
    }

    private void request() {
        if (mPic != null && !isRequest) {
            mShopInfo.setShop_pic(mPic);
            JsonRequester jsonRequester = RequesterFactory.getDefaultRequesterFactory().createPostRequester(UrlBase.SHOP_URL, mShopInfo, RespModel.class);
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
                if (resp != null && resp.length == 3) {
                    mPic = ((UpLoadResp)resp[0]).getImg_url() + "," + ((UpLoadResp)resp[1]).getImg_url() + "," + ((UpLoadResp)resp[2]).getImg_url();
                }
                request();
            }
        },mHalf,mFront,mBack);
    }

    public void setShopListener(ShopListener shopListener) {
        mShopListener = shopListener;
    }

    private ShopListener mShopListener;

    @Override
    public void onResponse(RespModel response, int resCode) {
        if (response == null) {
            doResult(false,"提交失败");
        } else {
            doResult(response.isSuccess(), response.message);
        }
    }

    public interface ShopListener {
        void onSubmit(boolean isSuccess, String msg);
    }

    private void doResult(boolean isSuccess,String msg) {
        if (isSuccess) {
            ShopInfo info = UserInfoManager.getShopInfo();
            info.setShop_name(mShopInfo.getShop_name());
            info.setStore_id(mShopInfo.getStore_id());
            info.setPhone(UserInfoManager.getUserName());
            info.setType(mShopInfo.getType());
            info.setAddress(mShopInfo.getAddress());
            info.setProvince(mProvince);
            info.setCity(mCity);
        }
        if (mShopListener != null) {
            mShopListener.onSubmit(isSuccess,msg);
        }
    }

}
