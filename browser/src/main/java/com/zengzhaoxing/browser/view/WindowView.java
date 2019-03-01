package com.zengzhaoxing.browser.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.zengzhaoxing.browser.R;

public class WindowView extends FrameLayout {

    public ImageView getImageView() {
        return mImageView;
    }

    public TextView getTextView() {
        return mTextView;
    }

    private ImageView mImageView;

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
        addView(view);
    }

    private float mLastX;

    private float mLastY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = ev.getX();
                mLastY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float offsetX = ev.getX() - mLastY;
                float offsetY = ev.getY() - mLastY;
                if (offsetY > offsetX) {
                    setTranslationY(Math.min(0,getTranslationY() + offsetY));
                }
                mLastX = ev.getX();
                mLastY = ev.getY();
                break;
            case MotionEvent.ACTION_UP:
                setTranslationY(0);
                setAlpha(1);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

}
