package com.example.admin.fastpay;

import com.example.admin.fastpay.fragment.MainHomeFragment;
import com.zxz.www.base.app.BaseActivity;
import com.zxz.www.base.app.MainFragment;
import com.zxz.www.base.utils.ToastUtil;

public class MainActivity extends BaseActivity {

    private long mLastClickBackTime = 0;

    @Override
    protected MainFragment returnMainFragment() {
        return new MainHomeFragment();
    }

    @Override
    public void finish() {
        long offsetTime = System.currentTimeMillis() - mLastClickBackTime;
        mLastClickBackTime = System.currentTimeMillis();
        if (offsetTime < 2000) {
            super.finish();
        } else {
            ToastUtil.toast("再按一次退出程序");
        }
    }

    public void exit() {
        super.finish();
    }



}
