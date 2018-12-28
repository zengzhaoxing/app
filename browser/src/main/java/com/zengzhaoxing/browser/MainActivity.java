package com.zengzhaoxing.browser;


import android.os.Bundle;

import com.zengzhaoxing.browser.ui.fragment.BrowserFragment;
import com.zengzhaoxing.browser.ui.fragment.HomeFragment;
import com.zxz.www.base.app.BaseActivity;
import com.zxz.www.base.app.BaseFragment;
import com.zxz.www.base.app.MainFragment;
import com.zxz.www.base.utils.ToastUtil;

import java.util.LinkedList;
import java.util.Stack;

public class MainActivity extends BaseActivity {

    public Stack<BaseFragment> mFragmentStack = new Stack<>();

    @Override
    protected MainFragment returnMainFragment() {
        return new HomeFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private long mLastClickBackTime = 0;

    @Override
    public void finish() {
        long offsetTime = System.currentTimeMillis() - mLastClickBackTime;
        mLastClickBackTime = System.currentTimeMillis();
        if (offsetTime < 2000) {
            moveTaskToBack(true);
        } else {
            ToastUtil.toast("再按一次退出程序");
        }
    }

    @Override
    public void onNotify(Bundle bundle) {

    }
}
