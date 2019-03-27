package com.zengzhaoxing.browser.view;

import android.content.Context;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.zengzhaoxing.browser.R;
import com.zxz.www.base.utils.DensityUtil;

public class WindowView extends FrameLayout {

    final float mFloat = 2f / 5f;

    public ImageView getImageView() {
        return mImageView;
    }

    public TextView getTextView() {
        return mTextView;
    }

    private ImageView mImageView;

    private WindowCloseView mCloseIv;

    private TextView mTextView;

    public WindowView(@NonNull Context context) {
        super(context);
        init();
    }

    public WindowView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WindowView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_window, this, false);
        mImageView = view.findViewById(R.id.window_iv);
        mTextView = view.findViewById(R.id.window_title_tv);
        mCloseIv = view.findViewById(R.id.close_iv);
        mCloseIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                animate().alpha(0).translationY(-getHeight()).setDuration(500).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        if (mOnWindowListener != null) {
                            mOnWindowListener.onWindowDelete((Integer) getTag());
                        }
                    }
                }).start();
            }
        });
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnWindowListener != null) {
                    mOnWindowListener.onWindowOpen((Integer) getTag());
                }
            }
        });
        addView(view);
    }

    private float mLastX;

    private float mLastY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = ev.getRawX();
                mLastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float offsetX = ev.getRawX() - mLastX;
                float offsetY = ev.getRawY() - mLastY;
                if (Math.abs(offsetY) > Math.abs(offsetX) && (Math.abs(offsetY) > DensityUtil.dip2px(2))) {
                    float t = getTranslationY() + offsetY;
                    float p = Math.abs(t) / getHeight();
                    setTranslationY(Math.min(0,t));
                    setAlpha(1 - p);
                    mLastX = ev.getRawX();
                    mLastY = ev.getRawY();
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (getTranslationY() != 0) {
                    float p = Math.abs(getTranslationY()) / getHeight();
                    if (p < mFloat) {
                        animate().alpha(1).translationY(0).setDuration(100).start();
                    } else {
                        animate().alpha(0).translationY(getHeight()).setDuration(100).withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                if (mOnWindowListener != null) {
                                    mOnWindowListener.onWindowDelete((Integer) getTag());
                                }
                            }
                        }).start();
                    }
                    return true;
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    public interface OnWindowListener {
        void onWindowDelete(int index);

        void onWindowOpen(int index);
    }

    public void setOnWindowListener(OnWindowListener onWindowListener) {
        mOnWindowListener = onWindowListener;
    }

    private OnWindowListener mOnWindowListener;

}
