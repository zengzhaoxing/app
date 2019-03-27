package com.zengzhaoxing.browser.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.baidu.mobstat.StatService;
import com.zengzhaoxing.browser.R;
import com.zengzhaoxing.browser.bean.UrlBean;
import com.zengzhaoxing.browser.ui.adapter.SwitchWindowAdapter;
import com.zengzhaoxing.browser.view.WindowView;
import com.zxz.www.base.app.BaseFragment;
import com.zxz.www.base.app.MainFragment;
import com.zxz.www.base.utils.DensityUtil;
import com.zxz.www.base.utils.DeviceInfoUtil;
import com.zxz.www.base.utils.ResUtil;
import com.zxz.www.base.utils.StringUtil;
import com.zxz.www.base.utils.ViewUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


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
    @BindView(R.id.white_view)
    View whiteView;

    SwitchWindowAdapter mSwitchWindowAdapter;

    private List<WindowFragment> mWindowFragments = new ArrayList<>();

    public static final float SCALE = 0.6F;

    public static final int ITEM_MARGIN = DensityUtil.dip2px(25);

    private static final int DURATION = 300;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fra_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        mBaseActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);;
        bg.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (bg != null) {
                    mBaseActivity.getWindow().setFlags(0, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    bg.setVisibility(View.GONE);
                }
            }
        }, 2000);
        initFirstWindow();

//        StatService.setUserId(getActivity(),  "13610107352");

        return view;
    }

    @Override
    protected boolean handleBackEvent() {
        if (switchRl.isShown()) {
            switchWindow(mWindowFragments.get(switchViewPager.getCurrentItem()));
            showSwitchWindow(false);
            return true;
        }
        return mCurrFragment.handleBackEvent();
    }

    @Override
    protected void onTopFragmentExit(Class<? extends BaseFragment> topFragmentClass, Bundle params) {
        mWindowFragments.get(getCurrWindowIndex()).onTopFragmentExit(topFragmentClass, params);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void showSwitchWindow(boolean isSwitch) {
        if (switchViewPager.getPageMargin() != DensityUtil.dip2px(25)) {
            int py = (int) (mWindowFrame.getHeight() * (1 - SCALE) / 2);
            int px = (int) (mWindowFrame.getWidth() * (1 - SCALE) / 2);
            switchViewPager.setPadding(px, (int) (py - ResUtil.getDimension(R.dimen.window_title_height)), px, py);
            switchViewPager.setPageMargin(ITEM_MARGIN);
        }
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
                initFirstWindow();
                showSwitchWindow(false);
                break;
        }
    }


    private void initFirstWindow() {
        mWindowFragments.clear();
        mCurrFragment = new WindowFragment();
        mWindowFragments.add(mCurrFragment);
        reSetAdapter();
        FragmentTransaction fm = getChildFragmentManager().beginTransaction();
        fm.replace(R.id.window_frame, mWindowFragments.get(0));
        fm.commitAllowingStateLoss();
        refreshWindowCount();
    }

    private void reSetAdapter() {
        reSetAdapter(-1);
    }

    private void reSetAdapter(int p) {
        mSwitchWindowAdapter = new SwitchWindowAdapter(mWindowFragments, getActivity());
        switchViewPager.setAdapter(mSwitchWindowAdapter);
        switchViewPager.setOffscreenPageLimit(5);
        mSwitchWindowAdapter.setOnWindowListener(new WindowView.OnWindowListener() {
            @Override
            public void onWindowOpen(final int position) {
                if (switchViewPager.getCurrentItem() != position) {
                    switchViewPager.setCurrentItem(position);
                }
                mWindowFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        switchWindow(mWindowFragments.get(position));
                        showSwitchWindow(false);
                    }
                }, 100);
            }

            @Override
            public void onWindowDelete(final int position) {
                if (mSwitchWindowAdapter.getCount() > 1) {
                    WindowFragment fragment =  mWindowFragments.remove(position);
                    if (fragment.isAdded()) {
                        FragmentTransaction fm = getChildFragmentManager().beginTransaction();
                        fm.remove(fragment);
                        fm.commitAllowingStateLoss();
                    }
                    refreshWindowCount();
                    int p;
                    if (position > 0) {
                        p = position - 1;
                    } else {
                        p = 0;
                    }
                    reSetAdapter(p);
                } else {
                    initFirstWindow();
                    showSwitchWindow(false);
                }
            }
        });
        if (p >= 0) {
            switchViewPager.setCurrentItem(p,false);
        }
    }

    private int getCurrWindowIndex() {
        return mWindowFragments.indexOf(mCurrFragment);
    }

    private WindowFragment mCurrFragment;

    private void switchWindow(WindowFragment fragment) {
        if (mCurrFragment != fragment && mWindowFragments.contains(fragment)) {
            FragmentTransaction fm = getChildFragmentManager().beginTransaction();
            if (fragment.isAdded()) {
                fm.show(fragment);
                fm.hide(mCurrFragment);
                fm.commitAllowingStateLoss();
            } else {
                if (fragment.getArguments() != null) {
                    fm.setCustomAnimations(R.anim.open_window, 0, 0, 0);
                }
                fm.add(R.id.window_frame, fragment);
                fm.commitAllowingStateLoss();
            }
            mCurrFragment = fragment;
        }
    }

    private void openNewWindow() {
        openNewWindow(null);
    }

    public void openNewWindow(UrlBean bean) {
        if (mWindowFragments.size() == 10) {
            mWindowFragments.remove(9);
        }
        WindowFragment fragment = new WindowFragment();
        if (bean != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(UrlBean.class.getName(),bean);
            fragment.setArguments(bundle);
            fragment.mPreWindow = mCurrFragment;
        }
        mWindowFragments.add(fragment);
        mSwitchWindowAdapter.notifyDataSetChanged();
        switchViewPager.setCurrentItem(mWindowFragments.size() - 1);
        switchWindow(fragment);
        refreshWindowCount();
    }

    public void openBackWindow(UrlBean bean) {
        if (mWindowFragments.size() == 10) {
            mWindowFragments.remove(9);
        }
        WindowFragment fragment = new WindowFragment();
        mTemp = fragment;
        Bundle bundle = new Bundle();
        bundle.putSerializable(UrlBean.class.getName(),bean);
        fragment.setArguments(bundle);
        mWindowFragments.add(fragment);
        mSwitchWindowAdapter.notifyDataSetChanged();
        FragmentTransaction fm = getChildFragmentManager().beginTransaction();
        fm.add(R.id.window_frame, fragment);
        fm.commitAllowingStateLoss();
        whiteView.setVisibility(View.VISIBLE);
        whiteView.setScaleX(0);
        whiteView.setScaleY(0);
        final float py = mWindowFrame.getHeight() - ResUtil.getDimension(R.dimen.menu_bar_height) / 2 - mWindowFrame.getHeight()/2;
        float m = ResUtil.getDimension(R.dimen.menu_item_size);
        final float px = ((mWindowFrame.getWidth() - 5 * m) / 6 + m) * 5 - m / 2 - mWindowFrame.getWidth()/2;
        whiteView.animate().scaleX(0.5f).scaleY(0.5f).setDuration(300).withEndAction(new Runnable() {
            @Override
            public void run() {
                whiteView.animate().scaleX(0).scaleY(0).translationX(px).translationY(py).setDuration(300).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        whiteView.setTranslationY(0);
                        whiteView.setTranslationX(0);
                        whiteView.setVisibility(View.GONE);
                        refreshWindowCount();
                    }
                }).start();
            }
        }).start();
    }

    public void deleteCurrWindow(WindowFragment newCurr) {
        if (mWindowFragments.contains(newCurr) && newCurr != mCurrFragment) {
            mWindowFragments.remove(mCurrFragment);
            FragmentTransaction fm = getChildFragmentManager().beginTransaction();
            fm.setCustomAnimations(0, R.anim.close_window, 0, R.anim.close_window);
            fm.remove(mCurrFragment);
            fm.commitAllowingStateLoss();
            mCurrFragment = newCurr;
            reSetAdapter();
            refreshWindowCount();
        }
    }

    WindowFragment mTemp;

    public void onBrowserCreate(final WindowFragment fragment) {
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

    private void refreshWindowCount() {
        for (WindowFragment fragment1 : mWindowFragments) {
            fragment1.setWindowCount(mWindowFragments.size());
        }
    }

    public UrlBean getUrlBean() {
        return mCurrFragment.getUrlBean();
    }

}
