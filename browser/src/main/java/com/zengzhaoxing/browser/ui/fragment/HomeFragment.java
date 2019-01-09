package com.zengzhaoxing.browser.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.zengzhaoxing.browser.R;
import com.zxz.www.base.app.BaseFragment;
import com.zxz.www.base.app.MainFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HomeFragment extends MainFragment {

    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.bg_iv)
    View bg;
    Unbinder unbinder;
    @BindView(R.id.switch_rl)
    RelativeLayout switchRl;
    private List<WindowFragment> mWindowFragments = new ArrayList<>();

    private static final float SCALE = 0.6F;

    private static final int DURATION = 300;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fra_home, container, false);
        unbinder = ButterKnife.bind(this, view);

        mBaseActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        bg.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (bg != null) {
                    mBaseActivity.getWindow().setFlags(0, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    bg.setVisibility(View.GONE);
                }
            }
        }, 2000);

        if (mWindowFragments.isEmpty()) {
            mWindowFragments.add(new WindowFragment());
        }
        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mWindowFragments.get(position);
            }

            @Override
            public int getCount() {
                return mWindowFragments.size();
            }
        });
        mViewPager.setOffscreenPageLimit(5);
        return view;
    }

    @Override
    protected boolean handleBackEvent() {
        if (mViewPager.getScaleX() != 1) {
            showSwitchWindow(false);
            return true;
        }
        return mWindowFragments.get(mViewPager.getCurrentItem()).handleBackEvent();
    }

    @Override
    protected void onTopFragmentExit(Class<? extends BaseFragment> topFragmentClass, Bundle params) {
        mWindowFragments.get(mViewPager.getCurrentItem()).onTopFragmentExit(topFragmentClass, params);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void showSwitchWindow(boolean isSwitch) {
        if (isSwitch) {
            mViewPager.animate().scaleX(SCALE).scaleY(SCALE).setDuration(DURATION).withEndAction(new Runnable() {
                @Override
                public void run() {
                    switchRl.setVisibility(View.VISIBLE);
                }
            }).start();
        } else {
            switchRl.setVisibility(View.GONE);
            mViewPager.animate().scaleX(1).scaleY(1).setDuration(DURATION).start();
        }
    }


    @OnClick({R.id.add_iv, R.id.clean_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_iv:
                break;
            case R.id.clean_iv:
                break;
        }
    }
}
