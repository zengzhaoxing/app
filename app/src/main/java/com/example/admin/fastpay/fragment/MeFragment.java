package com.example.admin.fastpay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.fastpay.R;
import com.example.admin.fastpay.presenter.UserInfoManager;
import com.zxz.www.base.utils.AppInfoUtil;
import com.zxz.www.base.utils.ViewUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by admin on 2017/7/11.
 */

public class MeFragment extends ChildBaseFragment {

    @BindView(R.id.me_fragment_name)
    TextView mMeFragmentName;
    @BindView(R.id.me_fragment_id)
    TextView mMeFragmentId;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.me_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        refreshUi();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.me_fragment_rl, R.id.setting_ll, R.id.me_fragment_ltquestion, R.id.me_fragment_ltabout,R.id.leading_ll})
    public void onViewClicked(View view) {
        if (ViewUtil.isDoubleRequest(view)) {
            return;
        }
        switch (view.getId()) {
            case R.id.me_fragment_rl:
                mBaseActivity.openNewFragmentWithDefaultAnim(new BusinessFragment());
                break;
            case R.id.setting_ll:
                mBaseActivity.openNewFragmentWithDefaultAnim(new SettingFragment());
                break;
            case R.id.me_fragment_ltquestion:
                mBaseActivity.openNewFragmentWithDefaultAnim(new QuestionFragment());
                break;
            case R.id.me_fragment_ltabout:
                mBaseActivity.openNewFragmentWithDefaultAnim(new AboutUsFragment());
                break;
            case R.id.leading_ll:
                String uri = "android.resource://" + AppInfoUtil.getPackageName() + "/" + R.raw.leading;
                VideoFragment.showFragment(mBaseActivity,uri,"视频教程");
                break;

        }
    }

    @Override
    public void refreshUi() {
        if (UserInfoManager.isLogin() && mMeFragmentId != null) {
            mMeFragmentId.setText(UserInfoManager.getStoreId());
            mMeFragmentName.setText(UserInfoManager.getStoreName());
        }
    }
}
