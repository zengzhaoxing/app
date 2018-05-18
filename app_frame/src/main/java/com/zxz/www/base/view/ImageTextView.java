package com.zxz.www.base.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zxz.www.base.R;
import com.zxz.www.base.utils.ViewUtil;


public class ImageTextView extends RelativeLayout{

    private ImageView mImageView;

    private TextView mTextView;

    private int mEnableTextColor;

    private int mDisableTextColor;

    private int mEnableImageRes;

    private int mDisableImageRes;

    private int mMargin;

    public void setEnable(boolean mIsEnable) {
        this.mEnable = mIsEnable;
        renderView();
    }

    boolean mEnable = true;

    public ImageTextView(Context context) {
        this(context,null,0);
    }

    public ImageTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ImageTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr){
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(lp);
        mImageView = new ImageView(getContext());
        mImageView.setId(ViewUtil.generateViewId());
        mTextView = new TextView(getContext());
        mTextView.setSingleLine();
        LayoutParams imageLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageLp.addRule(CENTER_HORIZONTAL,TRUE);
        imageLp.bottomMargin = mMargin;
        mImageView.setLayoutParams(imageLp);
        LayoutParams textLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textLp.addRule(CENTER_HORIZONTAL,TRUE);
        mTextView.setLayoutParams(textLp);
        textLp.addRule(BELOW, mImageView.getId());

        addView(mImageView);
        addView(mTextView);
        renderView();
    }

    private void renderView() {
        mTextView.setTextColor(mEnable ? mEnableTextColor : mDisableTextColor);
        mImageView.setImageResource(mEnable ? mEnableImageRes : mDisableImageRes);
    }


}
