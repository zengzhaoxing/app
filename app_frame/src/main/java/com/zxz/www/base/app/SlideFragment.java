package com.zxz.www.base.app;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.zxz.www.base.R;

public abstract class SlideFragment extends BaseFragment {

    private View mSlideView;

    private static final int DURATION = 300;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout relativeLayout = new RelativeLayout(getActivity());
        relativeLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        relativeLayout.setBackgroundResource(R.color.black_40);
        mSlideView = getSlideView(relativeLayout);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        relativeLayout.addView(mSlideView, lp);
        mSlideView.setVisibility(View.INVISIBLE);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SlideFragment.this.mBaseActivity.pressBackKey();
            }
        });
        return relativeLayout;
    }

    public abstract View getSlideView(ViewGroup parent);


    @Override
    protected boolean handleBackEvent() {
        mSlideView.animate().translationY(mSlideView.getHeight()).setDuration(DURATION).withEndAction(new Runnable() {
            @Override
            public void run() {
                mBaseActivity.closeCurrentFragment();
            }
        }).start();
        return true;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        view.post(new Runnable() {
            @Override
            public void run() {
                mSlideView.setTranslationY(mSlideView.getHeight());
                mSlideView.setVisibility(View.VISIBLE);
                mSlideView.animate().translationY(0).setDuration(DURATION).start();
            }
        });
    }
}
