package com.example.admin.fastpay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.fastpay.R;
import com.zxz.www.base.app.BaseActivity;
import com.zxz.www.base.app.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FunctionFragment extends BaseFragment {


    @BindView(R.id.function_title)
    TextView mFunctionTitle;
    @BindView(R.id.function_content)
    TextView mFunctionContent;
    Unbinder unbinder;

    private static final String TITLE = "title";

    public static void showFragment(BaseActivity activity, String title) {
        FunctionFragment fragment = new FunctionFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        fragment.setArguments(bundle);
        activity.openNewFragmentWithDefaultAnim(fragment);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_function, container, false);
        unbinder = ButterKnife.bind(this, view);
        mFunctionContent.setText(getArguments().getString(TITLE) + "功能正在建设中...");
        mFunctionTitle.setText(getArguments().getString(TITLE));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.back20)
    public void onClick() {
        goBack();
    }
}
