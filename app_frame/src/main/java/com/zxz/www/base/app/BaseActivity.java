package com.zxz.www.base.app;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zxz.www.base.R;
import com.zxz.www.base.utils.GetNativeBitmapSDK;
import com.zxz.www.base.utils.KeyBoardUtil;
import com.zxz.www.base.utils.MathUtil;
import com.zxz.www.base.utils.ViewUtil;
import com.zxz.www.base.view.LoadingView;


public abstract class BaseActivity extends AppCompatActivity implements UIPage{

    BaseFragment mCurrentFragment;

    private FragmentManager mFragmentManager;

    private MainFragment mMainFragment;

    private FragmentRouter mFragmentRouter;

    protected abstract MainFragment returnMainFragment();

    private LoadingView mLoadingView;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SDKAgent.getInstance().doInMainActivityResult(requestCode,  resultCode,  data);
        GetNativeBitmapSDK.getInstance().callbackFromSystem(requestCode,resultCode,data);
    }

    protected void onSaveInstanceState(Bundle outState) {
        // No call for super(). Bug on API Level > 11.
    }

    @Override
    public void onBackPressed() {
        pressBackKey();
    }

    public final void pressBackKey() {
        if (mCurrentFragment == null) {
            finish();
            return;
        }
        if (!mCurrentFragment.handleBackEvent()) {
            doGoBack();
        }
    }

    public final void doGoBack() {
        if (isHome()) {
            finish();
        } else {
            closeCurrentFragment();
        }
    }

    public final void openNewFragmentWithAnim(BaseFragment baseFragment,final int enterAnim, final int exitAnim) {
        openNewFragmentWithAnim(baseFragment,enterAnim,exitAnim,null);
    }

    public final void openNewFragmentWithAnim(BaseFragment baseFragment,final int enterAnim, final int exitAnim,BaseFragment pre) {
        if (baseFragment == null || (pre != mCurrentFragment && pre != null)) {
            return;
        }
        KeyBoardUtil.closeKeyboard(getWindow().getDecorView());
        baseFragment.mPreFragment = mCurrentFragment;
        mCurrentFragment = baseFragment;
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(enterAnim, 0, 0, exitAnim);
        fragmentTransaction.add(R.id.fragment_container_frame, baseFragment, baseFragment.getClass().getName());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public final void openNewFragment(BaseFragment newFragment) {
        openNewFragmentWithAnim(newFragment,0, 0,null);
    }

    public final void openNewFragment(BaseFragment newFragment,BaseFragment pre) {
        openNewFragmentWithAnim(newFragment,0, 0,pre);
    }

    public final void openNewFragmentWithDefaultAnim(BaseFragment newFragment) {
        openNewFragmentWithAnim(newFragment,android.R.anim.slide_in_left, android.R.anim.slide_out_right,null);
    }

    public final void openNewFragmentWithDefaultAnim(BaseFragment newFragment,BaseFragment pre) {
        openNewFragmentWithAnim(newFragment,android.R.anim.slide_in_left, android.R.anim.slide_out_right,pre);
    }

    public final void replaceFragment(BaseFragment baseFragment,boolean anim) {
        closeCurrentFragment();
        if (anim) {
            openNewFragmentWithDefaultAnim(baseFragment);
        } else {
            openNewFragment(baseFragment);
        }
    }

    public final void replaceFragmentWithAnim(BaseFragment baseFragment,final int enterAnim, final int exitAnim) {
        closeCurrentFragment();
        openNewFragmentWithAnim(baseFragment,enterAnim,exitAnim);
    }

    public final void goHome() {
        while (!isHome()) {
            closeCurrentFragment();
        }
    }

    public final boolean isHome() {
        return mCurrentFragment == mMainFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = getFragmentManager();
        SDKAgent.getInstance().doInMainActivityCreate(savedInstanceState,this);
        setContentView(R.layout.layout_app_frame);
        mLoadingView = (LoadingView) findViewById(R.id.loading_view);
        ViewUtil.interceptTouchEvent(mLoadingView);
        mMainFragment = returnMainFragment();
        openNewFragment(mMainFragment);
        mFragmentRouter = new FragmentRouter(this);
        mFragmentRouter.onRouterReady(mMainFragment);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SDKAgent.getInstance().doInMainActivityOnStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SDKAgent.getInstance().doInMainActivityStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SDKAgent.getInstance().doInMainActivityDestroy();
        mFragmentRouter.onRouterDestroy();
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

    public final BaseFragment getCurrentFragment() {
        return mCurrentFragment;
    }

    public final void closeCurrentFragment() {
        if (mCurrentFragment != null && mCurrentFragment == mMainFragment) {
            finish();
        } else if (mCurrentFragment != null) {
            KeyBoardUtil.closeKeyboard(getWindow().getDecorView());
            Bundle bundle = mCurrentFragment.onExit();
            Class cl = mCurrentFragment.getClass();
            mCurrentFragment = mCurrentFragment.mPreFragment;
            mCurrentFragment.onTopFragmentExit(cl,bundle);
            mFragmentManager.popBackStack();
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

    public final void closeTopFragment(Class<? extends BaseFragment>... cls) {

        while (mCurrentFragment != null) {
            if (MathUtil.contains(cls,mCurrentFragment.getClass())) {
                closeCurrentFragment();
            } else {
                return;
            }
        }
    }

    public final BaseFragment findFragment(Class<? extends BaseFragment> cls) {
        BaseFragment fragment = mCurrentFragment;
        while (fragment != null) {
            if (fragment.getClass() == cls) {
                return fragment;
            } else {
                fragment = fragment.mPreFragment;
            }
        }
        return null;
    }

    public final boolean hasFragment(Class<? extends BaseFragment> cls) {
        BaseFragment fragment = mCurrentFragment;
        while (fragment != null) {
            if (fragment.getClass() == cls) {
                return true;
            } else {
                fragment = fragment.mPreFragment;
            }
        }
        return false;
    }

    public final void goBackToFragment(Class<? extends BaseFragment> cls) {
        while (mCurrentFragment != null && !isHome() && !mCurrentFragment.getClass().getName().equals(cls.getName())) {
            closeCurrentFragment();
        }
    }


}
