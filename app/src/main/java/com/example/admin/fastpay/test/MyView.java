package com.example.admin.fastpay.test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MyView extends TextView {
    public MyView(Context context) {
        super(context);
        setGravity(Gravity.CENTER);
        setSingleLine();
        setText("dsfsdf");
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean b = super.onTouchEvent(ev);
        Log.i("zxz", "MyView onTouchEvent " + b);
        return b;
    }

    @Override
    protected int computeHorizontalScrollRange() {
        super.computeHorizontalScrollRange();
        return getWidth();
    }

}
