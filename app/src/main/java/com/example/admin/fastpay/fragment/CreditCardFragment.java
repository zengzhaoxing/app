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
import com.example.admin.fastpay.model.response.Bank;
import com.example.admin.fastpay.model.response.CaptchaResp;
import com.example.admin.fastpay.net.RequestCaller;
import com.example.admin.fastpay.presenter.CreditCardPresenter;
import com.example.admin.fastpay.presenter.ListDateManager;
import com.example.admin.fastpay.utils.UrlBase;
import com.example.admin.fastpay.view.GetCodeView;
import com.example.admin.fastpay.view.UpLoadView;
import com.zhaoxing.view.sharpview.SharpEditText;
import com.zxz.www.base.app.BaseFragment;
import com.zxz.www.base.net.request.JsonRequester;
import com.zxz.www.base.utils.StringUtil;
import com.zxz.www.base.utils.ToastUtil;
import com.zxz.www.base.view.corner.ProgressButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 曾宪梓 on 2018/1/5.
 */

public class CreditCardFragment extends BaseFragment implements CreditCardPresenter.BindCardListener{

    @BindView(R.id.card_no_et)
    EditText mCardNoEt;
    @BindView(R.id.bank_name_et)
    EditText mBankNameEt;
    @BindView(R.id.bank_photo_ulv)
    UpLoadView mBankPhotoUlv;
    @BindView(R.id.bank_cvv_et)
    EditText mBankCvvEt;
    @BindView(R.id.bank_phone_et)
    EditText mBankPhoneEt;
    @BindView(R.id.code_et)
    EditText mCodeEt;
    @BindView(R.id.date_et)
    EditText mDateEt;
    @BindView(R.id.ok_btn)
    ProgressButton mOkBtn;
    @BindView(R.id.get_code_tv)
    GetCodeView mGetCodeView;
    @BindView(R.id.captcha_et2)
    SharpEditText mCaptchaEt;
    @BindView(R.id.captcha_iv)
    ImageView mCaptchaIv;
    Unbinder unbinder;

    CreditCardPresenter mBindCardPresenter = new CreditCardPresenter();

    private CaptchaResp mResp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_credit_card, container, false);
        unbinder = ButterKnife.bind(this, view);
        mBindCardPresenter.setBindCardListener(this);
        mBankNameEt.setFocusable(false);
        requestCaptcha();
        return view;
    }

    private void requestCaptcha() {
        RequestCaller.get(UrlBase.CAPTCHA_URL, null, CaptchaResp.class, new JsonRequester.OnResponseListener<CaptchaResp>() {
            @Override
            public void onResponse(CaptchaResp response, int resCode) {
                if (response != null && mCaptchaIv != null) {
                    mResp = response;
                    Glide.with(CreditCardFragment.this).load(mResp.getPic()).into(mCaptchaIv);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.get_code_tv, R.id.ok_btn,R.id.bank_name_et,R.id.date_et,R.id.captcha_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.get_code_tv:
                if (StringUtil.isValidatePhoneNumber(mBankPhoneEt.getText().toString()) && mCaptchaEt.length() > 0) {
                    mGetCodeView.setDisable();
                    mBindCardPresenter.getCode(mBankPhoneEt.getText().toString(),mCaptchaEt.getText().toString(),mResp.getRand());
                } else {
                    ToastUtil.toast("手机号码或图像验证码有误");
                }
                break;
            case R.id.ok_btn:
                mOkBtn.setLoading(true);
                mBindCardPresenter.setBandPhoto(mBankPhotoUlv.getBitmap());
                mBindCardPresenter.bind(mCodeEt.getText().toString(),mBankPhoneEt.getText().toString(),
                        mCardNoEt.getText().toString(),mBankCvvEt.getText().toString(),mDateEt.getText().toString());
                break;
            case R.id.bank_name_et:
                ListDateManager.showBankList(mBaseActivity, new ListDateManager.OnBankSelectListener() {
                    @Override
                    public void onSelect(Bank bank) {
                        mBindCardPresenter.setBank(bank);
                        mBankNameEt.setText(bank.getName());
                    }
                });
                break;
            case R.id.captcha_iv:
                requestCaptcha();
                break;
        }
    }

    @Override
    public void onBindCard(boolean isSuccess, String msg) {
        if (mOkBtn != null) {
            ToastUtil.toast(msg);
            mOkBtn.setLoading(false);
            if (isSuccess) {
                goBack();
            }
        }
    }

}
