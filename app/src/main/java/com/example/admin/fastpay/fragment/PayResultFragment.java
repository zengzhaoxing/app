package com.example.admin.fastpay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.fastpay.R;
import com.zxz.www.base.app.BaseActivity;
import com.zxz.www.base.app.BaseFragment;
import com.zxz.www.base.utils.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class PayResultFragment extends BaseFragment {

    @BindView(R.id.result_iv)
    ImageView mResultIv;
    @BindView(R.id.error_text)
    TextView mErrorText;
    @BindView(R.id.error_tv)
    TextView mErrorTv;
    Unbinder unbinder;

    private static final String SUCCESS = "";

    private static final String ERROR_TEXT = "";

    public static void showFragment(BaseActivity activity, boolean isSuccess, String text) {
        PayResultFragment fragment = new PayResultFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(SUCCESS, isSuccess);
        bundle.putString(ERROR_TEXT, text);
        fragment.setArguments(bundle);
        activity.openNewFragmentWithDefaultAnim(fragment);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pay_result, container, false);
        unbinder = ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        boolean isSuccess = bundle.getBoolean(SUCCESS);
        String error = bundle.getString(ERROR_TEXT);
        mResultIv.setImageResource(isSuccess ? R.drawable.chenggong : R.drawable.shibai);
        if (StringUtil.isBlank(error)) {
            mErrorText.setVisibility(View.GONE);
            mErrorTv.setVisibility(View.GONE);
        } else {
            mErrorText.setVisibility(View.VISIBLE);
            mErrorTv.setVisibility(View.VISIBLE);
            mErrorTv.setText(error + "");
        }
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.result_btn)
    public void onClick() {
        goBack();
    }
}

