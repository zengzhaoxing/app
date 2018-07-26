package com.zxz.www.base.view.gallery;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.zhaoxing.view.sharpview.SharpTextView;
import com.zhaoxing.view.sharpview.SharpView;
import com.zxz.www.base.R;

/**
 * Created by zengxianzi on 2016/8/16.
 */
public class WaterLightDot extends WaterLightView{

    private float selectDotRadius;          //选中圆点半径

    private float defaultDotRadius;    //其他圆点半径

    private int selectDotColor;        //选中点颜色

    private int defaultDotColor;    //其他点颜色

    public void setSelectDotRadius(float dotRadius) {
        this.selectDotRadius = dotRadius;
        renderView();
    }

    public void setSelectDotColor(int dotColor) {
        this.selectDotColor = dotColor;
        renderView();
    }

    public void setDefaultDotColor(int othersDotColor) {
        this.defaultDotColor = othersDotColor;
        renderView();
    }

    public void setDefaultDotRadius(float othersDotRadius) {
        this.defaultDotRadius = othersDotRadius;
        renderView();
    }

    public WaterLightDot(Context context) {
        super(context);
        initialize(context, null, 0);
    }

    public WaterLightDot(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize(context, attrs, defStyleAttr);
    }

    public WaterLightDot(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr);
    }

    public WaterLightDot(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs, 0);
    }

    private void initialize(Context context, AttributeSet attrs, int defStyleAttr){
        if (isInEditMode()) { return; }
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WaterLightDot, defStyleAttr, defStyleAttr);
        selectDotRadius = a.getDimension(R.styleable.WaterLightDot_dotRadius, 5);
        defaultDotRadius = a.getDimension(R.styleable.WaterLightDot_othersDotRadius, selectDotRadius);
        defaultDotColor = a.getColor(R.styleable.WaterLightDot_othersDotColor, 0);
        selectDotColor = a.getColor(R.styleable.WaterLightDot_dotColor, 0);
        a.recycle();
    }

    @Override
    protected void renderItemView(int position, View v) {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) v.getLayoutParams();
         if(position == getCurrentPosition()){
             lp.height = (int) (selectDotRadius * 2);
             lp.width = (int) (selectDotRadius * 2);
             v.setBackgroundColor(selectDotColor);
             ((SharpView)v).getRenderProxy().setRadius(selectDotRadius);
         }else {
             lp.height = (int) (defaultDotRadius * 2);
             lp.width = (int) (defaultDotRadius * 2);
             v.setBackgroundColor(defaultDotColor);
             ((SharpView)v).getRenderProxy().setRadius(defaultDotRadius);
         }
        v.setLayoutParams(lp);
    }

    @Override
    protected View getItemView(int position) {
        return new SharpTextView(getContext());
    }

    @Override
    protected View getAheadArrowView() {
        return null;
    }

    @Override
    protected View getBackArrowView() {
        return null;
    }
}
