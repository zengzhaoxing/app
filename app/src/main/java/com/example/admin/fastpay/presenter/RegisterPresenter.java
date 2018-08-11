package com.example.admin.fastpay.presenter;

import com.example.admin.fastpay.model.request.GetCodeReq;
import com.example.admin.fastpay.model.request.RegReqMod;
import com.example.admin.fastpay.model.response.GetCodeResponseModel;
import com.example.admin.fastpay.model.response.RegRespMod;
import com.example.admin.fastpay.utils.UrlBase;
import com.zxz.www.base.net.request.JsonRequester;
import com.zxz.www.base.net.request.RequesterFactory;
import com.zxz.www.base.utils.StringUtil;

/**
 * Created by 10714 on 2017/12/26.
 */

public class RegisterPresenter{

    private RequesterFactory mRequesterFactory = RequesterFactory.getDefaultRequesterFactory();

    public void getCode(String phone,String captcha,String rand) {
        if (StringUtil.isBlank(phone)) {
            doRegisterFail("手机号码不能为空");
        } else {
            GetCodeReq model = new GetCodeReq(phone,captcha,rand);
            JsonRequester getCodeRequest = mRequesterFactory.createPostRequester(UrlBase.SEND_CODE, model, GetCodeResponseModel.class);
            getCodeRequest.setListener(new JsonRequester.OnResponseListener<GetCodeResponseModel>() {
                @Override
                public void onResponse(GetCodeResponseModel response, int resCode) {
                    switch (resCode) {
                        case 200:
                            break;
                        case 400:
                        case 422:
                            doRegisterFail("手机号码错误");
                            break;
                            default:
                                doRegisterFail("网络或服务器异常");
                            break;
                    }
                }
            });
            getCodeRequest.startRequest();
        }
    }

    public void register(String phone,String code,String password,String confirmPassword) {
        if (StringUtil.isBlank(phone)) {
            doRegisterFail("手机号码不能为空");
        } else if (StringUtil.isBlank(code)) {
            doRegisterFail("验证码不能为空");
        } else if (StringUtil.isBlank(password) || StringUtil.isBlank(confirmPassword) || password.length() != 6) {
            doRegisterFail("请输入六位数密码");
        } else if (!password.equals(confirmPassword)) {
            doRegisterFail("密码不一致");
        } else {
            RegReqMod mod = new RegReqMod();
            mod.setPhone(phone);
            mod.setCode(code);
            mod.setPassword(password);
            mod.setName(phone);
            JsonRequester registerRequest = mRequesterFactory.createPostRequester(UrlBase.REGISTER_URL, mod, RegRespMod.class);
            registerRequest.setListener(new JsonRequester.OnResponseListener<RegRespMod>() {
                @Override
                public void onResponse(RegRespMod response, int resCode) {
                    switch (resCode) {
                        case 200:
                            doRegisterSuccess();
                            break;
                        case 401:
                            doRegisterFail("验证码失效");
                            break;
                        case 402:
                            doRegisterFail("验证码错误");
                            break;
                        default:
                            doRegisterFail("网络或服务器异常");
                    }
                }
            });
            registerRequest.startRequest();
        }

    }

    public void setRegisterListener(RegisterListener registerListener) {
        mRegisterListener = registerListener;
    }

    private RegisterListener mRegisterListener;

    public interface RegisterListener{
        void onRegisterSuccess();

        void onRegisterFail(String msg);
    }

    private void doRegisterSuccess() {
        if (mRegisterListener != null) {
            mRegisterListener.onRegisterSuccess();
        }
    }

    public void doRegisterFail(String msg) {
        if (mRegisterListener != null) {
            mRegisterListener.onRegisterFail(msg);
        }
    }

}
