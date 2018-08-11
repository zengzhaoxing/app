package com.example.admin.fastpay.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.fastpay.R;
import com.example.admin.fastpay.model.request.CodePayReq;
import com.example.admin.fastpay.model.request.QuicklyPayReq;
import com.example.admin.fastpay.model.response.CodePayResp;
import com.example.admin.fastpay.model.response.PayWayResp;
import com.example.admin.fastpay.model.response.QuickPayResp;
import com.example.admin.fastpay.net.RequestCaller;
import com.example.admin.fastpay.presenter.UserInfoManager;
import com.example.admin.fastpay.utils.TextUtil;
import com.example.admin.fastpay.utils.UrlBase;
import com.google.gson.Gson;
import com.zxz.www.base.app.BaseFragment;
import com.zxz.www.base.net.request.JsonRequester;
import com.zxz.www.base.net.request.RequesterFactory;
import com.zxz.www.base.utils.ToastUtil;
import com.zxz.www.base.view.corner.ProgressButton;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 曾宪梓 on 2018/1/14.
 */

public class QuickPayFragment extends BaseFragment {

    public final static String sessionId = "sessionId";

    @BindView(R.id.total_price_tv)
    TextView mTotalPriceTv;
    @BindView(R.id.payee_name_tv)
    TextView mPayeeNameTv;
    @BindView(R.id.payee_amount_tv)
    TextView mPayeeAmountTv;
    @BindView(R.id.payee_fax_tv)
    TextView mPayeeFaxTv;
    @BindView(R.id.payer_card_tv)
    TextView mPayerCardTv;
    @BindView(R.id.payee_card_tv)
    TextView mPayeeCardTv;
    @BindView(R.id.code_et)
    EditText mCodeEt;
    @BindView(R.id.pay_btn)
    ProgressButton mPayBtn;

    Unbinder unbinder;

    QuicklyPayReq req;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmen_quick_pay, container, false);
        unbinder = ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        req = (QuicklyPayReq) bundle.getSerializable(QuicklyPayReq.class.getName());
        PayWayResp.DataBean bean = (PayWayResp.DataBean) bundle.getSerializable(PayWayResp.DataBean.class.getName());
        mTotalPriceTv.setText(TextUtil.getMoneyText(req.getTotalPrice()));
        mPayeeNameTv.setText(UserInfoManager.getAuthInfo().getAcc_name());
        mPayerCardTv.setText(req.getPayerAcc());
        mPayeeCardTv.setText(req.getPayeeAcc());
        mPayeeAmountTv.setText(req.getFeeRate());
        mPayeeFaxTv.setText(req.getExtraFee() + "元");
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (jsonRequest != null) {
            jsonRequest.stopRequest();
        }
    }

    @OnClick(R.id.pay_btn)
    public void onViewClicked() {
        if (mCodeEt.length() == 0) {
            ToastUtil.toast(R.string.code_hint);
        } else {
            requestPay();
        }
    }

    JsonRequester jsonRequest;

    private void requestPay() {
        mPayBtn.setLoading(true);
        CodePayReq codePayReq = new CodePayReq(mCodeEt.getText().toString(),req.getTransactionId());
        HashMap<String, String> map = new HashMap<>();
        map.put(JsonRequester.CONTENT_TYPE, JsonRequester.CONTENT_TYPE_URLENCODED);
        map.put("Cookie", getArguments().getString(sessionId));
        RequestCaller.withHeader(map);
        RequestCaller.post(UrlBase.CODE_PAY_URL, codePayReq, CodePayResp.class, new JsonRequester.OnResponseListener<CodePayResp>() {
            @Override
            public void onResponse(CodePayResp response, int resCode) {
                if (mPayBtn != null) {
                    mPayBtn.setLoading(false);
                }
                if (response != null && response.isSuccess()) {
                    mBaseActivity.goHome();
                }

                if (response != null ) {
                    ToastUtil.toast(response.getRetRemark());
                }

            }
        });
    }


}
