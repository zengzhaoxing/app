package com.example.admin.fastpay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.admin.fastpay.R;
import com.example.admin.fastpay.adapter.ManagementAdapter;
import com.example.admin.fastpay.model.Device;
import com.example.admin.fastpay.model.response.DevicesResp;
import com.example.admin.fastpay.model.TokenModel;
import com.example.admin.fastpay.utils.UrlBase;
import com.zxz.www.base.app.BaseFragment;
import com.zxz.www.base.net.request.JsonRequester;
import com.zxz.www.base.net.request.RequesterFactory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DevicesManagerFragment extends BaseFragment{


    @BindView(R.id.lv_management1)
    ListView mLvManagement1;
    @BindView(R.id.tv_problem_tishi3)
    TextView mTvProblemTishi3;
    Unbinder unbinder;
    private List<Device> mDevices;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device_manager, container, false);
        unbinder = ButterKnife.bind(this, view);
        mLvManagement1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DeviceDetailFragment.showFragment(mBaseActivity,mDevices.get(position));
            }
        });
        requestData();
        return view;
    }

    //请求设备接口
    private void requestData() {

        final JsonRequester requester = RequesterFactory.getDefaultRequesterFactory().createPostRequester(UrlBase.SHEBEI, new TokenModel(), DevicesResp.class);
        requester.setListener(new JsonRequester.OnResponseListener<DevicesResp>() {
            @Override
            public void onResponse(DevicesResp response, int resCode) {
                if ( mLvManagement1 == null) {
                    return;
                }
                if (response != null && response.getData() != null && response.getData().size() > 0) {
                    mDevices = response.getData();
                    mLvManagement1.setVisibility(View.VISIBLE);
                    mTvProblemTishi3.setVisibility(View.GONE);
                    ManagementAdapter adapter = new ManagementAdapter(mDevices, mBaseActivity);
                    mLvManagement1.setAdapter(adapter);
                } else {
                    mLvManagement1.setVisibility(View.GONE);
                    mTvProblemTishi3.setVisibility(View.VISIBLE);
                }
            }
        });
        requester.startRequest();
    }


    @OnClick({R.id.back21, R.id.management_config, R.id.add_management})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back21:
                goBack();
                break;
            case R.id.management_config:
                mBaseActivity.openNewFragmentWithDefaultAnim(new ConfigFragment());
                break;
            case R.id.add_management:
                mBaseActivity.openNewFragmentWithDefaultAnim(new AddDeviceFragment());
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
