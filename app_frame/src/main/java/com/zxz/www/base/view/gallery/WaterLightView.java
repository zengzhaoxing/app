package com.zxz.www.base.view.gallery;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zxz.www.base.R;
import com.zxz.www.base.utils.SpecialTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zengxianzi on 2016/8/15.
 */
public abstract class WaterLightView extends RelativeLayout{

    public enum Direction {
        LEFT,UP,RIGHT,DOWN
    }

    public enum ChildLayoutType{
        TYPE_FIX,             //所有item固定在中间
        TYPE_UN_FIX,         //item可以随着选中item滑动
        TYPE_DIVIDE_PARENT  //每个item平分
    }

    private ChildLayoutType mChildLayoutType;

    private int count;                //item个数

    private int duration = 1000;      //时间间隔，单位毫秒;

    protected float margin;             // item之间间隔,当mChildLayoutType为TYPE_DIVIDE_PARENT时无效

    private boolean isShowArrow = false;      //是否可通过前进和后退按钮改变当前位置

    private boolean itemClickable = false;    //是否可通过点击改变当前位置

    private boolean allowAuto = false;           //是否自动改变当前位置

    private int currentPosition;  //当前位置

    private Direction direction = Direction.RIGHT;  //移动方向

    private boolean isVertical;

    private List<View> itemViews = new ArrayList<>();

    View rightArrowView;

    View leftArrowView;

    private SpecialTask mSpecialTask;

    public void setAllowAuto(boolean allowAuto) {
        this.allowAuto = allowAuto;
        stop();
    }

    public void setCount(int count) {
        this.count = count;
        initView();
    }

    public int getCount(){
        return count;
    }

    public void setCurrentPosition(int currentPosition) {
        if(currentPosition < count && currentPosition >= 0){
            this.currentPosition = currentPosition;
            renderView();
        }else{
            try {
                throw new Exception("OutOfDotCountException: position is "+currentPosition+", but dotCount is "+count);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setPosition(int offset){
        currentPosition = (currentPosition + offset + count) % count;
        renderView();
        notifyPositionChange(offset);
    }

    public void notifyPositionChange(int offset) {
        if(mOnDotPositionChangeListener != null){
            mOnDotPositionChangeListener.onDotPositionChange(offset);
        }
    }

    public int getCurrentPosition(){
        return currentPosition;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
        initView();
    }

    public void setDuration(int duration) {
        this.duration = duration;
        initTask();
    }

    public void setIsShowArrow(boolean isShowArrow) {
        this.isShowArrow = isShowArrow;
        initView();
    }

    public void setItemClickable(boolean itemClickable) {
        this.itemClickable = itemClickable;
        initView();
    }

    public void setMargin(float margin) {
        this.margin = margin;
        initView();
    }

    public WaterLightView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr);
    }

    public WaterLightView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs, 0);
    }

    public WaterLightView(Context context) {
        super(context);
        initialize(context, null, 0);
    }

    public WaterLightView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        renderView();
    }

    private void initialize(Context context, AttributeSet attrs, int defStyleAttr){
        if (isInEditMode()) { return; }
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WaterLightView, defStyleAttr, defStyleAttr);
        this.allowAuto = a.getBoolean(R.styleable.WaterLightView_showArrow,false);
        this.allowAuto = a.getBoolean(R.styleable.WaterLightView_auto, false);
        this.itemClickable = a.getBoolean(R.styleable.WaterLightView_itemClickable, false);
        int type = a.getInt(R.styleable.WaterLightView_child_layout_type,1);
        setChildLayoutType(type);
        margin = a.getDimension(R.styleable.WaterLightView_itemMargin, 0f);
        this.count = a.getInteger(R.styleable.WaterLightView_itemCount, 0);
        int d = a.getInt(R.styleable.WaterLightView_direction,3);
        duration = a.getInteger(R.styleable.WaterLightView_direction,1000);
        setDirection(d);
        initView();
        a.recycle();
    }

    private void setChildLayoutType(int i){
        switch (i){
            case 1:
                mChildLayoutType = ChildLayoutType.TYPE_FIX;
                break;
            case 2:
                mChildLayoutType = ChildLayoutType.TYPE_UN_FIX;
                break;
            case 3:
                mChildLayoutType = ChildLayoutType.TYPE_DIVIDE_PARENT;
                break;
            default:
                mChildLayoutType = ChildLayoutType.TYPE_FIX;
        }
    }

    private void setDirection(int i){
        switch (i){
            case 1:
                direction = Direction.LEFT;
                break;
            case 2:
                direction = Direction.UP;
                break;
            case 3:
                direction = Direction.RIGHT;
                break;
            case 4:
                direction = Direction.DOWN;
                break;
            default:
                direction = Direction.RIGHT;
        }
    }

    private void initTask(){
        if(mSpecialTask != null){mSpecialTask.stop();
        }
        mSpecialTask = new SpecialTask(new Handler(),null, new SpecialTask.OnPeriodListener() {

            @Override
            public void onPeriod(int currentTime) {
                if(direction == Direction.RIGHT || direction == Direction.DOWN){
                    setPosition(1);
                }else{
                    setPosition(-1);
                }
            }
        },duration,SpecialTask.FOREVER);
    }

    public void start(int delay){
        if(!allowAuto){
            return;
        }
        if(mSpecialTask != null){
            mSpecialTask.startDelay(delay);
        }else{
            initTask();
            mSpecialTask.startDelay(delay);
        }
    }

    public void stop(){
        if(mSpecialTask != null){
            mSpecialTask.stop();
        }
    }

    @Override
    public void setVisibility(int visibility) {
        if(visibility != VISIBLE){
            stop();
        }else{
            start(0);
        }
        super.setVisibility(visibility);
    }

    private void initView(){
        isVertical = direction == Direction.DOWN || direction == Direction.UP;
        removeAllViews();
        itemViews.clear();
        leftArrowView = getBackArrowView();
        rightArrowView = getAheadArrowView();
        if(isShowArrow && rightArrowView != null && leftArrowView != null){
            addView(leftArrowView);
            addView(rightArrowView);
            rightArrowView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    setPosition(1);
                }
            });
            leftArrowView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    setPosition(-1);
                }
            });
        }

        LinearLayout linearLayout = new LinearLayout(getContext());
        if (mChildLayoutType == ChildLayoutType.TYPE_DIVIDE_PARENT) {
            linearLayout.setOrientation(isVertical ? LinearLayout.VERTICAL : LinearLayout.HORIZONTAL);
            addView(linearLayout,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        for(int i = 0;i < count;i++){
            View v = getItemView(i);
            final int position = i;
            if(v != null){
                v.setOnClickListener(itemClickable ? new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setPosition(position - currentPosition);
                    }
                } : null);
                if (mChildLayoutType == ChildLayoutType.TYPE_DIVIDE_PARENT) {
                    LinearLayout.LayoutParams layoutParams;
                    if (isVertical) {
                        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 0);
                    } else {
                        layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
                    }
                    layoutParams.weight = 1;
                    linearLayout.addView(v,layoutParams);
                }else{
                    addView(v, i);
                }
                itemViews.add(v);
            }
        }
        initTask();
    }

    public void setChildLayoutType(ChildLayoutType type) {
        mChildLayoutType = type;
        initView();
    }

    protected void renderView(){
        for(int i = 0;i < count;i++){
            View v = itemViews.get(i);
            if (mChildLayoutType != ChildLayoutType.TYPE_DIVIDE_PARENT) {
                LayoutParams lp = (LayoutParams) v.getLayoutParams();
                int m;
                if (mChildLayoutType == ChildLayoutType.TYPE_UN_FIX) {
                    m = (getMeasuredWidth() - itemViews.get(i).getMeasuredWidth()) / 2;
                    m += (margin + itemViews.get(i).getMeasuredWidth()) * (i - currentPosition);
                } else {
                    m = (int) (getMeasuredWidth() - count * itemViews.get(i).getMeasuredWidth() - count * margin + margin) / 2;
                    m += (margin + itemViews.get(i).getMeasuredWidth()) * i;
                }
                if (!isVertical) {
                    lp.leftMargin = m;
                } else {
                    lp.topMargin = m;
                }
                v.setLayoutParams(lp);
            }
            renderItemView(i, itemViews.get(i));
        }
    }

    public void refreshAllItem() {
        for (int i = 0 ;itemViews != null && i < itemViews.size();i++) {
            renderItemView(i,itemViews.get(i));
        }
    }

    abstract protected void renderItemView(int position,View v);

    abstract protected View getItemView(int position);

    abstract protected View getAheadArrowView();

    abstract protected View getBackArrowView();

    public interface OnDotPositionChangeListener{
        void onDotPositionChange(int offset);
    }

    private OnDotPositionChangeListener mOnDotPositionChangeListener;

    public void setOnDotPositionChange(OnDotPositionChangeListener onDotPositionChangeListener){
        mOnDotPositionChangeListener = onDotPositionChangeListener;
    }

}
