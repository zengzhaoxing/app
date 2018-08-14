package com.example.admin.fastpay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.fastpay.R;
import com.zxz.www.base.app.BaseFragment;
import com.zxz.www.base.view.gallery.GalleryView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 曾宪梓 on 2018/8/14.
 */

public class LeadingFragment extends BaseFragment implements GalleryView.OnItemClickListener {

    @BindView(R.id.leading_gv)
    GalleryView mLeadingGv;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leading, container, false);
        unbinder = ButterKnife.bind(this, view);
        mLeadingGv.setOnItemClickListener(this);
        mLeadingGv.addImageUrl("null", R.drawable.first);
        mLeadingGv.addImageUrl("null", R.drawable.second);
        mLeadingGv.addImageUrl("null", R.drawable.third);
        mLeadingGv.addImageUrl("null", R.drawable.four);
        mLeadingGv.showViewPager();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onItemClick(int position, View v) {
        if (position == mLeadingGv.getPageCount() - 1) {
            mLeadingGv.setVisibility(View.GONE);
        } else {
            mLeadingGv.setCurrentPosition(position + 1);
        }
    }
}
