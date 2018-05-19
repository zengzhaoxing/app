package com.zxz.www.base.view.corner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhaoxing.view.sharpview.SharpRelativeLayout;
import com.zxz.www.base.R;
import com.zxz.www.base.utils.DensityUtil;
import com.zxz.www.base.utils.ResUtil;

public class ProgressButton extends SharpRelativeLayout {

    private enum ProgressPosition{
        LEFT,MIDDLE,RIGHT
    }

    private int enableBg;

    private  int disableBg;

    private int[] enableBgs;

    private  int[] disableBgs;

    private int textColor;

    private int disableTextColor;

    private ProgressPosition progressPosition;

    private TextView mTextView;

    private ProgressBar mProgress;

    public void setText(String text) {
        this.text = text;
        mTextView.setText(text);
    }

    public void setTextSize(int size) {
        mTextView.setTextSize(size);
    }

    public void setText(int id) {
        this.text = ResUtil.getString(id);
        mTextView.setText(text);
    }

    private String text;

    private String disableText;

    public ProgressButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr);
    }

    public ProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs, 0);
    }

    public ProgressButton(Context context) {
        super(context);
        initialize(context, null, 0);
    }

    private void initialize(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ProgressButton, defStyleAttr, defStyleAttr);
        enableBg = a.getColor(R.styleable.ProgressButton_enable_bg_color, 0);
        disableBg = a.getColor(R.styleable.ProgressButton_disable_bg_color, 0);
        textColor = a.getColor(R.styleable.ProgressButton_text_color, 0);
        disableTextColor = a.getColor(R.styleable.ProgressButton_disable_text_color, textColor);
        float textSize = a.getDimension(R.styleable.ProgressButton_text_size, 12);
        int i = a.getInt(R.styleable.ProgressButton_progress_position, 1);
        text = a.getString(R.styleable.ProgressButton_text);
        disableText = a.getString(R.styleable.ProgressButton_loading_text);
        if (disableText == null) {
            disableText = text;
        }
        switch (i){
            case 1:
                progressPosition = ProgressPosition.LEFT;
                break;
            case 2:
                progressPosition = ProgressPosition.MIDDLE;
                break;
            case 3:
                progressPosition = ProgressPosition.RIGHT;
                break;
        }
        a.recycle();

        mTextView = new TextView(context);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        mTextView.setTextColor(textColor);
        mTextView.setGravity(Gravity.CENTER);

        mProgress = new ProgressBar(context);
        LayoutParams layoutParams = new LayoutParams(DensityUtil.dip2px(30), DensityUtil.dip2px(30));
        layoutParams.addRule(CENTER_VERTICAL, TRUE);
        switch (progressPosition){
            case LEFT:
                layoutParams.addRule(ALIGN_PARENT_LEFT,TRUE);
                layoutParams.leftMargin = getLayoutParams().height / 2 - DensityUtil.dip2px(15);
                break;
            case MIDDLE:
                layoutParams.addRule(CENTER_HORIZONTAL, TRUE);
                break;
            case RIGHT:
                layoutParams.addRule(ALIGN_PARENT_RIGHT,TRUE);
                layoutParams.leftMargin = getLayoutParams().height / 2 - DensityUtil.dip2px(15);
                break;
        }
        mProgress.setLayoutParams(layoutParams);

        addView(mTextView,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(mProgress);

        setEnabled(true);
        setLoading(false);

    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enableBgs != null && disableBgs != null) {
            getRenderProxy().setBgColor(enabled ? enableBgs : disableBgs);
        }
        getRenderProxy().setBackgroundColor(enabled ? enableBg : disableBg);
        mTextView.setTextColor(enabled ? textColor : disableTextColor);
    }

    public void setEnableBg(int color) {
        enableBg = color;
    }

    public void setDisableBg(int color) {
        disableBg = color;
    }

    public void setEnableBg(int[] color) {
        enableBgs = color;
    }

    public void setDisableBg(int[] color) {
        disableBgs = color;
    }

    public void setEnableCanClick(boolean enabled) {
        getRenderProxy().setBackgroundColor(enabled ? enableBg : disableBg);
        mTextView.setTextColor(enabled ? textColor : disableTextColor);
    }

    public void setLoading(boolean isLoading) {
        if (isLoading) {
            mProgress.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
            mProgress.setVisibility(VISIBLE);
            if (progressPosition == ProgressPosition.MIDDLE) {
                mTextView.setText(null);
            } else {
                mTextView.setText(disableText);
            }
        } else {
            mProgress.setVisibility(GONE);
            mTextView.setText(text);
        }
        setClickable(!isLoading);
        invalidate();
    }

    public boolean isLoading() {
        return mProgress.getVisibility() == VISIBLE;
    }

}