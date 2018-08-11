package com.example.admin.fastpay.fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.fastpay.R;
import com.tencent.bugly.beta.Beta;
import com.zxz.www.base.app.BaseFragment;
import com.zxz.www.base.utils.AppInfoUtil;
import com.zxz.www.base.utils.IntentUtil;
import com.zxz.www.base.utils.PermissionUtil;
import com.zxz.www.base.utils.ResUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class AboutUsFragment extends BaseFragment {


    @BindView(R.id.version_tv)
    TextView mVersionTv;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_us, container, false);
        unbinder = ButterKnife.bind(this, view);
        mVersionTv.setText(AppInfoUtil.getVersionName());
        return view;
    }


    @OnClick({R.id.back6, R.id.version_rl, R.id.company_website_rl, R.id.company_phone_rl, R.id.disclaimer_ll, R.id.user_protocol_rl, R.id.consumer_protocol_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back6:
                goBack();
                break;
            case R.id.version_rl:
                Beta.checkUpgrade(true,true);
                break;
            case R.id.company_website_rl:
                H5Fragment.showH5Fragment(mBaseActivity,ResUtil.getString(R.string.app_name), ResUtil.getString(R.string.company_website));
                break;
            case R.id.company_phone_rl:
                PermissionUtil.requestPermission(mBaseActivity, Manifest.permission.CALL_PHONE, new PermissionUtil.OnPermissionRequest() {
                    @Override
                    public void onResult(boolean isAllow) {
                        if (isAllow) {
                            IntentUtil.call(ResUtil.getString(R.string.company_phone));
                        }
                    }
                });
                break;
            case R.id.disclaimer_ll:
                H5Fragment.showH5Fragment(mBaseActivity,ResUtil.getString(R.string.disclaimer), ResUtil.getString(R.string.disclaimer_url));
                break;
            case R.id.user_protocol_rl:
                H5Fragment.showH5Fragment(mBaseActivity,ResUtil.getString(R.string.user_protocol), ResUtil.getString(R.string.user_protocol_url));
                break;
            case R.id.consumer_protocol_rl:
                H5Fragment.showH5Fragment(mBaseActivity,ResUtil.getString(R.string.consumer_protocol), ResUtil.getString(R.string.consumer_protocol_url));
                break;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
