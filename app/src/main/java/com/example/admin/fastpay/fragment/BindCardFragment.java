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
import com.example.admin.fastpay.model.AuthInfo;
import com.example.admin.fastpay.model.response.Bank;
import com.example.admin.fastpay.model.response.CaptchaResp;
import com.example.admin.fastpay.model.response.City;
import com.example.admin.fastpay.model.response.Province;
import com.example.admin.fastpay.net.RequestCaller;
import com.example.admin.fastpay.presenter.BindCardPresenter;
import com.example.admin.fastpay.presenter.ListDateManager;
import com.example.admin.fastpay.presenter.UserInfoManager;
import com.example.admin.fastpay.utils.UrlBase;
import com.example.admin.fastpay.view.GetCodeView;
import com.example.admin.fastpay.view.UpLoadView;
import com.zhaoxing.view.sharpview.SharpEditText;
import com.zxz.www.base.app.BaseFragment;
import com.zxz.www.base.net.request.JsonRequester;
import com.zxz.www.base.utils.StringUtil;
import com.zxz.www.base.utils.ToastUtil;
import com.zxz.www.base.utils.ViewUtil;
import com.zxz.www.base.view.corner.ProgressButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 曾宪梓 on 2018/1/1.
 */

public class BindCardFragment extends BaseFragment implements BindCardPresenter.BindCardListener {

    @BindView(R.id.card_no_et)
    EditText mCardNoEt;
    @BindView(R.id.bank_name_et)
    EditText mBankNameEt;
    @BindView(R.id.bank_photo_ulv)
    UpLoadView mBankPhotoUlv;
    @BindView(R.id.bank_location_et)
    EditText mBankLocationEt;
    @BindView(R.id.bank_phone_et)
    EditText mBankPhoneEt;
    @BindView(R.id.code_et)
    EditText mCodeEt;
    @BindView(R.id.ok_btn)
    ProgressButton mOkBtn;
    @BindView(R.id.get_code_tv)
    GetCodeView mGetCodeView;
    @BindView(R.id.captcha_et1)
    SharpEditText mCaptchaEt;
    @BindView(R.id.captcha_iv)
    ImageView mCaptchaIv;
    Unbinder unbinder;

    BindCardPresenter mBindCardPresenter = new BindCardPresenter();

    AuthInfo mAuthInfo = UserInfoManager.getAuthInfo();

    private CaptchaResp mResp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bind_card, container, false);
        unbinder = ButterKnife.bind(this, view);
        mBindCardPresenter.setBindCardListener(this);
        mBankNameEt.setFocusable(false);
        mBankLocationEt.setFocusable(false);
        if (mAuthInfo != null) {
            mBankNameEt.setText(mAuthInfo.getBank_name());
            mCardNoEt.setText(mAuthInfo.getBank_card());
            mBankLocationEt.setText(mAuthInfo.getProvince_name() + mAuthInfo.getCity_name());
            mBankPhoneEt.setText(mAuthInfo.getMobile());
            mBindCardPresenter.setProvince(mAuthInfo.getProvince());
            mBindCardPresenter.setCity(mAuthInfo.getCity());
            mBindCardPresenter.setBank(mAuthInfo.getBank());
            if (mAuthInfo.getBank_pic() != null) {
                mBankPhotoUlv.setImageUrl(mAuthInfo.getBank_pic());
                mBindCardPresenter.setPic(mAuthInfo.getBank_pic());
            }
        }
        requestCaptcha();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        UserInfoManager.requestAuthInfo();
        unbinder.unbind();
    }

    @OnClick({R.id.get_code_tv, R.id.ok_btn,R.id.bank_name_et,R.id.bank_location_et, R.id.clear,R.id.captcha_iv})
    public void onViewClicked(View view) {
        if (ViewUtil.isDoubleRequest(view)) {
            return;
        }
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
                mBindCardPresenter.bind(mCodeEt.getText().toString(),mBankPhoneEt.getText().toString(),mCardNoEt.getText().toString());
                break;
            case R.id.bank_location_et:
                ListDateManager.showProvinceList(mBaseActivity, new ListDateManager.OnProvinceSelectListener() {
                    @Override
                    public void onSelect(final Province province) {
                        mBindCardPresenter.setProvince(province);
                        ListDateManager.showCityList(mBaseActivity, new ListDateManager.OnCitySelectListener() {
                            @Override
                            public void onSelect(City city) {
                                mBindCardPresenter.setCity(city);
                                mBankLocationEt.setText(province.getName() + city.getName());
                            }
                        },province);
                    }
                });
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
            case R.id.clear:
                mBankPhotoUlv.setImageBitmap(null);
                mBindCardPresenter.setPic(null);
                mBindCardPresenter.setBandPhoto(null);
                break;
            case R.id.captcha_iv:
                requestCaptcha();
                break;
        }
    }

    private void requestCaptcha() {
        RequestCaller.get(UrlBase.CAPTCHA_URL, null, CaptchaResp.class, new JsonRequester.OnResponseListener<CaptchaResp>() {
            @Override
            public void onResponse(CaptchaResp response, int resCode) {
                if (response != null && mCaptchaIv != null) {
                    mResp = response;
                    Glide.with(BindCardFragment.this).load(mResp.getPic()).into(mCaptchaIv);
                }
            }
        });
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
