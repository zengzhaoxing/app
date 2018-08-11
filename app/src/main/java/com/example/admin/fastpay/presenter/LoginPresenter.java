package com.example.admin.fastpay.presenter;

import com.appkefu.lib.interfaces.KFAPIs;
import com.example.admin.fastpay.constant.SPConstant;
import com.example.admin.fastpay.model.request.LoginRequestModel;
import com.example.admin.fastpay.model.TokenModel;
import com.example.admin.fastpay.thirdsdk.KeFuSDKConnector;
import com.example.admin.fastpay.utils.UrlBase;
import com.zxz.www.base.net.request.JsonRequester;
import com.zxz.www.base.net.request.RequesterFactory;
import com.zxz.www.base.utils.SPUtil;
import com.zxz.www.base.utils.StringUtil;

/**
 * Created by 10714 on 2017/12/25.
 */

public class LoginPresenter {

    JsonRequester mJsonRequester;

    public void login(final String accountId, final String password) {
//        if (StringUtil.isBlank(accountId)) {
//            doLoginFail("请输入账号");
//            return;
//        }
//        SPUtil.put(SPConstant.USER_NAME,accountId);
//        if (StringUtil.isBlank(password)) {
//            doLoginFail("请输入密码");
//            return;
//        }
        SPUtil.put(SPConstant.USER_NAME,accountId);
        LoginRequestModel loginRequestModel = new LoginRequestModel();
        loginRequestModel.setEmail(accountId);
        loginRequestModel.setPassword(password);
        mJsonRequester = RequesterFactory.getDefaultRequesterFactory().createPostRequester(UrlBase.LOGINURL, loginRequestModel, TokenModel.class);
        mJsonRequester.setListener(new JsonRequester.OnResponseListener<TokenModel>() {
            @Override
            public void onResponse(TokenModel response, int resCode) {
                switch (resCode) {
                    case 200:
                        if (StringUtil.isBlank(response.getToken())) {
                            doLoginFail("验证错误");
                        } else {
                            SPUtil.put(SPConstant.TOKEN,response.getToken());
                            SPUtil.put(SPConstant.PASSWORD,password);
                            doLoginSuccess();
                        }
                        break;
                    case 401:
                        doLoginFail("用户名密码错误");
                        break;
                    case 404:
                        doLoginFail("用户不存在");
                        break;
                    case 500:
                        doLoginFail("用户名密码错误");
                        break;
                    case 422:
                        doLoginFail("验证错误");
                        default:
                            doLoginFail("用户名密码错误");
                }
            }
        });
        mJsonRequester.startRequest();
    }

    public interface OnLoginListener {
        void onLoginSuccess();

        void onLoginFail(String msg);
    }

    public void setOnLoginListener(OnLoginListener onLoginListener) {
        mOnLoginListener = onLoginListener;
    }

    private OnLoginListener mOnLoginListener;

    private void doLoginFail(String msg) {
        SPUtil.remove(SPConstant.TOKEN);
        SPUtil.remove(SPConstant.PASSWORD);
        if (mOnLoginListener != null) {
            mOnLoginListener.onLoginFail(msg);
        }
    }

    private void doLoginSuccess() {
        UserInfoManager.requestShopInfo();
        UserInfoManager.requestStoreInfo();
        UserInfoManager.requestAuthInfo();
        ListDateManager.requestBankList();
        ListDateManager.requestLocation();
        if (mOnLoginListener != null) {
            mOnLoginListener.onLoginSuccess();
        }
        KeFuSDKConnector.getInstance().login(UserInfoManager.getUserName());
    }



}
