package com.zxz.www.base.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxz.www.base.R;

public class TitleView extends FrameLayout{

    public TextView getTitleTv() {
        return mTitleTv;
    }

    public TextView getRightTv() {
        return mRightTv;
    }

    public TextView getLeftTv() {
        return mLeftTv;
    }

    public ImageView getLeftIv() {
        return mLeftIv;
    }

    public ImageView getRightIv() {
        return mRightIv;
    }

    TextView mTitleTv;

    TextView mRightTv;

    TextView mLeftTv;

    ImageView mLeftIv;

    ImageView mRightIv;


    public TitleView(@NonNull Context context) {
        super(context);
        init(context,null,0);
    }

    public TitleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs,0);
    }

    public TitleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.bs_title_view, this, false);
        addView(view);
        mTitleTv = (TextView) view.findViewById(R.id.bs_title_tv);
        mRightTv = (TextView) view.findViewById(R.id.bs_title_right_tv);
        mLeftTv = (TextView) view.findViewById(R.id.bs_title_left_tv);
        mLeftIv = (ImageView) view.findViewById(R.id.bs_title_left_iv);
        mRightIv = (ImageView) view.findViewById(R.id.bs_title_right_iv);



    }





}
