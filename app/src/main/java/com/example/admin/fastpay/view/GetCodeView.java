package com.example.admin.fastpay.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.example.admin.fastpay.R;
import com.zhaoxing.view.sharpview.SharpTextView;
import com.zxz.www.base.utils.ResUtil;
import com.zxz.www.base.utils.SpecialTask;

/**
 * Created by 曾宪梓 on 2018/1/1.
 */

public class GetCodeView extends SharpTextView {

    private SpecialTask mSpecialTask;

    private String mText;

    public void setEnableTime(int enableTime) {
        mEnableTime = enableTime;
    }

    private int mEnableTime = 60;

    public GetCodeView(Context context) {
        super(context);
        init();
    }

    public GetCodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GetCodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setDisable() {
        if (isClickable()) {
            getRenderProxy().setBackgroundColor(R.color.bg_gray);
            mSpecialTask.start();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mSpecialTask != null) {
            mSpecialTask.stop();
        }
    }

    private void init() {
        mText = getText().toString();
        getRenderProxy().setBackgroundColor(ResUtil.getColor(R.color.blue));
        mSpecialTask = new SpecialTask(new Handler(), new SpecialTask.OnEndListener() {
            @Override
            public void onEnd() {
                if (GetCodeView.this.getContext() != null) {
                    setClickable(false);
                    setClickable(true);
                    getRenderProxy().setBackgroundColor(ResUtil.getColor(R.color.blue));
                    setText(mText);
                }
            }
        }, new SpecialTask.OnPeriodListener() {
            @Override
            public void onPeriod(int currentTime) {
                if (GetCodeView.this.getContext() != null) {
                    int time = currentTime / 1000;
                    setText(time + "s");
                }
            }
        }, 1000, mEnableTime * 1000);
    }

}
