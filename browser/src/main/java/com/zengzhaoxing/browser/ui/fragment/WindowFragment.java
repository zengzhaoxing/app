package com.zengzhaoxing.browser.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zengzhaoxing.browser.Constants;
import com.zengzhaoxing.browser.MainActivity;
import com.zengzhaoxing.browser.R;
import com.zxz.www.base.app.BaseFragment;
import com.zxz.www.base.utils.KeyBoardUtil;
import com.zxz.www.base.utils.ViewUtil;

import java.util.Stack;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class WindowFragment extends BaseFragment {

    @BindView(R.id.ahead_iv)
    ImageView aheadIv;
    Unbinder unbinder;
    @BindView(R.id.window_tv)
    TextView windowTv;
    private Stack<BrowserFragment> mFragmentStack = new Stack<>();

    private View mRootView;

    BrowserFragment mCurrBrowserFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_window, container, false);
        unbinder = ButterKnife.bind(this, mRootView);
        if (getArguments() != null) {
            openNew(getArguments());
        } else {
            openNew(new String[]{Constants.DEFAULT_WEB,null});
        }
        windowTv.setText(((HomeFragment)getParentFragment()).getWindowCount() + "");
        return mRootView;
    }

    @Override
    protected boolean handleBackEvent() {
        if (mCurrBrowserFragment == null) {
            return false;
        }
        boolean isHandle = mCurrBrowserFragment.handleBackEvent();
        if (!isHandle && mCurrBrowserFragment.mPreBrowserFragment != null) {
            closeCurr();
            return true;
        }
        return isHandle;
    }

    @Override
    protected void onTopFragmentExit(Class<? extends BaseFragment> topFragmentClass, Bundle params) {
        if (mCurrBrowserFragment != null) {
            mCurrBrowserFragment.onTopFragmentExit(topFragmentClass, params);
        }
    }

    @Override
    protected Bundle onExit() {
        return super.onExit();
    }

    public void openNew(String[] url) {
        mFragmentStack.removeAllElements();
        BrowserFragment browserFragment = new BrowserFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArray(Constants.DEFAULT_WEB, url);
        browserFragment.setArguments(bundle);
        openNew(browserFragment);
    }

    public void openNew(Bundle bundle) {
        mFragmentStack.removeAllElements();
        BrowserFragment browserFragment = new BrowserFragment();
        browserFragment.setArguments(bundle);
        openNew(browserFragment);
    }

    private void openNew(BrowserFragment fragment) {
        fragment.mPreBrowserFragment = mCurrBrowserFragment;
        mCurrBrowserFragment = fragment;
        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.frame_layout, mCurrBrowserFragment)
                .addToBackStack(null)
                .commitAllowingStateLoss();
        aheadIv.setEnabled(!mFragmentStack.isEmpty());
    }

    public void goAhead() {
        openNew(mFragmentStack.pop());
    }

    public void closeCurr() {
        if (mCurrBrowserFragment != null) {
            mFragmentStack.add(mCurrBrowserFragment);
            KeyBoardUtil.closeKeyboard(getActivity().getWindow().getDecorView());
            Bundle bundle = mCurrBrowserFragment.onExit();
            Class cl = mCurrBrowserFragment.getClass();
            mCurrBrowserFragment = mCurrBrowserFragment.mPreBrowserFragment;
            mCurrBrowserFragment.onTopFragmentExit(cl, bundle);
            getChildFragmentManager().popBackStack();
        }
        aheadIv.setEnabled(!mFragmentStack.isEmpty());
    }

    public final void goHome() {
        while (mCurrBrowserFragment.mPreBrowserFragment != null) {
            closeCurr();
        }
        mFragmentStack.removeAllElements();
    }

    public final boolean isHome() {
        return mCurrBrowserFragment == null || mCurrBrowserFragment.mPreBrowserFragment == null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.back_iv, R.id.ahead_iv, R.id.menu_iv, R.id.home_iv, R.id.window_fl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                mBaseActivity.onBackPressed();
                break;
            case R.id.ahead_iv:
                goAhead();
                break;
            case R.id.menu_iv:
                ((MainActivity) getActivity()).getHome().showSwitchWindow(true);
                break;
            case R.id.home_iv:
                goHome();
                break;
            case R.id.window_fl:
                ((MainActivity) getActivity()).getHome().showSwitchWindow(true);
                break;
        }
    }

    public Bitmap getBitmap() {
        if (mRootView == null) {
            return null;
        }
        return ViewUtil.getBackground(getView());
    }

    public void setWindowCount(int count) {
        if (windowTv != null) {
            windowTv.setText(count + "");
        }
    }

}
