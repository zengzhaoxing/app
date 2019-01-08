package com.zengzhaoxing.browser;


import android.Manifest;
import android.os.Bundle;

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
        PermissionUtil.requestPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, null);
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

    public void getBitmap() {
        ViewUtil.getBackground(getCurrentFragment().getView());
    }

}
