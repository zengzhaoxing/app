package com.zxz.www.base.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.zhaoxing.view.sharpview.SharpImageView;
import com.zhaoxing.view.sharpview.SharpTextView;
import com.zxz.www.base.R;
import com.zxz.www.base.utils.DensityUtil;
import com.zxz.www.base.utils.ResUtil;
import com.zxz.www.base.utils.ViewUtil;

/**
 * Created by zengxianzi on 2017/2/16.
 */

public class ImageTextView extends RelativeLayout {

    private SharpImageView mRoundedImageView;
    private SharpTextView mCircleTextView;
    private int mDotNum;

    private TextView mTextView;

    int mTextColor;

    float mTextSize;

    public void setDisableTextColor(int disableTextColor) {
        mDisableTextColor = disableTextColor;
        renderView();
    }

    int mDisableTextColor  = -1;

    public void setImageRes(int imageRes) {
        mImageRes = imageRes;
    }

    public void setEnable(boolean enable) {
        mIsEnable = enable;
    }

    int mImageRes;

    public void setDisableImageRes(int disableImageRes) {
        mDisableImageRes = disableImageRes;
    }

    int mDisableImageRes  = -1;

    public void setmIsEnable(boolean mIsEnable) {
        this.mIsEnable = mIsEnable;
        renderView();
    }

    boolean mIsEnable = true;

    public void setMargin(int mMargin) {
        LayoutParams imageLp = (LayoutParams) mRoundedImageView.getLayoutParams();
        imageLp.bottomMargin = mMargin;
        mRoundedImageView.setLayoutParams(imageLp);
    }

    public ImageTextView(Context context) {
        this(context, null, 0);
    }

    public ImageTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ImageTextView, defStyleAttr, defStyleAttr);
        mImageRes = a.getResourceId(R.styleable.ImageTextView_image, R.drawable.bs_black_back);
        mDisableImageRes = a.getResourceId(R.styleable.ImageTextView_disable_image, mImageRes);
        String text = a.getString(R.styleable.ImageTextView_text);
        mTextColor = a.getColor(R.styleable.ImageTextView_textColor, 0);
        mDisableTextColor = a.getColor(R.styleable.ImageTextView_disable_textColor, mTextColor);
        boolean showCircle = a.getBoolean(R.styleable.ImageTextView_showCircle, false);
        mTextSize = a.getDimensionPixelSize(R.styleable.ImageTextView_textSize, 0);
        float margin = a.getDimension(R.styleable.ImageTextView_margin, DensityUtil.dip2px(2));
        float marginRight = DensityUtil.dip2px(2);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(lp);
        mRoundedImageView = new SharpImageView(getContext());
        mRoundedImageView.setId(ViewUtil.generateViewId());
        mTextView = new TextView(getContext());
        mTextView.setSingleLine();
        LayoutParams imageLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageLp.addRule(CENTER_HORIZONTAL, TRUE);
        imageLp.bottomMargin = (int) margin;
        imageLp.rightMargin = (int) marginRight;
        mRoundedImageView.setLayoutParams(imageLp);
        mRoundedImageView.setPadding(0, DensityUtil.dip2px(10), 0, 0);
        LayoutParams textLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textLp.addRule(CENTER_HORIZONTAL, TRUE);
        mTextView.setLayoutParams(textLp);
        textLp.addRule(BELOW, mRoundedImageView.getId());
        addView(mRoundedImageView);
        addView(mTextView);
        mCircleTextView = new SharpTextView(context);
        mCircleTextView.setTextColor(ResUtil.getColor(R.color.white));
        mCircleTextView.setTextSize(9);
        mCircleTextView.setGravity(Gravity.CENTER);
        mCircleTextView.getRenderProxy().setBackgroundColor(ResUtil.getColor(R.color.red));
        LayoutParams circleLp = new LayoutParams(DensityUtil.dip2px(16), DensityUtil.dip2px(16));
        circleLp.addRule(ALIGN_PARENT_TOP, TRUE);
        circleLp.addRule(RIGHT_OF, mRoundedImageView.getId());
        mCircleTextView.setLayoutParams(circleLp);
        addView(mCircleTextView);
        mCircleTextView.setVisibility(showCircle ? VISIBLE : INVISIBLE);
        setText(text);
        renderView();
        a.recycle();
    }

    public boolean isDotNumShown() {
        return mDotNum > 0;
    }

    public void hideDot() {
        mCircleTextView.setVisibility(GONE);
    }

    public void showDot(int i) {
        mCircleTextView.setVisibility(VISIBLE);
        mDotNum = i;
        if (i >= 1) {
            LayoutParams circleLp = new LayoutParams(DensityUtil.dip2px(16), DensityUtil.dip2px(16));
            circleLp.addRule(ALIGN_PARENT_TOP, TRUE);
            circleLp.addRule(RIGHT_OF, mRoundedImageView.getId());
            mCircleTextView.setLayoutParams(circleLp);
            mCircleTextView.getRenderProxy().setRadius(DensityUtil.dip2px(8));
            if (i > 99) {
                mCircleTextView.setText("99+");
            } else {
                mCircleTextView.setText(i + "");
            }
        } else if (i == 0) {
            LayoutParams circleLp = new LayoutParams(DensityUtil.dip2px(8), DensityUtil.dip2px(8));
            circleLp.addRule(ALIGN_PARENT_TOP, TRUE);
            circleLp.addRule(RIGHT_OF, mRoundedImageView.getId());
            mCircleTextView.getRenderProxy().setRadius(DensityUtil.dip2px(4));
            mCircleTextView.setLayoutParams(circleLp);
            mCircleTextView.setText("");
        }
    }

    private void renderView() {
        if (mDisableTextColor == -1) {
            mDisableTextColor = mTextColor;
        }
        if (mDisableImageRes == -1) {
            mDisableImageRes = mImageRes;
        }
        mTextView.setTextColor(mIsEnable ? mTextColor : mDisableTextColor);
        mTextView.setTextSize(mTextSize);
        mRoundedImageView.setImageResource(mIsEnable ? mImageRes : mDisableImageRes);
    }

    public void setImageResource(@DrawableRes int resId) {
        mImageRes = resId;
        renderView();
    }

    public void setImageBitmap(Bitmap bitmap) {
        mRoundedImageView.setImageBitmap(bitmap);
    }

    public void setImageUrl(String url) {
        ViewUtil.LoadImg(mRoundedImageView,url,0);
    }

    public void setText(@StringRes int resId) {
        mTextView.setText(getContext().getString(resId));
    }

    public void setText(String s) {
        mTextView.setText(s + "");
    }

    public void setTextColor(int color) {
        mTextColor = color;
        renderView();
    }

    public void setTextSize(float size) {
        mTextSize = size;
        renderView();
    }

}
