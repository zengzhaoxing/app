package com.example.admin.fastpay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.fastpay.R;
import com.example.admin.fastpay.model.Device;
import com.example.admin.fastpay.model.request.DeleteDeviceReq;
import com.example.admin.fastpay.model.response.Resp;
import com.example.admin.fastpay.utils.UrlBase;
import com.zxz.www.base.app.BaseActivity;
import com.zxz.www.base.app.BaseFragment;
import com.zxz.www.base.net.request.JsonRequester;
import com.zxz.www.base.net.request.RequesterFactory;
import com.zxz.www.base.utils.StringUtil;
import com.zxz.www.base.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DeviceDetailFragment extends BaseFragment{

    @BindView(R.id.management_detail_ming)
    TextView mManagementDetailMing;
    @BindView(R.id.management_detail_moyao)
    TextView mManagementDetailMoyao;
    @BindView(R.id.management_detail_hao)
    TextView mManagementDetailHao;
    @BindView(R.id.management_detail_phone)
    TextView mManagementDetailPhone;
    @BindView(R.id.management_detail_dianpu)
    TextView mManagementDetailDianpu;
    Unbinder unbinder;

    private Device mDevice;

    private static final String DEVICE = "device";

    public static void showFragment(BaseActivity activity, Device device) {
        DeviceDetailFragment fragment = new DeviceDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DEVICE, device);
        fragment.setArguments(bundle);
        activity.openNewFragmentWithDefaultAnim(fragment);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        mDevice = (Device) getArguments().getSerializable(DEVICE);
        renderView();
        requestDelete();
        return view;
    }

    private void renderView() {
        mManagementDetailMing.setText(mDevice.getMname() + "");
        mManagementDetailMoyao.setText(mDevice.getMsign());
        mManagementDetailHao.setText(mDevice.getMachineCode());
        mManagementDetailPhone.setText(mDevice.getPhone());
        mManagementDetailDianpu.setText(mDevice.getStoreName());
    }

    private void requestDelete() {
        final JsonRequester requester = RequesterFactory.getDefaultRequesterFactory().createPostRequester(UrlBase.DELETESHEBEI, new DeleteDeviceReq(mDevice.getId()), Resp.class);
        requester.setListener(new JsonRequester.OnResponseListener<Resp>() {
            @Override
            public void onResponse(Resp response, int resCode) {
                if (response != null && response.isSuccess()) {
                    goBack();
                }
                if (response != null) {
                    ToastUtil.toast(response.getMsg());
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.back27, R.id.management_detail_xiugai, R.id.management_detail_shanchu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back27:
                goBack();
                break;
            case R.id.management_detail_xiugai:
                ModifyDeviceFragment.replaceFragment(mBaseActivity,mDevice);
                break;
            case R.id.management_detail_shanchu:
                requestDelete();
                break;
        }
    }

    @Override
    protected void onTopFragmentExit(Class<? extends BaseFragment> topFragmentClass, Bundle params) {
        renderView();
    }
}
