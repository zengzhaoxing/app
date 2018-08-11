package com.example.admin.fastpay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.fastpay.R;
import com.example.admin.fastpay.presenter.UserInfoManager;
import com.zhaoxing.view.sharpview.SharpTextView;
import com.zxz.www.base.app.BaseActivity;
import com.zxz.www.base.app.BaseFragment;
import com.zxz.www.base.utils.ViewUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by 曾宪梓 on 2017/12/31.
 */

public class CertificationFragment extends BaseFragment {

    @BindView(R.id.merchants_btn)
    SharpTextView mMerchantsBtn;
    @BindView(R.id.name_btn)
    SharpTextView mNameBtn;
    @BindView(R.id.bind_btn)
    SharpTextView mBindBtn;
    @BindView(R.id.add_card_btn)
    SharpTextView mAddCardBtn;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_certification, container, false);
        unbinder = ButterKnife.bind(this, view);
        mBaseActivity = (BaseActivity) getActivity();
        mNameBtn.setText(UserInfoManager.isAuth() ? R.string.certified : R.string.add);
        mBindBtn.setText(UserInfoManager.isAuth() ? R.string.certified : R.string.add);
        mMerchantsBtn.setText(R.string.see);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.merchants_btn, R.id.name_btn, R.id.bind_btn, R.id.add_card_btn})
    public void onViewClicked(View view) {
        if (ViewUtil.isDoubleRequest(view)) {
            return;
        }
        switch (view.getId()) {
            case R.id.merchants_btn:
                StringBuffer s = new StringBuffer("dddd");
                Log.i("zxz", s.toString());
                ShopInfoFragment shopInfoFragment = new ShopInfoFragment();
                mBaseActivity.openNewFragmentWithAnim(shopInfoFragment,R.animator.enter_rotation,R.animator.exit_rotation);
                break;
            case R.id.name_btn:
             if (!UserInfoManager.isAuth()) {
                NameCertFragment fragment = new NameCertFragment();
                mBaseActivity.openNewFragmentWithDefaultAnim(fragment);
            }
                break;
            case R.id.bind_btn:
                if (!UserInfoManager.isAuth()) {
                    BindCardFragment bindCardFragment = new BindCardFragment();
                    mBaseActivity.openNewFragmentWithDefaultAnim(bindCardFragment);
                }
                break;
            case R.id.add_card_btn:
                CardListFragment.showFragment(mBaseActivity,false);
                break;
        }
    }
}
