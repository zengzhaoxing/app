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
import com.example.admin.fastpay.model.response.StoreResp;
import com.example.admin.fastpay.model.TokenModel;
import com.example.admin.fastpay.model.request.AddDeviceReq;
import com.example.admin.fastpay.model.response.Resp;
import com.example.admin.fastpay.utils.UrlBase;
import com.zxz.www.base.app.BaseFragment;
import com.zxz.www.base.net.request.JsonRequester;
import com.zxz.www.base.net.request.RequesterFactory;
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


public class AddDeviceFragment extends BaseFragment {

    @BindView(R.id.add_management_shebeiming)
    EditText mAddManagementShebeiming;
    @BindView(R.id.add_management_shebeimiyao)
    EditText mAddManagementShebeimiyao;
    @BindView(R.id.add_management_shebeihao)
    EditText mAddManagementShebeihao;
    @BindView(R.id.add_management_phone)
    EditText mAddManagementPhone;
    @BindView(R.id.add_management_dianpu)
    Spinner mAddManagementDianpu;
    Unbinder unbinder;
    private List<StoreResp.Store> mStores;

    private int munber;
    private SpAdapter spadapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_device, container, false);
        unbinder = ButterKnife.bind(this, view);
        mAddManagementDianpu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    private void requestAddDevice() {
        if (mStores == null || mStores.size() == 0) {
            ToastUtil.toast("无可绑定店铺");
            return;
        }
        String addming = mAddManagementShebeiming.getText().toString().trim();
        String addmiyao = mAddManagementShebeimiyao.getText().toString().trim();
        String addhao = mAddManagementShebeihao.getText().toString().trim();
        String addshouji = mAddManagementPhone.getText().toString().trim();
        if (StringUtil.isBlank(addming) || StringUtil.isBlank(addmiyao) || StringUtil.isBlank(addhao) || StringUtil.isBlank(addshouji)) {
            ToastUtil.toast("请输入正确的信息");
            return;
        }

        String storeName = mStores.get(munber).getStore_name();
        String storeId = mStores.get(munber).getStore_id();
        AddDeviceReq req = new AddDeviceReq();
        req.setStore_id(storeId);
        req.setStore_name(storeName);
        req.setPhone(addshouji);
        req.setMachine_code(addhao);
        req.setSign(addmiyao);
        req.setName(addming);
        final JsonRequester requester = RequesterFactory.getDefaultRequesterFactory().createPostRequester(UrlBase.TIANJIASHEBEI,req, Resp.class);
        requester.setListener(new JsonRequester.OnResponseListener<Resp>() {
            @Override
            public void onResponse(Resp response, int resCode) {
                if (response != null && response.isSuccess()) {
                    goBack();
                }
                if (response != null && !StringUtil.isBlank(response.getMsg())) {
                    ToastUtil.toast(response.getMsg());
                }
            }
        });
        requester.startRequest();
    }

    //请求店铺信息   适配spinner
    private void requestStore() {

        final JsonRequester requester = RequesterFactory.getDefaultRequesterFactory().createPostRequester(UrlBase.GETANNEX, new TokenModel(), StoreResp.class);
        requester.setListener(new JsonRequester.OnResponseListener<StoreResp>() {
            @Override
            public void onResponse(StoreResp response, int resCode) {
                if (response != null && response.getData() != null && response.getData().size() > 0) {
                    mStores = response.getData();
                    if (mAddManagementDianpu != null) {
                        spadapter = new SpAdapter(mBaseActivity, mStores);
                        mAddManagementDianpu.setAdapter(spadapter);
                    }
                } else {
                    ToastUtil.toast("无可绑定店铺");
                }
            }
        });
        requester.startRequest();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.backk30, R.id.add_management_tianjia})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backk30:
                goBack();
                break;
            case R.id.add_management_tianjia:
                requestAddDevice();
                break;
        }
    }
}
