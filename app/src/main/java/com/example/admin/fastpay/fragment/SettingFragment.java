package com.example.admin.fastpay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.example.admin.fastpay.R;
import com.example.admin.fastpay.presenter.UserInfoManager;
import com.zxz.www.base.app.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SettingFragment extends BaseFragment {

    @BindView(R.id.tb_setting_bobao)
    ToggleButton mTbSettingBobao;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        unbinder = ButterKnife.bind(this, view);
        mTbSettingBobao.setChecked(UserInfoManager.isVideoOpen());
        mTbSettingBobao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                UserInfoManager.setVideoOpen(isChecked);
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.back4, R.id.btn_log_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back4:
                goBack();
                break;
            case R.id.btn_log_out:
//                new NormalAlertDialog.Builder(mBaseActivity)
//                        .setContentText("确认退出？")
//                        .setContentTextColor(R.color.black)
//                        .setLeftButtonText("取消")
//                        .setLeftButtonTextColor(R.color.black)
//                        .setRightButtonText("确定")
//                        .setRightButtonTextColor(R.color.black_light)
//                        .setOnclickListener(new DialogInterface.OnLeftAndRightClickListener<NormalAlertDialog>() {
//                            @Override
//                            public void clickLeftButton(NormalAlertDialog dialog, View view) {
//                                dialog.dismiss();
//                            }
//
//                            @Override
//                            public void clickRightButton(NormalAlertDialog dialog, View view) {
//                                dialog.dismiss();
//                                UserInfoManager.logout();
//                                mBaseActivity.goHome();
//                                mBaseActivity.openNewFragmentWithDefaultAnim(new LoginFragment());
//                            }
//                        })
//                        .build()
//                        .show();
                break;
        }
    }
}
