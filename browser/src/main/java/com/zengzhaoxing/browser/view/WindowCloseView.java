package com.zengzhaoxing.browser.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.zengzhaoxing.browser.R;
import com.zxz.www.base.utils.DensityUtil;
import com.zxz.www.base.utils.ResUtil;

public class WindowCloseView extends View {

    Path mPath = new Path();

    Paint mPaint = new Paint();

    public WindowCloseView(Context context) {
        super(context);
    }

    public WindowCloseView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WindowCloseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setAntiAlias(true);
        mPath.moveTo(0, 0);
        mPath.lineTo(getWidth(), 0);
        mPath.lineTo(getWidth(), getHeight());
        mPath.close();
        mPaint.setColor(ResUtil.getColor(R.color.white));
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawPath(mPath, mPaint);

        mPaint.setColor(ResUtil.getColor(R.color.red));
        //13 2 3.5,7.5
        mPaint.setStrokeWidth(DensityUtil.dip2px(1.5f));
        float startY = getWidth() * 2 / 13;
        float startX = getWidth() * 7.5f / 13;
        float endY = getWidth() * 5.5f / 13;
        float endX = getWidth() * 11 / 13;
        canvas.drawLine(startX,startY,endX,endY,mPaint);
        canvas.drawLine(startX,endY,endX,startY,mPaint);
    }


}
