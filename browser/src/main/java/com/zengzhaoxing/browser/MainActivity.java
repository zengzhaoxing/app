package com.zengzhaoxing.browser;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.zengzhaoxing.browser.bean.UrlBean;
import com.zengzhaoxing.browser.ui.fragment.BrowserFragment;
import com.zengzhaoxing.browser.ui.fragment.HomeFragment;
import com.zxz.www.base.app.BaseActivity;
import com.zxz.www.base.app.BaseFragment;
import com.zxz.www.base.app.MainFragment;
import com.zxz.www.base.utils.PermissionUtil;
import com.zxz.www.base.utils.ToastUtil;
import com.zxz.www.base.utils.ViewUtil;

import java.util.LinkedList;
import java.util.Stack;

public class MainActivity extends BaseActivity {



    @Override
    protected MainFragment returnMainFragment() {
        return new HomeFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PermissionUtil.requestPermission(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA}, null);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null && intent.getData() != null) {
            String url = intent.getData().toString();
            Log.i("zxz", url);
            UrlBean bean = new UrlBean();
            bean.setUrl(url);
            getHome().openNewWindow(bean);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideLoadingView();
    }

    private long mLastClickBackTime = 0;

    @Override
    public void exit() {
        long offsetTime = System.currentTimeMillis() - mLastClickBackTime;
        mLastClickBackTime = System.currentTimeMillis();
        if (offsetTime < 2000) {
//            moveTaskToBack(true);
            finish();
        } else {
            ToastUtil.toast("再按一次退出程序");
        }
    }

    @Override
    public void onNotify(Bundle bundle) {

    }

    public HomeFragment getHome() {
        return (HomeFragment) mMainFragment;
    }

}
