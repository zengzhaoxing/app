package com.zxz.www.base.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.zxz.www.base.R;

public abstract class StatusBarFragment extends BaseFragment {

    private View mChildContent;

    public abstract int getLayoutId();

    public boolean fitSystemUi(){
        return true;
    }

    public int getStatusBarColorRes(){
        return 0;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mChildContent = inflater.inflate(getLayoutId(), container, false);
        if (fitSystemUi()) {
            View view = inflater.inflate(R.layout.fragment_status_bar, container, false);
            if (getStatusBarColorRes() != 0) {
                view.setBackgroundColor(getStatusBarColorRes());
            }
            FrameLayout root = (FrameLayout) view.findViewById(R.id.root_view);
            root.setFitsSystemWindows(fitSystemUi());
            root.addView(mChildContent);
            return view;
        } else {
            return mChildContent;
        }
    }
}
