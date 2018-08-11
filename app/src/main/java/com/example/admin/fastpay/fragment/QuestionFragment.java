package com.example.admin.fastpay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.admin.fastpay.R;
import com.example.admin.fastpay.adapter.CommonProblemAdapter;
import com.example.admin.fastpay.model.CommonProblemBean;
import com.example.admin.fastpay.model.TokenModel;
import com.example.admin.fastpay.utils.UrlBase;
import com.zxz.www.base.app.BaseFragment;
import com.zxz.www.base.net.request.JsonRequester;
import com.zxz.www.base.net.request.RequesterFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class QuestionFragment extends BaseFragment implements JsonRequester.OnResponseListener<CommonProblemBean> {

    @BindView(R.id.lv_common_problem)
    ListView mLvCommonProblem;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        unbinder = ButterKnife.bind(this, view);
        JsonRequester requester = RequesterFactory.getDefaultRequesterFactory().createPostRequester(UrlBase.CHANGJIANWENTI, new TokenModel(), CommonProblemBean.class);
        requester.setListener(this);
        requester.startRequest();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.back5)
    public void onViewClicked() {
        goBack();
    }

    @Override
    public void onResponse(CommonProblemBean response, int resCode) {
        if (mLvCommonProblem == null || response == null) {
            return;
        }
        final CommonProblemAdapter commonProblemAdapter = new CommonProblemAdapter(mBaseActivity, response.getData());
        mLvCommonProblem.setAdapter(commonProblemAdapter);
        mLvCommonProblem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                commonProblemAdapter.setSelectItem(position);
                commonProblemAdapter.notifyDataSetChanged();
            }
        });
    }
}
