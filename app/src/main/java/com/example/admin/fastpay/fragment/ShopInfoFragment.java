package com.example.admin.fastpay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.admin.fastpay.R;
import com.example.admin.fastpay.model.ShopInfo;
import com.example.admin.fastpay.model.response.City;
import com.example.admin.fastpay.model.response.Province;
import com.example.admin.fastpay.presenter.ListDateManager;
import com.example.admin.fastpay.presenter.ShopPresenter;
import com.example.admin.fastpay.presenter.UserInfoManager;
import com.example.admin.fastpay.view.UpLoadView;
import com.zxz.www.base.app.BaseFragment;
import com.zxz.www.base.utils.ToastUtil;
import com.zxz.www.base.view.corner.ProgressButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 曾宪梓 on 2018/1/3.
 */

public class ShopInfoFragment extends BaseFragment implements ShopPresenter.ShopListener {

    @BindView(R.id.shop_type_et)
    EditText mShopTypeEt;
    @BindView(R.id.shop_name_et)
    EditText mShopNameEt;
    @BindView(R.id.location_et)
    EditText mLocationEt;
    @BindView(R.id.shop_address_et)
    EditText mShopAddressEt;
    @BindView(R.id.shop_code_et)
    EditText mShopCodeEt;
    @BindView(R.id.half_ulv)
    UpLoadView mHalfUlv;
    @BindView(R.id.front_ulv)
    UpLoadView mFrontUlv;
    @BindView(R.id.back_ulv)
    UpLoadView mBackUlv;
    @BindView(R.id.ok_btn)
    ProgressButton mOkBtn;
    Unbinder unbinder;

    private ShopPresenter mShopPresenter = new ShopPresenter();

    private ShopInfo mShopInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_info, container, false);
        unbinder = ButterKnife.bind(this, view);
        mLocationEt.setFocusable(false);
        mShopPresenter.setShopListener(this);
        mShopInfo = UserInfoManager.getShopInfo();
        if (mShopInfo != null) {
            mShopNameEt.setText(mShopInfo.getShop_name());
            mShopTypeEt.setText(mShopInfo.getType());
            mLocationEt.setText(mShopInfo.getProvince() + mShopInfo.getCity());
            mShopAddressEt.setText(mShopInfo.getAddress());
            mShopCodeEt.setText(mShopInfo.getMerchant_info_id());
            mShopPresenter.setProvince(mShopInfo.getProvince());
            mShopPresenter.setCity(mShopInfo.getCity());
            if (mShopInfo.getShop_pic() != null) {
                if (!mShopInfo.getShop_pic().contains("http")) {
                    mShopInfo.setShop_pic(null);
                } else {
                    String[] urls = mShopInfo.getShop_pic().split(",");
                    if (urls != null && urls.length == 3) {
                        mShopPresenter.setPic(mShopInfo.getShop_pic());
                        mHalfUlv.setImageUrl(urls[0]);
                        mFrontUlv.setImageUrl(urls[1]);
                        mBackUlv.setImageUrl(urls[2]);
                    }
                }

            }
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.location_et,R.id.ok_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.location_et:
                ListDateManager.showProvinceList(mBaseActivity, new ListDateManager.OnProvinceSelectListener() {
                    @Override
                    public void onSelect(final Province province) {
                        mShopPresenter.setProvince(province.getAreaName());
                        ListDateManager.showCityList(mBaseActivity, new ListDateManager.OnCitySelectListener() {
                            @Override
                            public void onSelect(City city) {
                                mShopPresenter.setCity(city.getAreaName());
                                mLocationEt.setText(province.getName() + city.getName());
                            }
                        },province);
                    }
                });
                break;
            case R.id.ok_btn:
                mOkBtn.setLoading(true);
                mShopPresenter.setHalf(mHalfUlv.getBitmap());
                mShopPresenter.setFront(mFrontUlv.getBitmap());
                mShopPresenter.setBack(mBackUlv.getBitmap());
                mShopPresenter.submit(mShopNameEt.getText().toString(),mShopCodeEt.getText().toString(),mShopTypeEt.getText().toString(),mShopAddressEt.getText().toString());
                break;
        }
    }


    @Override
    public void onSubmit(boolean isSuccess, String msg) {
        if (mOkBtn != null) {
            mOkBtn.setLoading(false);
        }
        ToastUtil.toast(msg);
        if (isSuccess) {
            goBack();
        }
    }
}
