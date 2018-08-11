package com.example.admin.fastpay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.admin.fastpay.R;
import com.example.admin.fastpay.model.response.ConfigResp;
import com.example.admin.fastpay.model.ConfigModel;
import com.example.admin.fastpay.model.TokenModel;
import com.example.admin.fastpay.model.response.RespModel;
import com.example.admin.fastpay.net.RequestCaller;
import com.example.admin.fastpay.utils.UrlBase;
import com.zxz.www.base.app.BaseFragment;
import com.zxz.www.base.net.request.JsonRequester;
import com.zxz.www.base.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ConfigFragment extends BaseFragment {


    @BindView(R.id.tv_config_id)
    EditText mTvConfigId;
    @BindView(R.id.tv_config_key)
    EditText mTvConfigKey;
    @BindView(R.id.tv_config_username)
    EditText mTvConfigUsername;
    Unbinder unbinder;
    @BindView(R.id.btn_config_xiugai)
    Button mBtnConfigXiugai;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_config, container, false);
        unbinder = ButterKnife.bind(this, view);
        setToChange(false);
        requestData();
        return view;
    }

    private void setToChange(boolean isChangeUi) {
        mTvConfigId.setEnabled(isChangeUi);
        mTvConfigKey.setEnabled(isChangeUi);
        mTvConfigUsername.setEnabled(isChangeUi);
        mBtnConfigXiugai.setText(isChangeUi ? R.string.ok : R.string.change_config);
    }

    private void requestData() {

        RequestCaller.post(UrlBase.SHEBEIPEIZHI, new TokenModel(), ConfigResp.class, new JsonRequester.OnResponseListener<ConfigResp>() {
            @Override
            public void onResponse(ConfigResp response, int resCode) {
                if (response != null && response.getData() != null && response.getData().size() > 0) {
                    mTvConfigId.setText(response.getData().get(0).getPushId() + "");
                    mTvConfigKey.setText(response.getData().get(0).getPushKey() + "");
                    mTvConfigUsername.setText(response.getData().get(0).getPushUserName() + "");
                }
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.back25, R.id.btn_config_xiugai})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back25:
                goBack();
                break;
            case R.id.btn_config_xiugai:
                if (mTvConfigId.isEnabled()) {
                    changeRequest();
                } else {
                    setToChange(true);
                }
                break;
        }
    }

    //   参数token,push_id,push_key,push_user_name   请求修改设备接口
    private void changeRequest() {

        ConfigModel configModel = new ConfigModel();
        configModel.setPush_id(mTvConfigId.getText().toString().trim());
        configModel.setPush_key(mTvConfigKey.getText().toString().trim());
        configModel.setPush_user_name(mTvConfigUsername.getText().toString().trim());
        RequestCaller.post(UrlBase.SHEBEIXIUGAI, configModel, RespModel.class, new JsonRequester.OnResponseListener<RespModel>() {
            @Override
            public void onResponse(RespModel response, int resCode) {
                if (mTvConfigId != null) {
                    setToChange(false);
                }
                if (response != null && response.message != null) {
                    ToastUtil.toast(response.message);
                }
                if (response != null && response.isSuccess()) {
                    goBack();
                }
            }
        });
    }

}
