package com.zengzhaoxing.browser.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.zengzhaoxing.browser.App;
import com.zengzhaoxing.browser.R;
import com.zxz.www.base.utils.DensityUtil;
import com.zxz.www.base.utils.ResUtil;

public class ProgressView extends View {

    Paint mPaint = new Paint();

    RectF mRectF = new RectF();

    Rect mRectDsc = new Rect();

    Rect mRectSrc = new Rect();

    Bitmap mPauseBitmap = BitmapFactory.decodeResource(App.getContext().getResources(), R.drawable.pause);

    Bitmap mStartBitmap = BitmapFactory.decodeResource(App.getContext().getResources(), R.drawable.start);

    public ProgressView(Context context) {
        super(context);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(ResUtil.getColor(R.color.white));
        float mid = getWidth() / 2;
        int _41mid = (int) (mid/2);
        int strokeWidth = DensityUtil.dip2px(1.5f);
        mRectF.set(strokeWidth,strokeWidth,getWidth() - strokeWidth,getHeight() - strokeWidth);
        mRectSrc.set(0,0,getWidth(),getHeight());
        mRectDsc.set(_41mid + strokeWidth,_41mid + strokeWidth,getWidth() - _41mid - strokeWidth,getHeight() - _41mid - strokeWidth);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(strokeWidth);
        Bitmap bitmap = mIsPause ? mStartBitmap : mPauseBitmap;
        canvas.drawBitmap(bitmap,mRectSrc,mRectDsc,mPaint);
        mPaint.setColor(ResUtil.getColor(R.color.line_gray));
        canvas.drawCircle(mid,mid,mid - strokeWidth,mPaint);
        mPaint.setColor(ResUtil.getColor(mIsPause ? R.color.text_gray : R.color.blue));
        float sweepAngle = mProgress * 360;
        canvas.drawArc(mRectF, -90, sweepAngle, false, mPaint);
    }

    private boolean mIsPause;

    private float mProgress;

    public void pause() {
        mIsPause = true;
        invalidate();
    }

    public void start() {
        mIsPause = false;
        invalidate();
    }

    public void setProgress(float progress) {
        if (progress == 1) {
            setVisibility(INVISIBLE);
            mProgress = 0;
        } else {
            mProgress = progress;
        }
        invalidate();
    }

}
