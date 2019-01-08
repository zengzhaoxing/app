package com.zxz.www.base.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.zxz.www.base.R;
import com.zxz.www.base.utils.DensityUtil;
import com.zxz.www.base.utils.ResUtil;

/**
 * Created by igola on 2017/10/16.
 */

public class LoadingView extends LinearLayout {

    TextView mTextView;

    public LoadingView(Context context) {
        super(context);
        initView(context, null, 0, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, 0, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs, defStyleAttr, defStyleRes);
    }



    private void initView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        setBackgroundResource(R.color.black_80);
        ProgressBar progressBar = new ProgressBar(context);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        LayoutParams layoutParams = new LayoutParams(DensityUtil.dip2px(30), DensityUtil.dip2px(30));
        layoutParams.bottomMargin = DensityUtil.dip2px(10);
        mTextView = new TextView(context);
        mTextView.setTextColor(ResUtil.getColor(R.color.white));
        mTextView.setTextSize(13);
        mTextView.setGravity(Gravity.CENTER);
        addView(progressBar, layoutParams);
        addView(mTextView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void setLoadingText(String text) {
        mTextView.setText(text);
    }


}
