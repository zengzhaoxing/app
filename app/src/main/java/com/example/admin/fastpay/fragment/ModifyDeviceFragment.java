package com.example.admin.fastpay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.admin.fastpay.R;
import com.example.admin.fastpay.adapter.SpAdapter;
import com.example.admin.fastpay.model.Device;
import com.example.admin.fastpay.model.TokenModel;
import com.example.admin.fastpay.model.request.AddDeviceReq;
import com.example.admin.fastpay.model.response.Resp;
import com.example.admin.fastpay.model.response.StoreResp;
import com.example.admin.fastpay.net.RequestCaller;
import com.example.admin.fastpay.utils.UrlBase;
import com.zxz.www.base.app.BaseActivity;
import com.zxz.www.base.app.BaseFragment;
import com.zxz.www.base.net.request.JsonRequester;
import com.zxz.www.base.utils.StringUtil;
import com.zxz.www.base.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by admin on 2017/7/25.
 */


public class ModifyDeviceFragment extends BaseFragment {

    @BindView(R.id.add_management_shebeiming1)
    EditText mAddManagementShebeiming1;
    @BindView(R.id.add_management_shebeimiyao1)
    EditText mAddManagementShebeimiyao1;
    @BindView(R.id.add_management_shebeihao1)
    EditText mAddManagementShebeihao1;
    @BindView(R.id.add_management_phone1)
    EditText mAddManagementPhone1;
    @BindView(R.id.add_management_dianpu1)
    Spinner mAddManagementDianpu1;
    Unbinder unbinder;
    private List<StoreResp.Store> dplist;
    private int munber;
    private Device mDevice;

    private static final String DEVICE = "device";

    public static void replaceFragment(BaseActivity activity, Device device) {
        ModifyDeviceFragment deviceFragment = new ModifyDeviceFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DEVICE,device);
        deviceFragment.setArguments(bundle);
        activity.replaceFragment(deviceFragment,true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modify_device, container, false);
        unbinder = ButterKnife.bind(this, view);
        mDevice = (Device) getArguments().getSerializable(DEVICE);
        mAddManagementShebeiming1.setText(mDevice.getMname() + "");
        mAddManagementShebeimiyao1.setText(mDevice.getMsign());
        mAddManagementShebeihao1.setText(mDevice.getMachineCode());
        mAddManagementPhone1.setText(mDevice.getPhone());
        mAddManagementDianpu1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                munber = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        requestStore();
        return view;
    }

    //请求接口修改设备
    private void requestAddDevice() {
        String addming = mAddManagementShebeiming1.getText().toString().trim();
        String addmiyao = mAddManagementShebeimiyao1.getText().toString().trim();
        String addhao = mAddManagementShebeihao1.getText().toString().trim();
        String addshouji = mAddManagementPhone1.getText().toString().trim();
        if (StringUtil.isBlank(addming) || StringUtil.isBlank(addmiyao) || StringUtil.isBlank(addhao) || StringUtil.isBlank(addshouji)) {
            ToastUtil.toast("请输入正确的信息");
            return;
        }
        String storeName = dplist.get(munber).getStore_name();
        String storeId = dplist.get(munber).getStore_id();
        int id = mDevice.getId();
        AddDeviceReq req = new AddDeviceReq();
        req.setStore_id(storeId);
        req.setStore_name(storeName);
        req.setPhone(addshouji);
        req.setMachine_code(addhao);
        req.setSign(addmiyao);
        req.setName(addming);
        req.setId(id);
        RequestCaller.post(UrlBase.TIANJIASHEBEI, req, Resp.class, new JsonRequester.OnResponseListener<Resp>() {
            @Override
            public void onResponse(Resp response, int resCode) {
                if (response != null && response.isSuccess()) {
                    goBack();
                }
                if (!StringUtil.isBlank(response.getMsg())) {
                    ToastUtil.toast(response.getMsg());
                }
            }
        });
    }

    //请求店铺信息   适配spinner
    private void requestStore() {
        RequestCaller.post(UrlBase.GETANNEX, new TokenModel(), StoreResp.class, new JsonRequester.OnResponseListener<StoreResp>() {
            @Override
            public void onResponse(StoreResp response, int resCode) {
                if (response != null && response.getData() != null && response.getData().size() > 0 && mAddManagementDianpu1 != null) {
                    dplist = response.getData();
                    SpAdapter spadapter = new SpAdapter(mBaseActivity, dplist);
                    mAddManagementDianpu1.setAdapter(spadapter);
                } else {
                    ToastUtil.toast("无可绑定店铺");
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.back30, R.id.add_management_xiugai})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back30:
                goBack();
                break;
            case R.id.add_management_xiugai:
                requestAddDevice();
                break;
        }
    }
}
