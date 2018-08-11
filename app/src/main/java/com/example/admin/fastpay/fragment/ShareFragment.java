package com.example.admin.fastpay.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.fastpay.R;
import com.example.admin.fastpay.presenter.SharePresenter;
import com.example.admin.fastpay.thirdsdk.ShareListener;
import com.zxz.www.base.app.BaseActivity;
import com.zxz.www.base.app.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 曾宪梓 on 2018/1/14.
 */

public class ShareFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.share_text_tv)
    TextView mShareTextTv;

    private SharePresenter mPresenter;

    private String shareMoney = "3";

    public static void showFragment(BaseActivity activity, float shareMoney) {
        ShareFragment fragment = new ShareFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ShareFragment.class.getName(), shareMoney+"");
        fragment.setArguments(bundle);
        activity.openNewFragmentWithDefaultAnim(fragment);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share, container, false);
        unbinder = ButterKnife.bind(this, view);
        mPresenter = new SharePresenter(mBaseActivity);
        Bundle bundle = getArguments();
        if (bundle != null) {
            shareMoney = bundle.getString(ShareFragment.class.getName());
        }
        mShareTextTv.setText(String.format(getString(R.string.share),shareMoney));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (share == 1) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    goBack();
                }
            }, 500);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private int share = -1;

    @OnClick({R.id.no_share_tv, R.id.share_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.no_share_tv:
                share = 0;
                goBack();
                break;
            case R.id.share_tv:
                mPresenter.share(new ShareListener() {
                    @Override
                    public void onShareResult(int resultCode) {
                        share = resultCode;
                    }
                });
                break;
        }
    }

    @Override
    protected Bundle onExit() {
        Bundle bundle = new Bundle();
        bundle.putInt(ShareFragment.class.getName(), share);
        return bundle;
    }
}
