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

    Rect mRect = new Rect();

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
        float mid = getWidth() / 2;
        mRectF.set(0,0,getWidth(),getHeight());
        mRect.set(0,0,getWidth(),getHeight());
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(DensityUtil.dip2px(2));
        Bitmap bitmap = mIsPause ? mPauseBitmap : mStartBitmap;
        canvas.drawBitmap(bitmap,mRect,mRect,mPaint);
        mPaint.setColor(ResUtil.getColor(R.color.line_gray));
        canvas.drawCircle(mid,mid,mid,mPaint);
        mPaint.setColor(ResUtil.getColor(mIsPause ? R.color.text_gray : R.color.blue));
        float sweepAngle = mProgress / 100f * 360;
        canvas.drawArc(mRectF, 90, sweepAngle, false, mPaint);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mPauseBitmap != null && !mPauseBitmap.isRecycled()) {
            mPauseBitmap.recycle();
        }
        if (mStartBitmap != null && !mStartBitmap.isRecycled()) {
            mStartBitmap.recycle();
        }
    }

    private boolean mIsPause;

    public void setPaint(Paint paint) {
        mPaint = paint;
    }

    private int mProgress;

    public void pause() {
        mIsPause = true;
        invalidate();
    }

    public void start() {
        mIsPause = false;
        invalidate();
    }

    public void setProgress(int progress) {
        if (progress == 100) {
            setVisibility(INVISIBLE);
            mProgress = 0;
        } else {
            mProgress = progress;
        }
        invalidate();
    }

}
