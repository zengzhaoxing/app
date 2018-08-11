package com.example.admin.fastpay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.admin.fastpay.R;
import com.example.admin.fastpay.constant.SPConstant;
import com.example.admin.fastpay.model.response.RemarkResp;
import com.example.admin.fastpay.net.RequestCaller;
import com.example.admin.fastpay.presenter.LoginPresenter;
import com.example.admin.fastpay.utils.UrlBase;
import com.zxz.www.base.app.BaseFragment;
import com.zxz.www.base.net.request.JsonRequester;
import com.zxz.www.base.utils.SPUtil;
import com.zxz.www.base.utils.ToastUtil;
import com.zxz.www.base.utils.ViewUtil;
import com.zxz.www.base.view.corner.ProgressButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LoginFragment extends BaseFragment implements LoginPresenter.OnLoginListener {
    @BindView(R.id.user_phone)
    EditText mUserPhone;
    @BindView(R.id.user_password)
    EditText mUserPassword;
    @BindView(R.id.user_logins)
    ProgressButton mUserLogin;

    LoginPresenter mLoginPresenter;

    Unbinder unbinder;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        mLoginPresenter = new LoginPresenter();
        mLoginPresenter.setOnLoginListener(this);
        mUserPhone.setText((String) SPUtil.get(SPConstant.USER_NAME, ""));
        return view;
    }

    @Override
    public void onLoginSuccess() {

        if (mUserLogin != null) {
            mUserLogin.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mUserLogin != null) {
                        mUserLogin.setLoading(false);
                    }
                    goBack();
                }
            }, 1000);
        }
    }

    @Override
    public void onLoginFail(final String msg) {
        if (mUserLogin != null) {
            mUserLogin.setLoading(false);
        }
        ToastUtil.toast(msg);
    }

    @OnClick({R.id.user_logins, R.id.register_tv,R.id.forget_pw_tv})
    public void onViewClicked(View view) {
        if (ViewUtil.isDoubleRequest(view)) {
            return;
        }
        switch (view.getId()) {
            case R.id.user_logins:
//                View v = null;
//                v.getId();
                mUserLogin.setLoading(true);
                mLoginPresenter.login(mUserPhone.getText().toString().trim(), mUserPassword.getText().toString().trim());
                break;
            case R.id.register_tv:
                mBaseActivity.openNewFragmentWithDefaultAnim(new RegisterFragment(),this);
                break;
            case R.id.forget_pw_tv:
                H5Fragment.showH5Fragment(mBaseActivity,"忘记密码","https://www.miaodaochina.cn/merchant/setPassword");
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected boolean handleBackEvent() {
        mBaseActivity.finish();
        return true;
    }
}

