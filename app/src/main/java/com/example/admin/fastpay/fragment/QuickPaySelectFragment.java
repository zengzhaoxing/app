package com.example.admin.fastpay.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.fastpay.R;
import com.example.admin.fastpay.model.AuthInfo;
import com.example.admin.fastpay.model.CreditCard;
import com.example.admin.fastpay.model.TokenModel;
import com.example.admin.fastpay.model.request.QuicklyPayReq;
import com.example.admin.fastpay.model.response.CashierResp2;
import com.example.admin.fastpay.model.response.PayWayResp;
import com.example.admin.fastpay.model.response.QuickPayResp;
import com.example.admin.fastpay.net.RequestCaller;
import com.example.admin.fastpay.presenter.UserInfoManager;
import com.example.admin.fastpay.utils.TextUtil;
import com.example.admin.fastpay.utils.UrlBase;
import com.example.admin.fastpay.zxing.encode.CodeCreator;
import com.google.gson.Gson;
import com.google.zxing.WriterException;
import com.zxz.www.base.app.BaseActivity;
import com.zxz.www.base.app.BaseFragment;
import com.zxz.www.base.net.request.JsonRequester;
import com.zxz.www.base.net.request.RequesterFactory;
import com.zxz.www.base.utils.StringUtil;
import com.zxz.www.base.utils.ToastUtil;
import com.zxz.www.base.view.corner.ProgressButton;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class QuickPaySelectFragment extends BaseFragment {

    @BindView(R.id.quick_payment_count)
    TextView mQuickPaymentCount;
    @BindView(R.id.card_name_tv)
    TextView mCardNameTv;
    @BindView(R.id.card_no_tv)
    TextView mCardNoTv;
    @BindView(R.id.card_info_ll)
    View mCardInfoLl;
    @BindView(R.id.no_card_tv)
    View mNoCardTv;
    @BindView(R.id.quick_payment_sure)
    ProgressButton mQuickPaymentSure;
    @BindView(R.id.pay_ll)
    LinearLayout mPayLl;
    Unbinder unbinder;

    JsonRequester jsonRequest;

    public static String PRICE = "price";

    public static void showFragment(BaseActivity activity, float price) {
        QuickPaySelectFragment fragment = new QuickPaySelectFragment();
        Bundle bundle = new Bundle();
        bundle.putFloat(PRICE, price);
        fragment.setArguments(bundle);
        activity.openNewFragmentWithDefaultAnim(fragment);
    }

    private float mAmount;

    private AuthInfo mAuthInfo = UserInfoManager.getAuthInfo();

    private List<PayWayResp.DataBean> mDataBean;

    private PayWayResp.DataBean mBean;

    QuicklyPayReq req = new QuicklyPayReq();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quick_pay_select, container, false);
        unbinder = ButterKnife.bind(this, view);
        mAmount = getArguments().getFloat(PRICE);
        mQuickPaymentCount.setText(TextUtil.getMoneyText(mAmount));
        renderCard();
        RequestCaller.post(UrlBase.PAY_WAY_URL, null, PayWayResp.class, new JsonRequester.OnResponseListener<PayWayResp>() {
            @Override
            public void onResponse(PayWayResp response, int resCode) {
                if (mQuickPaymentSure == null) {
                    return;
                }
                if (response != null) {
                    mDataBean = response.getData();
                }
                addPayMethod();
            }
        });
        RequestCaller.post(UrlBase.SHOUYINYUAN, new TokenModel(), CashierResp2.class, new JsonRequester.OnResponseListener<CashierResp2>() {
            @Override
            public void onResponse(CashierResp2 response, int resCode) {
                if (response == null || response.getData() == null) {
                    return;
                }
                req.setAppid(response.getData().getId()+"");
            }
        });
        return view;
    }

    private ImageView[] payIv;

    private void addPayMethod() {
        mPayLl.removeAllViews();
        if (mDataBean != null && mDataBean.size() > 0) {
            payIv = new ImageView[mDataBean.size()];
        }
        for(int i = 0;mDataBean != null && i < mDataBean.size();i++) {
            final PayWayResp.DataBean bean = mDataBean.get(i);
            View view = LayoutInflater.from(mBaseActivity).inflate(R.layout.row_pay_methed, mPayLl, false);
            TextView payNameTv = (TextView) view.findViewById(R.id.pay_name_tv);
            TextView payRangeTv = (TextView) view.findViewById(R.id.pay_range_tv);
            TextView payRateTv = (TextView) view.findViewById(R.id.pay_rate_tv);
            TextView remarkTv = (TextView) view.findViewById(R.id.remark_tv);
            TextView timeTv = (TextView) view.findViewById(R.id.time_tv);

            payIv[i] = (ImageView) view.findViewById(R.id.pay_iv);
            payNameTv.setText(bean.getPayWay());
            payRangeTv.setText(bean.getAccWay());
            payRateTv.setText(bean.getRate());
            timeTv.setText(bean.getExchangeHour());
            if (StringUtil.isBlank(bean.getRemark())) {
                remarkTv.setVisibility(View.GONE);
            }
            if (StringUtil.isBlank(bean.getExchangeHour())) {
                timeTv.setVisibility(View.GONE);
            }
            remarkTv.setText(bean.getRemark());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBean = bean;
                    renderPayMethod();
                }
            });
            mPayLl.addView(view);
            if (mBean == null) {
                mBean = bean;
            }
        }
        renderPayMethod();
    }

    private void renderPayMethod() {
        if (mBean != null && payIv != null) {
            int defaultPosition = mDataBean.indexOf(mBean);
            PayWayResp.DataBean bean = mDataBean.get(defaultPosition);
            for (ImageView imageView : payIv) {
                imageView.setImageResource(R.mipmap.weixuanzhong);
            }
            payIv[defaultPosition].setImageResource(R.mipmap.xuanzhong);
            req.setFeeRate(bean.getRate());
            req.setPayWayId(bean.getPayWayId());
            req.setExtraFee(bean.getExtraFeeFloat()+"");
        }
    }


    private void requestLarger() {
        mQuickPaymentSure.setLoading(true);
        req.setCvv2(mCard.getCnn());
        req.setOrderAmount(mAmount + "");
        req.setExpiryDate(mCard.getExpiry());
        req.setPayerAcc(mCard.getBank_card());
        req.setPayerBankCode(mCard.getBank_id());
        req.setPayerIdNum(mAuthInfo.getId_card());
        req.setPayerName(mAuthInfo.getAcc_name());
        req.setPayerPhoneNo(mCard.getMobile());
        req.setPayeeAcc(mAuthInfo.getBank_card());
        req.setPayeeBankCode(mAuthInfo.getBank_code());
        req.setPayeePhoneNo(mAuthInfo.getMobile());
        req.setPayeeUnioBank(mAuthInfo.getBank_code());
        HashMap<String, String> map = new HashMap<>();
        map.put(JsonRequester.CONTENT_TYPE, JsonRequester.CONTENT_TYPE_URLENCODED);
        RequestCaller.withHeader(map);
        RequestCaller.post(UrlBase.QUICKLY_PAY_URL, req, QuickPayResp.class, new JsonRequester.OnResponseListener<QuickPayResp>() {
            @Override
            public void onResponse(QuickPayResp response, int resCode) {
                if (mQuickPaymentSure != null) {
                    mQuickPaymentSure.setLoading(false);
                }
                if (response != null && response.isSuccess()) {
                    if (StringUtil.isBlank(response.getUrl())) {
                        QuickPayFragment fragment = new QuickPayFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(QuicklyPayReq.class.getName(), req);
                        bundle.putSerializable(PayWayResp.DataBean.class.getName(), mBean);
                        bundle.putString(QuickPayFragment.sessionId, response.getSessionId());
                        fragment.setArguments(bundle);
                        mBaseActivity.openNewFragmentWithDefaultAnim(fragment);
                    } else {
                        H5Fragment.showH5Fragment(mBaseActivity, response.getUrl());
                    }
                } else if (response != null) {
                    ToastUtil.toast(response.getMessage());
                } else {
                    ToastUtil.toast("目前系统繁忙，请稍后再做交易！");
                }

            }
        });
    }

    @OnClick({R.id.quick_payment_sure, R.id.back31, R.id.quick_payment_selectcard})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.quick_payment_sure:
                if (!UserInfoManager.isAuth()) {
                    ToastUtil.toast("未实名认证");
                } else if (mCard == null) {
                    ToastUtil.toast("请选择信用卡");
                } else if(mBean == null){
                    ToastUtil.toast("请选择支付通道");
                }else if(!mBean.isOpen()){
                    ToastUtil.toast("该支付通道暂未开通，请选择其他支付通道");
                } else if(!mBean.isInRate(mAmount)){
                    ToastUtil.toast("该支付通道的金额范围是 : " + mBean.getAccWay());
                }else {
                    ShareFragment.showFragment(mBaseActivity,mBean.getExtraFeeFloat() - mBean.getShareFeeFloat());
                }
                break;
            case R.id.back31:
                goBack();
                break;
            case R.id.quick_payment_selectcard:
                CardListFragment.showFragment(mBaseActivity, true);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (jsonRequest != null) {
            jsonRequest.stopRequest();
        }
    }

    CreditCard mCard;

    @Override
    protected void onTopFragmentExit(Class<? extends BaseFragment> topFragmentClass, Bundle params) {
        if (params != null && topFragmentClass == CardListFragment.class) {
            mCard = (CreditCard) params.getSerializable(CreditCard.class.getName());
            renderCard();
        } else if (topFragmentClass == ShareFragment.class && params != null) {
            int code =  params.getInt(ShareFragment.class.getName());
            if (code == 1) {
                req.setExtraFee(mBean.getShareFeeFloat() + "");
            }
            if (code != -1) {
                requestLarger();
            }
        }
    }

    private void renderCard() {
        if (mCard != null) {
            mCardInfoLl.setVisibility(View.VISIBLE);
            mNoCardTv.setVisibility(View.GONE);
            mCardNoTv.setText("尾号" + mCard.getLastNo(4) + "信用卡");
            mCardNameTv.setText(mCard.getBank_name());
        } else {
            mCardInfoLl.setVisibility(View.GONE);
            mNoCardTv.setVisibility(View.VISIBLE);
        }
    }
}
