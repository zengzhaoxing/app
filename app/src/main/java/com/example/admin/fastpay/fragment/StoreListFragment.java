package com.example.admin.fastpay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.admin.fastpay.R;
import com.example.admin.fastpay.adapter.DianpuAdapter;
import com.example.admin.fastpay.model.response.StoreResp;
import com.example.admin.fastpay.model.TokenModel;
import com.example.admin.fastpay.utils.UrlBase;
import com.zxz.www.base.app.BaseFragment;
import com.zxz.www.base.net.request.JsonRequester;
import com.zxz.www.base.net.request.RequesterFactory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class StoreListFragment extends BaseFragment {

    @BindView(R.id.lv_annex)
    ListView mLvAnnex;
    @BindView(R.id.tv_problem_tishi1)
    TextView mTvProblemTishi1;
    Unbinder unbinder;

    private List<StoreResp.Store> mStores;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cashier_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        requestData();
        mLvAnnex.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StoreFragment.openFragment(mBaseActivity, mStores.get(position).getStore_name(), mStores.get(position).getStore_id());
            }
        });
        return view;
    }

   JsonRequester mJsonRequester;
    private void requestData() {
        mJsonRequester = RequesterFactory.getDefaultRequesterFactory().createPostRequester(UrlBase.GETANNEX, new TokenModel(), StoreResp.class);
        mJsonRequester.setListener(new JsonRequester.OnResponseListener<StoreResp>() {
            @Override
            public void onResponse(StoreResp response, int resCode) {
                if (mLvAnnex == null) {
                    return;
                }
                if (response != null && response.getData() != null && response.getData().size() > 0) {
                    mStores = response.getData();
                    mLvAnnex.setVisibility(View.VISIBLE);
                    mTvProblemTishi1.setVisibility(View.GONE);
                    DianpuAdapter adapter = new DianpuAdapter(mBaseActivity, mStores);
                    mLvAnnex.setAdapter(adapter);
                } else {
                    mLvAnnex.setVisibility(View.GONE);
                    mTvProblemTishi1.setVisibility(View.VISIBLE);
                }
            }
        });
        mJsonRequester.startRequest();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.back7)
    public void onViewClicked() {
        goBack();
        mJsonRequester.stopRequest();
    }

}
