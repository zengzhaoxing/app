package com.example.admin.fastpay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.fastpay.R;
import com.example.admin.fastpay.constant.SPConstant;
import com.example.admin.fastpay.presenter.LoginPresenter;
import com.zxz.www.base.app.BaseFragment;
import com.zxz.www.base.utils.SPUtil;
import com.zxz.www.base.utils.ToastUtil;
import com.zxz.www.base.utils.ViewUtil;
import com.zxz.www.base.view.corner.ProgressButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LoginFragment extends BaseFragment implements LoginPresenter.OnLoginListener {

    LoginPresenter mLoginPresenter;
    @BindView(R.id.imageView)
    ImageView mImageView;
    @BindView(R.id.user_phone_et)
    EditText mUserPhoneEt;
    @BindView(R.id.user_password)
    EditText mUserPassword;
    @BindView(R.id.user_logins)
    ProgressButton mUserLogins;
    @BindView(R.id.forget_pw_tv)
    TextView mForgetPwTv;
    @BindView(R.id.register_tv)
    TextView mRegisterTv;
    @BindView(R.id.checkbox1)
    CheckBox mCheckbox1;
    @BindView(R.id.mianze)
    TextView mMianze;
    @BindView(R.id.checkbox2)
    CheckBox mCheckbox2;
    @BindView(R.id.shoukuan)
    TextView mShoukuan;
    @BindView(R.id.checkbox3)
    CheckBox mCheckbox3;
    @BindView(R.id.xiaofei)
    TextView mXiaofei;
    butterknife.Unbinder unbinder;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = butterknife.ButterKnife.bind(this, view);
        mLoginPresenter = new LoginPresenter();
        mLoginPresenter.setOnLoginListener(this);
        mUserPhoneEt.setText((String) SPUtil.get(SPConstant.USER_NAME, ""));

        return view;
    }

    @Override
    public void onLoginSuccess() {

        if (mUserLogins != null) {
            mUserLogins.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mUserLogins != null) {
                        mUserLogins.setLoading(false);
                    }
                    goBack();
                }
            }, 1000);
        }
    }

    @Override
    public void onLoginFail(final String msg) {
        if (mUserLogins != null) {
            mUserLogins.setLoading(false);
        }
        ToastUtil.toast(msg);
    }

    @OnClick({R.id.user_logins, R.id.register_tv, R.id.forget_pw_tv})
    public void onViewClicked(View view) {
        if (ViewUtil.isDoubleRequest(view)) {
            return;
        }
        switch (view.getId()) {
            case R.id.user_logins:
//                View v = null;
//                v.getId();
                mUserLogins.setLoading(true);
                mLoginPresenter.login(mUserPhoneEt.getText().toString().trim(), mUserPassword.getText().toString().trim());
                break;
            case R.id.register_tv:
                mBaseActivity.openNewFragmentWithDefaultAnim(new RegisterFragment(), this);
                break;
            case R.id.forget_pw_tv:
                H5Fragment.showH5Fragment(mBaseActivity, "忘记密码", "https://www.miaodaochina.cn/merchant/setPassword");
                break;
        }
    }

    @Override
    protected boolean handleBackEvent() {
        mBaseActivity.finish();
        return true;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

