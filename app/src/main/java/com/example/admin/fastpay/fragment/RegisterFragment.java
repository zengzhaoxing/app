package com.example.admin.fastpay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.admin.fastpay.R;
import com.example.admin.fastpay.model.response.CaptchaResp;
import com.example.admin.fastpay.net.RequestCaller;
import com.example.admin.fastpay.presenter.RegisterPresenter;
import com.example.admin.fastpay.utils.UrlBase;
import com.example.admin.fastpay.view.GetCodeView;
import com.zhaoxing.view.sharpview.SharpEditText;
import com.zxz.www.base.app.BaseFragment;
import com.zxz.www.base.net.request.JsonRequester;
import com.zxz.www.base.utils.StringUtil;
import com.zxz.www.base.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 10714 on 2017/12/26.
 */

public class RegisterFragment extends BaseFragment implements RegisterPresenter.RegisterListener {

    @BindView(R.id.user_phone)
    SharpEditText mUserPhone;
    @BindView(R.id.user_password)
    SharpEditText mUserPassword;
    @BindView(R.id.user_password2)
    SharpEditText mUserPassword2;
    @BindView(R.id.code_et)
    SharpEditText mCodeEt;
    @BindView(R.id.get_code_tv)
    GetCodeView mGetCodeView;
    Unbinder unbinder;
    @BindView(R.id.captcha_et3)
    SharpEditText mCaptchaEt;
    @BindView(R.id.captcha_iv)
    ImageView mCaptchaIv;

    private RegisterPresenter mRegisterPresenter;

    private CaptchaResp mResp;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        unbinder = ButterKnife.bind(this, view);
        mRegisterPresenter = new RegisterPresenter();
        mRegisterPresenter.setRegisterListener(this);
        requestCaptcha();
        return view;
    }

    private void requestCaptcha() {
        RequestCaller.get(UrlBase.CAPTCHA_URL, null, CaptchaResp.class, new JsonRequester.OnResponseListener<CaptchaResp>() {
            @Override
            public void onResponse(CaptchaResp response, int resCode) {
                if (response != null && mCaptchaIv != null) {
                    mResp = response;
                    Glide.with(RegisterFragment.this).load(mResp.getPic()).into(mCaptchaIv);
                }
            }
        });
    }


    @OnClick({R.id.get_code_tv, R.id.user_logins,R.id.captcha_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.get_code_tv:
                if (StringUtil.isValidatePhoneNumber(mUserPhone.getText().toString()) && mCaptchaEt.length() > 0 && mResp != null) {
                    mRegisterPresenter.getCode(mUserPhone.getText().toString(),mCaptchaEt.getText().toString(),mResp.getRand());
                    mGetCodeView.setDisable();
                } else {
                    ToastUtil.toast("手机号码或图像验证码有误");
                }
                break;
            case R.id.user_logins:
                mRegisterPresenter.register(mUserPhone.getText().toString(),
                        mCodeEt.getText().toString(), mUserPassword.getText().toString(), mUserPassword2.getText().toString());
                break;
            case R.id.captcha_iv:
                requestCaptcha();
                break;
        }
    }

    @Override
    public void onRegisterSuccess() {
        ToastUtil.toast("注册成功");
        goBack();
    }

    @Override
    public void onRegisterFail(final String msg) {
        ToastUtil.toast(msg);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
