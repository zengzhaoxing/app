package com.zengzhaoxing.browser.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zengzhaoxing.browser.R;
import com.zxz.www.base.app.BaseFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.zengzhaoxing.browser.Constants.DEFAULT_WEB;
import static com.zengzhaoxing.browser.Constants.getKeyWord;

public class WindowHomeFragment extends WindowChildFragment {

    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_window_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.search_srl)
    public void onViewClicked() {
        SearchFragment fragment = new SearchFragment();
        mBaseActivity.openNewFragment(fragment);
    }
}
