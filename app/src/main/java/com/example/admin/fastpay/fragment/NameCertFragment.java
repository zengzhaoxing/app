package com.example.admin.fastpay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.admin.fastpay.R;
import com.example.admin.fastpay.model.AuthInfo;
import com.example.admin.fastpay.presenter.AuthPresenter;
import com.example.admin.fastpay.presenter.UserInfoManager;
import com.example.admin.fastpay.utils.DialogUtil;
import com.example.admin.fastpay.view.UpLoadView;
import com.zxz.www.base.app.BaseFragment;
import com.zxz.www.base.utils.ToastUtil;
import com.zxz.www.base.utils.ViewUtil;
import com.zxz.www.base.view.corner.ProgressButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 曾宪梓 on 2017/12/31.
 */

public class NameCertFragment extends BaseFragment implements AuthPresenter.AuthListener {
    @BindView(R.id.name_et)
    EditText mNameEt;
    @BindView(R.id.card_no_et)
    EditText mCardNoEt;
    @BindView(R.id.card_date_et)
    EditText mCardDateEt;
    @BindView(R.id.card_organ_et)
    EditText mCardOrganEt;
    @BindView(R.id.half_ulv)
    UpLoadView mHalfUlv;
    @BindView(R.id.front_ulv)
    UpLoadView mFrontUlv;
    @BindView(R.id.back_ulv)
    UpLoadView mBackUlv;
    @BindView(R.id.ok_btn)
    ProgressButton mOkBtn;

    Unbinder unbinder;

    AuthPresenter mAuthPresenter = new AuthPresenter();

    AuthInfo mAuthInfo = UserInfoManager.getAuthInfo();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_name_cert, container, false);
        unbinder = ButterKnife.bind(this, view);
        mCardDateEt.setFocusable(false);
        mAuthPresenter.setAuthListener(this);
        if (mAuthInfo != null) {
            mNameEt.setText(mAuthInfo.getAcc_name());
            mCardNoEt.setText(mAuthInfo.getId_card());
            mCardDateEt.setText(mAuthInfo.getEffective_date());
            mCardOrganEt.setText(mAuthInfo.getLicence_organ());
            if (mAuthInfo.getId_card_pic() != null) {
                String[] urls = mAuthInfo.getId_card_pic().split(",");
                if (urls != null && urls.length == 3) {
                    mAuthPresenter.setPic(mAuthInfo.getId_card_pic());
                    mHalfUlv.setImageUrl(urls[0]);
                    mFrontUlv.setImageUrl(urls[1]);
                    mBackUlv.setImageUrl(urls[2]);
                }
            }
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        UserInfoManager.requestAuthInfo();
    }

    @OnClick({R.id.ok_btn,R.id.card_date_et, R.id.clear})
    public void onViewClicked(View view) {
        if (ViewUtil.isDoubleRequest(view)) {
            return;
        }
        switch (view.getId()) {
            case R.id.ok_btn:
                mOkBtn.setLoading(true);
                mAuthPresenter.setHalf(mHalfUlv.getBitmap());
                mAuthPresenter.setFront(mFrontUlv.getBitmap());
                mAuthPresenter.setBack(mBackUlv.getBitmap());
                mAuthPresenter.auth(mCardNoEt.getText().toString(),mNameEt.getText().toString(),mCardOrganEt.getText().toString(),mCardDateEt.getText().toString());
                break;
            case R.id.card_date_et:
                DialogUtil.pickExpiry(mBaseActivity, new DialogUtil.OnDatePicker() {
                    @Override
                    public void onPicker(String date) {
                        if (mCardDateEt != null) {
                            mCardDateEt.setText(date);
                        }
                    }
                });
                break;
            case R.id.clear:
                mHalfUlv.setImageBitmap(null);
                mFrontUlv.setImageBitmap(null);
                mBackUlv.setImageBitmap(null);
                mAuthPresenter.setPic(null);
                mAuthPresenter.setBack(null);
                mAuthPresenter.setFront(null);
                mAuthPresenter.setHalf(null);
                break;
        }
    }

    @Override
    public void onAuth(boolean isSuccess,String msg) {
        if (mOkBtn != null) {
            mOkBtn.setLoading(false);
        }
        ToastUtil.toast(msg);
        if (isSuccess) {
            goBack();
        }
    }
}
