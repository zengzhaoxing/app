package com.example.admin.fastpay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.admin.fastpay.R;
import com.example.admin.fastpay.adapter.CashierAdapter;
import com.example.admin.fastpay.model.response.CashierResp;
import com.example.admin.fastpay.model.request.CashierReq;
import com.example.admin.fastpay.utils.UrlBase;
import com.zxz.www.base.app.BaseActivity;
import com.zxz.www.base.app.BaseFragment;
import com.zxz.www.base.net.request.JsonRequester;
import com.zxz.www.base.net.request.RequesterFactory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class StoreFragment extends BaseFragment {

    @BindView(R.id.cashier_title)
    TextView mCashierTitle;
    @BindView(R.id.lv_cashier)
    ListView mLvCashier;
    @BindView(R.id.tv_problem_tishi2)
    TextView mTvProblemTishi2;
    Unbinder unbinder;

    private String store_id;

    private CashierAdapter adapter;

    private List<CashierResp.Cashier> mCashiers;

    private static final String NAME  = "name";

    private static final String STORE_ID  = "store_id";

    public static void openFragment(BaseActivity activity, String storeName, String storeId) {
        StoreFragment fragment = new StoreFragment();
        Bundle bundle = new Bundle();
        bundle.putString(NAME, storeName);
        bundle.putString(STORE_ID, storeId);
        fragment.setArguments(bundle);
        activity.openNewFragmentWithDefaultAnim(fragment);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store, container, false);
        unbinder = ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        String title = bundle.getString(NAME);
        store_id = bundle.getString(STORE_ID);
        mCashierTitle.setText(title);
        requestData();
        return view;
    }

    private void requestData() {

        final JsonRequester requester = RequesterFactory.getDefaultRequesterFactory().createPostRequester(UrlBase.GETCASHIER, new CashierReq(store_id), CashierResp.class);
        requester.setListener(new JsonRequester.OnResponseListener<CashierResp>() {
            @Override
            public void onResponse(CashierResp response, int resCode) {
                if (response != null && response.getData() != null && response.getData().size() > 0 && mLvCashier != null) {
                    mCashiers = response.getData();
                    mLvCashier.setVisibility(View.VISIBLE);
                    mTvProblemTishi2.setVisibility(View.GONE);
                    adapter = new CashierAdapter(mBaseActivity, mCashiers);
                    mLvCashier.setAdapter(adapter);
                } else {
                    mLvCashier.setVisibility(View.GONE);
                    mTvProblemTishi2.setVisibility(View.VISIBLE);
                }
            }
        });
        requester.startRequest();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.back14, R.id.shouyinyuan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back14:
                goBack();
                break;
            case R.id.shouyinyuan:
                AddCashierFragment.showFragment(mBaseActivity,store_id);
                break;
        }
    }
}
