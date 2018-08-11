package com.example.admin.fastpay.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.admin.fastpay.R;
import com.example.admin.fastpay.presenter.UserInfoManager;
import com.zxz.www.base.app.BaseActivity;
import com.zxz.www.base.app.BaseFragment;
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

public class MainHomeFragment extends MainFragment implements RadioGroup.OnCheckedChangeListener, GalleryView.OnItemClickListener {

    Unbinder unbinder;
    @BindView(R.id.radio_group)
    RadioGroup mRadioGroup;
    @BindView(R.id.leading_gv)
    GalleryView mLeadingGv;
    @BindView(R.id.home_fragment)
    RadioButton mHomeFragment;
    @BindView(R.id.receipt_fragment)
    RadioButton mReceiptFragment;
    @BindView(R.id.invite_fragment)
    RadioButton mInviteFragment;
    @BindView(R.id.me_fragment)
    RadioButton mMeFragment;


    private ArrayList<ChildBaseFragment> mFragment;

    private int mPosition = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        initUi();
        mBaseActivity = (BaseActivity) getActivity();
        if (!UserInfoManager.isLogin()) {
            mBaseActivity.openNewFragmentWithAnim(new LoginFragment(), 0, android.R.anim.slide_out_right,this);
        }
        return view;
    }

    private void initUi() {
        mFragment = new ArrayList<>();
        mFragment.add(new HomeFragment());
        mFragment.add(new ReceiptFragment());
        mFragment.add(new InviteFragment());
        mFragment.add(new MeFragment());
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        for (Fragment fragment : mFragment) {
            ft.add(R.id.fragment, fragment);
            if (!(fragment instanceof HomeFragment)) {
                ft.hide(fragment);
            }
        }
        ft.commitAllowingStateLoss();
        mRadioGroup.setOnCheckedChangeListener(this);
        mLeadingGv.setOnItemClickListener(this);
        mLeadingGv.addImageUrl("null", R.drawable.first);
        mLeadingGv.addImageUrl("null", R.drawable.second);
        mLeadingGv.addImageUrl("null", R.drawable.third);
        mLeadingGv.addImageUrl("null", R.drawable.four);
        mLeadingGv.showViewPager();
        mLeadingGv.setVisibility(View.GONE);
    }

    private void switchFragment() {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        for (int i = 0; mFragment != null && i < mFragment.size(); i++) {
            if (mFragment.get(i).isAdded()) {
                ft.hide(mFragment.get(i));
            }
        }
        BaseFragment fragment = mFragment.get(mPosition);
        fragment.refreshUi();
        ft.show(fragment);
        ft.commit();
    }

    public void goToHomeFragment() {
        mPosition = 0;
        mHomeFragment.setChecked(true);
        mMeFragment.setChecked(false);
        mInviteFragment.setChecked(false);
        mReceiptFragment.setChecked(false);
        switchFragment();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (!UserInfoManager.isAuth() && checkedId == R.id.receipt_fragment) {
            goToHomeFragment();
            mBaseActivity.openNewFragmentWithAnim(new CertificationFragment(),R.animator.enter_rotation,R.animator.exit_rotation);
            ToastUtil.toast("请先进行实名认证！");
            return;
        }
        switch (checkedId) {
            case R.id.home_fragment://0
                mPosition = 0;
                switchFragment();
                break;
            case R.id.receipt_fragment://1   receipt_fragment
                mPosition = 1;
                switchFragment();
                break;
            case R.id.invite_fragment://2
                mPosition = 2;
                switchFragment();
                break;
            case R.id.me_fragment://3
                mPosition = 3;
                switchFragment();
                break;
        }
    }

    @Override
    public void onTopFragmentExit(Class<? extends BaseFragment> topFragmentClass, Bundle params) {
        if (topFragmentClass == LoginFragment.class) {
            if (UserInfoManager.isAuth()) {
                mLeadingGv.setVisibility(View.GONE);
            } else {
                mLeadingGv.setVisibility(View.VISIBLE);
            }
            goToHomeFragment();
        }
        mFragment.get(mPosition).onTopFragmentExit(topFragmentClass, params);
    }

    @Override
    protected boolean handleBackEvent() {
        return mFragment.get(mPosition).handleBackEvent();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onItemClick(int position, View v) {
        if (position == mLeadingGv.getPageCount() - 1) {
            mLeadingGv.setVisibility(View.GONE);
        } else {
            mLeadingGv.setCurrentPosition(position + 1);
        }
    }
}
