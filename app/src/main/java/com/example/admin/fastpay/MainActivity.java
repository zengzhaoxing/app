package com.example.admin.fastpay;

import com.example.admin.fastpay.fragment.HomeFragment;
import com.example.admin.fastpay.fragment.InviteFragment;
import com.example.admin.fastpay.fragment.MainHomeFragment;
import com.example.admin.fastpay.fragment.MeFragment;
import com.example.admin.fastpay.fragment.ReceiptFragment;
import com.example.admin.fastpay.test.TestFragment;
import com.zxz.www.base.app.BaseActivity;
import com.zxz.www.base.app.DefaultMainFragment;
import com.zxz.www.base.app.MainFragment;
import com.zxz.www.base.utils.ResUtil;
import com.zxz.www.base.utils.ToastUtil;

public class MainActivity extends BaseActivity {

    private long mLastClickBackTime = 0;

    @Override
    protected MainFragment returnMainFragment() {
        DefaultMainFragment fragment = new MainHomeFragment();
        fragment.addFragment(new HomeFragment(),R.mipmap.home1,R.mipmap.home2,R.string.home);
        fragment.addFragment(new ReceiptFragment(),R.mipmap.shoukuan1,R.mipmap.shoukuan2,R.string.shoukuan);
        fragment.addFragment(new InviteFragment(),R.mipmap.yaoqing1,R.mipmap.yaoqing2,R.string.yaoqing);
        fragment.addFragment(new MeFragment(),R.mipmap.me1,R.mipmap.me2,R.string.me);
        fragment.setTabTextColor(ResUtil.getColor(R.color.blue),ResUtil.getColor(R.color.dark));
        return new TestFragment();
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
