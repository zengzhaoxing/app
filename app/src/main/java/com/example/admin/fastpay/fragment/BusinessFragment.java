package com.example.admin.fastpay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.admin.fastpay.R;
import com.example.admin.fastpay.adapter.MenDianAdapter;
import com.example.admin.fastpay.presenter.UserInfoManager;
import com.zxz.www.base.app.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class BusinessFragment extends BaseFragment {

    @BindView(R.id.sh_listview)
    ListView mShListview;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_business, container, false);
        unbinder = ButterKnife.bind(this, view);
        mShListview.setAdapter(new MenDianAdapter(mBaseActivity,UserInfoManager.getStoreInfo().getData()));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.back3)
    public void onViewClicked() {
        goBack();
    }
}
