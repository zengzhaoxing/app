package com.zxz.www.base.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zxz.www.base.R;
import com.zxz.www.base.view.TitleView;

public abstract class TitleFragment extends BaseFragment {

    public abstract int getContentLayoutId();

    public abstract void refreshTitle(TitleView titleView);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_title, container, false);
        viewGroup.addView(inflater.inflate(getContentLayoutId(),viewGroup,false));
        TitleView titleView = (TitleView) viewGroup.findViewById(R.id.bs_title_view);
        titleView.getLeftIv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBaseActivity.closeCurrentFragment();
            }
        });
        refreshTitle(titleView);
        return viewGroup;
    }


}
