package com.example.admin.fastpay.presenter;

import com.example.admin.fastpay.constant.SPConstant;
import com.example.admin.fastpay.model.AuthInfo;
import com.example.admin.fastpay.model.CreditCard;
import com.example.admin.fastpay.model.ShopInfo;
import com.example.admin.fastpay.model.StoreInfo;
import com.example.admin.fastpay.model.TokenModel;
import com.example.admin.fastpay.model.response.CreditCardList;
import com.example.admin.fastpay.thirdsdk.KeFuSDKConnector;
import com.example.admin.fastpay.utils.UrlBase;
import com.zxz.www.base.net.request.JsonRequester;
import com.zxz.www.base.net.request.RequesterFactory;
import com.zxz.www.base.utils.SPUtil;
import com.zxz.www.base.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 曾宪梓 on 2017/12/30.
 */

public class UserInfoManager {

    public static boolean isLogin() {
        return !StringUtil.isEmpty((String) SPUtil.get(SPConstant.TOKEN, ""));
    }

    public static String getUserName() {
        return (String) SPUtil.get(SPConstant.USER_NAME, "");
    }

    public static String getToken() {
        return (String) SPUtil.get(SPConstant.TOKEN, "");
    }

    public static String getPassword() {
        return (String) SPUtil.get(SPConstant.PASSWORD, "");
    }

    public static boolean isAuth() {
        return mAuthInfo != null && mAuthInfo.isStatus();
    }

    public static AuthInfo getAuthInfo() {
        if (mAuthInfo == null) {
            mAuthInfo = new AuthInfo();
        }
        return mAuthInfo;
    }

    private static AuthInfo mAuthInfo;

    public static ShopInfo getShopInfo() {
        if (mShopInfo == null) {
            mShopInfo = new ShopInfo();
        }
        return mShopInfo;
    }

    private static ShopInfo mShopInfo;

    public static void requestAuthInfo() {
        JsonRequester jsonRequester = RequesterFactory.getDefaultRequesterFactory().createGetRequester(UrlBase.AUTH_URL, null, AuthInfo.class);
        jsonRequester.addHeader("Authorization","Bearer " + UserInfoManager.getToken());
        jsonRequester.setListener(new JsonRequester.OnResponseListener<AuthInfo>() {
            @Override
            public void onResponse(AuthInfo response, int resCode) {
                mAuthInfo = response;
            }
        });
        jsonRequester.startRequest();
    }

    static void requestShopInfo() {
        JsonRequester jsonRequester = RequesterFactory.getDefaultRequesterFactory().createPostRequester(UrlBase.SHOP_URL, null, ShopInfo.class);
        jsonRequester.addHeader("Authorization","Bearer " + UserInfoManager.getToken());
        jsonRequester.setListener(new JsonRequester.OnResponseListener<ShopInfo>() {
            @Override
            public void onResponse(ShopInfo response, int resCode) {
                mShopInfo = response;
            }
        });
        jsonRequester.startRequest();
    }

    public static String getStoreId() {
        if (mStoreInfo == null || mStoreInfo.getData() == null || mStoreInfo.getData().size() == 0) {
            return null;
        } else {
            return mStoreInfo.getData().get(0).getStore_id();
        }
    }

    public static String getStoreName() {
        if (mStoreInfo == null || mStoreInfo.getData() == null || mStoreInfo.getData().size() == 0) {
            return null;
        } else {
            return mStoreInfo.getData().get(0).getStore_name();
        }
    }

    public static StoreInfo getStoreInfo() {
        if (mStoreInfo == null) {
            mStoreInfo = new StoreInfo();
        }
        return mStoreInfo;
    }

    private static StoreInfo mStoreInfo;

    static void requestStoreInfo() {
        TokenModel tokenModel = new TokenModel();
        JsonRequester jsonRequester = RequesterFactory.getDefaultRequesterFactory().createPostRequester(UrlBase.MENDIAN, tokenModel, StoreInfo.class);
        jsonRequester.setListener(new JsonRequester.OnResponseListener<StoreInfo>() {
            @Override
            public void onResponse(StoreInfo response, int resCode) {
                mStoreInfo = response;
            }
        });
        jsonRequester.startRequest();
    }

    public static void logout() {
        SPUtil.remove(SPConstant.TOKEN);
        SPUtil.remove(SPConstant.PASSWORD);
        mAuthInfo = null;
        mShopInfo = null;
        ListDateManager.clearList();
        setVideoOpen(false);
        KeFuSDKConnector.getInstance().logout();
    }

    public static boolean isVideoOpen() {
        return (boolean) SPUtil.get(SPConstant.VIDEO_OPEN, false);
    }

    public static void setVideoOpen(boolean open) {
        SPUtil.get(SPConstant.VIDEO_OPEN, open);
    }



}
