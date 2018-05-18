package com.zxz.www.base.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zxz.www.base.R;
import com.zxz.www.base.utils.ViewUtil;
import com.zxz.www.base.view.LoadingView;


public abstract class BaseActivity extends AppCompatActivity {

    public boolean isBackGround() {
        return mIsBackGround;
    }

    private boolean mIsBackGround;

    private BaseFragment mCurrentFragment;

    private FragmentManager mFragmentManager;

    private MainFragment mMainFragment;

    protected abstract MainFragment returnMainFragment();

    private LoadingView mLoadingView;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SDKAgent.getInstance().doInMainActivityResult(requestCode,  resultCode,  data);
    }

    @Override
    public final void onBackPressed() {
        if (mCurrentFragment == null) {
            super.onBackPressed();
            return;
        }
        if (!mCurrentFragment.handleBackEvent()) {
            if (isHome()) {
                finish();
            } else {
                closeCurrentFragment();
            }
        }
    }

    public final void openNewFragmentWithAnim(BaseFragment baseFragment,final int enterAnim, final int exitAnim) {
        if (baseFragment == null) {
            return;
        }
        baseFragment.mPreFragment = mCurrentFragment;
        mCurrentFragment = baseFragment;
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(enterAnim, 0, 0, exitAnim);
        fragmentTransaction.add(R.id.fragment_container_frame, baseFragment, baseFragment.getClass().getName());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public final void openNewFragment(BaseFragment baseFragment) {
        openNewFragmentWithAnim(baseFragment,0, 0);
    }

    public final void openNewFragmentWithDefaultAnim(BaseFragment baseFragment) {
        openNewFragmentWithAnim(baseFragment,android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    public final void goHome() {
        while (!isHome()) {
            closeCurrentFragment();
        }
    }

    public final boolean isHome() {
        return mCurrentFragment == null || mCurrentFragment instanceof MainFragment;
    }

    public final void backToFragment(Class<? extends BaseFragment> cls) {
        while (!isHome() && mCurrentFragment.getClass() == cls) {
            closeCurrentFragment();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = getSupportFragmentManager();
        SDKAgent.getInstance().doInMainActivityCreate(savedInstanceState,this);
        setContentView(R.layout.layout_app_frame);
        mLoadingView = (LoadingView) findViewById(R.id.loading_view);
        ViewUtil.interceptTouchEvent(mLoadingView);
        mMainFragment = returnMainFragment();
        openNewFragment(mMainFragment);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mIsBackGround = false;
        SDKAgent.getInstance().doInMainActivityOnStart();
    }


    @Override
    protected void onStop() {
        super.onStop();
        mIsBackGround = true;
        SDKAgent.getInstance().doInMainActivityStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SDKAgent.getInstance().doInMainActivityDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SDKAgent.getInstance().doInMainActivityPause();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        SDKAgent.getInstance().doInMainActivityNewIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SDKAgent.getInstance().doInMainActivityResume();
    }

    BaseFragment getCurrentFragment() {
        return mCurrentFragment;
    }

    public final void closeCurrentFragment() {
        if (mCurrentFragment != null && mCurrentFragment instanceof MainFragment) {
            finish();
        }
        else if (mCurrentFragment != null) {
            Bundle bundle = mCurrentFragment.onExit();
            Class cl = mCurrentFragment.getClass();
            mCurrentFragment = mCurrentFragment.mPreFragment;
            mFragmentManager.popBackStack();
            mCurrentFragment.onTopFragmentExit(cl,bundle);
        }
    }

    public void showLoadingView(String text) {
        mLoadingView.setVisibility(View.VISIBLE);
        mLoadingView.setLoadingText(text);
    }

    public void hideLoadingView() {
        mLoadingView.setVisibility(View.GONE);
    }

    public boolean isLoadingViewShown() {
        return  mLoadingView.getVisibility() == View.VISIBLE;
    }

}
