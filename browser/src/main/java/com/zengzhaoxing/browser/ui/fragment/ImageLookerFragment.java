package com.zengzhaoxing.browser.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zengzhaoxing.browser.R;
import com.zxz.www.base.app.TitleFragment;
import com.zxz.www.base.utils.ViewUtil;
import com.zxz.www.base.view.TitleView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ImageLookerFragment extends TitleFragment {


    @BindView(R.id.image_iv)
    ImageView imageIv;
    Unbinder unbinder;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fra_img_looker;
    }

    @Override
    protected void refreshTitle(TitleView titleView) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        ViewUtil.LoadImg(imageIv,getArguments().getString(String.class.getName()),R.mipmap.icon);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
