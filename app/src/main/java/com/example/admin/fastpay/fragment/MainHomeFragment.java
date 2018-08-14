package com.example.admin.fastpay.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.admin.fastpay.R;
import com.example.admin.fastpay.presenter.UserInfoManager;
import com.zxz.www.base.app.BaseActivity;
import com.zxz.www.base.app.BaseFragment;
import com.zxz.www.base.app.DefaultMainFragment;
import com.zxz.www.base.app.MainFragment;
import com.zxz.www.base.utils.ToastUtil;
import com.zxz.www.base.view.gallery.GalleryView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 曾宪梓 on 2017/12/30.
 */

public class MainHomeFragment extends DefaultMainFragment{

    @Override
    public void onTopFragmentExit(Class<? extends BaseFragment> topFragmentClass, Bundle params) {
        if (topFragmentClass == LoginFragment.class && !UserInfoManager.isAuth()) {
            mBaseActivity.openNewFragment(new LeadingFragment());
        }
    }

    @Override
    public void onItemSelect(int position, boolean isDoubleClick) {
        if (!UserInfoManager.isAuth() && position == 1) {
            switchFragment(HomeFragment.class);
            mBaseActivity.openNewFragmentWithAnim(new CertificationFragment(), R.animator.enter_rotation, R.animator.exit_rotation);
            ToastUtil.toast("请先进行实名认证！");
        } else {
            super.onItemSelect(position, isDoubleClick);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!UserInfoManager.isLogin()) {
            mBaseActivity.openNewFragmentWithAnim(new LoginFragment(), 0, android.R.anim.slide_out_right,this);
        }
    }
}
