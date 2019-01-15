package com.zengzhaoxing.browser.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.zengzhaoxing.browser.R;
import com.zengzhaoxing.browser.ui.adapter.SwitchWindowAdapter;
import com.zxz.www.base.app.BaseFragment;
import com.zxz.www.base.app.MainFragment;
import com.zxz.www.base.utils.DensityUtil;
import com.zxz.www.base.utils.DeviceInfoUtil;
import com.zxz.www.base.utils.ViewUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.zengzhaoxing.browser.Constants.DEFAULT_WEB;

public class HomeFragment extends MainFragment {

    Unbinder unbinder;

    @BindView(R.id.window_frame)
    View mWindowFrame;
    @BindView(R.id.bg_iv)
    View bg;
    @BindView(R.id.switch_rl)
    RelativeLayout switchRl;
    @BindView(R.id.switch_view_pager)
    ViewPager switchViewPager;

    SwitchWindowAdapter mSwitchWindowAdapter;


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

        setPagerAdapter();
        int height = DeviceInfoUtil.getScreenHeight(getActivity()) - ViewUtil.getNavigationBarHeight(getActivity());
        int py = (int) (height * (1 - SCALE) / 2);
        int px = (int) (DeviceInfoUtil.getScreenWidth() * (1 - SCALE) / 2);
        switchViewPager.setPadding(px,py,px,py);
        switchViewPager.setPageMargin(DensityUtil.dip2px(25));
        return view;
    }

    @Override
    protected boolean handleBackEvent() {
        if (switchRl.isShown()) {
            switchWindow(switchViewPager.getCurrentItem());
            showSwitchWindow(false);
            return true;
        }
        return mWindowFragments.get(mCurrWindow).handleBackEvent();
    }

    @Override
    protected void onTopFragmentExit(Class<? extends BaseFragment> topFragmentClass, Bundle params) {
        mWindowFragments.get(mCurrWindow).onTopFragmentExit(topFragmentClass, params);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void showSwitchWindow(boolean isSwitch) {
        if (isSwitch) {
            mWindowFrame.animate().scaleX(SCALE).scaleY(SCALE).setDuration(DURATION).withStartAction(new Runnable() {
                @Override
                public void run() {
                    switchRl.setVisibility(View.VISIBLE);
                }
            }).withEndAction(new Runnable() {
                @Override
                public void run() {
                    mWindowFrame.setVisibility(View.INVISIBLE);
                    mSwitchWindowAdapter.refresh(switchViewPager.getCurrentItem());
                }
            }).start();

        } else {
            switchRl.setVisibility(View.GONE);
            mWindowFrame.setVisibility(View.VISIBLE);
            mWindowFrame.animate().scaleX(1).scaleY(1).setDuration(DURATION).withEndAction(new Runnable() {
                @Override
                public void run() {

                }
            }).start();
        }
    }

    @OnClick({R.id.add_iv, R.id.clean_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_iv:
                openNewWindow();
                showSwitchWindow(false);
                break;
            case R.id.clean_iv:
                mWindowFragments.clear();
                mWindowFragments.add(new WindowFragment());
                setPagerAdapter();
                showSwitchWindow(false);
                for (WindowFragment fragment1 : mWindowFragments) {
                    fragment1.setWindowCount(mWindowFragments.size());
                }
                break;
        }
    }

    public int getWindowCount() {
        return mWindowFragments.size();
    }

    private void setPagerAdapter() {
        mWindowFragments.clear();
        mWindowFragments.add(new WindowFragment());
        mSwitchWindowAdapter = new SwitchWindowAdapter(mWindowFragments,getActivity());
        switchViewPager.setAdapter(mSwitchWindowAdapter);
        switchViewPager.setOffscreenPageLimit(5);
        mSwitchWindowAdapter.setOnItemClickListener(new SwitchWindowAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final int position) {
                if (switchViewPager.getCurrentItem() != position) {
                    switchViewPager.setCurrentItem(position);
                }
                mWindowFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        switchWindow(position);
                        showSwitchWindow(false);
                    }
                },100);

            }
        });
        FragmentTransaction fm = getChildFragmentManager().beginTransaction();
        fm.replace(R.id.window_frame, mWindowFragments.get(0));
        mCurrWindow = 0;
        fm.commitAllowingStateLoss();
    }

    private int mCurrWindow;

    private void switchWindow(int index) {
        if (mCurrWindow != index) {
            mCurrWindow = index;
            FragmentTransaction fm = getChildFragmentManager().beginTransaction();
            for (int i = 0; i < mWindowFragments.size(); i++) {
                WindowFragment fragment = mWindowFragments.get(i);
                if (i == index) {
                    if (fragment.isAdded()) {
                        fm.show(fragment);
                    } else {
                        fm.add(R.id.window_frame, fragment);
                    }
                } else if (fragment.isAdded()) {
                    fm.hide(fragment);
                }
            }
            fm.commitAllowingStateLoss();
        }
    }

    public void openNewWindow() {
        openNewWindow(DEFAULT_WEB);
    }

    public void openNewWindow(String url) {
        if (mWindowFragments.size() == 10) {
            mWindowFragments.remove(9);
        }
        WindowFragment fragment = new WindowFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArray(DEFAULT_WEB,new String[]{url,null});
        fragment.setArguments(bundle);
        mWindowFragments.add(fragment);
        mSwitchWindowAdapter.notifyDataSetChanged();
        switchViewPager.setCurrentItem(mWindowFragments.size() - 1);
        switchWindow(mWindowFragments.size() - 1);
        for (WindowFragment fragment1 : mWindowFragments) {
            fragment1.setWindowCount(mWindowFragments.size());
        }
    }

    public void openBackWindow(String url) {
        if (mWindowFragments.size() == 10) {
            mWindowFragments.remove(9);
        }
        WindowFragment fragment = new WindowFragment();
        mTemp = fragment;
        Bundle bundle = new Bundle();
        bundle.putStringArray(DEFAULT_WEB,new String[]{url,null});
        fragment.setArguments(bundle);
        mWindowFragments.add(fragment);
        mSwitchWindowAdapter.notifyDataSetChanged();
        FragmentTransaction fm = getChildFragmentManager().beginTransaction();
        fm.add(R.id.window_frame, fragment);
        fm.commitAllowingStateLoss();
        for (WindowFragment fragment1 : mWindowFragments) {
            fragment1.setWindowCount(mWindowFragments.size());
        }
    }

    WindowFragment mTemp;

    public void onFirstBrowserCreate(final WindowFragment fragment) {
        if (mWindowFragments.contains(fragment) && fragment.isAdded() && fragment == mTemp) {
            mWindowFrame.post(new Runnable() {
                @Override
                public void run() {
                    FragmentTransaction fm = getChildFragmentManager().beginTransaction();
                    fm.hide(fragment);
                    fm.commitAllowingStateLoss();
                    mTemp = null;
                }
            });

        }
    }


}
