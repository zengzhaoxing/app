package com.zxz.www.base.view.gallery;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Scroller;


import com.zxz.www.base.app.BaseApp;
import com.zxz.www.base.R;
import com.zxz.www.base.adapter.ViewPagerAdapter;
import com.zxz.www.base.utils.DensityUtil;
import com.zxz.www.base.utils.ViewUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zengxianzi on 2016/7/29.
 * Gallery with a {@link WaterLightDot}
 */
public class GalleryView extends RelativeLayout {

    private static final String TAG = "GalleryView";

    protected ViewPager mViewPager ;

    private PagerAdapter mPagerAdapter ;

    protected WaterLightView mWaterLightView;

    private int duration = 5000;    // 图片切换时间间隔;当allowAuto为true时有效

    private float mDotViewMargin;  //WaterLightView离父控件margin

    private  WaterLightDot.Direction mDotPosition ;    //圆点位置（上下左右）

    public int getPageCount() {
        return pageCount;
    }

    private int pageCount;          //页数

    private int currentPosition;     //当前位置

    private boolean isShowDot = false;  //是否显示WaterLightView

    private boolean isLoop = false; //是否循环滚动

    private boolean allowManual = false; //是否允许手动

    private boolean allowAuto = false;  //是否允许自动播放

    private float selectDotRadius;          //选中圆点半径

    private float defaultDotRadius;    //其他圆点半径

    private int selectDotColor;        //选中点颜色

    private int defaultDotColor;    //其他点颜色

    private WaterLightDot.Direction direction ;  //自动滚动方向,当allowAuto为true时有效

    private boolean isVertical;

    private boolean isScrolling;

    ArrayList<View> views = new ArrayList<>();

    ArrayList<View> views1 = new ArrayList<>();

    protected ArrayList<Fragment> mFragments = new ArrayList<>();

    public void clearViews(){
        views= new ArrayList<>();
        views1= new ArrayList<>();
    }

    public GalleryView(Context context) {
        super(context);
        initialize(context, null, 0);
    }

    public GalleryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, 0);
    }

    public GalleryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public GalleryView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize(context, attrs, defStyleRes);
    }

    private void initialize(Context context, AttributeSet attrs, int defStyleAttr) {
        if (isInEditMode()) {
            return;
        }
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GalleryView, defStyleAttr, defStyleAttr);
        duration = a.getInteger(R.styleable.GalleryView_swapDuration, 5000);
        currentPosition = a.getInteger(R.styleable.GalleryView_initPosition, 0);
        isShowDot = a.getBoolean(R.styleable.GalleryView_showDot, false);
        isLoop = a.getBoolean(R.styleable.GalleryView_loop, false);
        allowAuto = a.getBoolean(R.styleable.GalleryView_allowAuto, false);
        allowManual = a.getBoolean(R.styleable.GalleryView_allowManual, false);
        selectDotColor = a.getColor(R.styleable.GalleryView_selectDotColor, 0xffffffff);
        defaultDotColor = a.getColor(R.styleable.GalleryView_defaultDotColor, 0xff000000);
        selectDotRadius = a.getInteger(R.styleable.GalleryView_selectDotRadius, DensityUtil.dip2px(4));
        defaultDotRadius = a.getInteger(R.styleable.GalleryView_defaultDotRadius, DensityUtil.dip2px(4));
        mDotViewMargin = a.getDimension(R.styleable.GalleryView_dotMargin, DensityUtil.dip2px(4));
        mDotPosition =  setDirection(a.getInt(R.styleable.GalleryView_dotPosition, 4));
        direction = setDirection(a.getInt(R.styleable.GalleryView_autoDirection, 3));
        isVertical = direction == WaterLightView.Direction.UP || direction == WaterLightView.Direction.DOWN;
        a.recycle();
    }

    private WaterLightDot.Direction  setDirection(int i){
        switch (i){
            case 1:
                return WaterLightView.Direction.LEFT;
            case 2:
                return WaterLightView.Direction.UP;
            case 3:
                return WaterLightView.Direction.RIGHT;
            case 4:
                return WaterLightView.Direction.DOWN;
            default:
                return WaterLightView.Direction.RIGHT;
        }
    }

    public void setCurrentPosition(int position){
        setCurrentPosition(position,false);
    }

    public void setCurrentPosition(int position,boolean smoothScroll){
        if(position >= 0 && position < pageCount){
            currentPosition = position;
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() / pageCount * pageCount + currentPosition,smoothScroll);
        }
    }

    public void setNextPosition(){
        setCurrentPosition(currentPosition + 1,true);
    }

    public void setLastPosition(){
        setCurrentPosition(currentPosition - 1,true);
    }

    public int getCurrentPosition(){
        return currentPosition;
    }

    public void addImageUrl(String networkUrl, int localUrl) {
        Log.i(TAG,networkUrl+"");
        ImageView iv = new ImageView(getContext());
        ImageView iv1 = new ImageView(getContext());
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        iv.setLayoutParams(lp);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        ViewUtil.LoadImg(iv,networkUrl,localUrl);
        iv1.setLayoutParams(lp);
        iv1.setScaleType(ImageView.ScaleType.FIT_XY);
        ViewUtil.LoadImg(iv1,networkUrl,localUrl);
        views.add(iv);
        views1.add(iv1);
    }

    public void addPageView(View view){
        views.add(view);
    }


    public void addFragment(Fragment f){
        mFragments.add(f);
    }

    public void showViewPager() {
        Log.e(TAG, "initData: " );
        pageCount = views.size();
        for(View v : views1){
            views.add(v);
        }
        for(View v : views){
            if(!(v instanceof AdapterView)){
                v.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ViewUtil.isDoubleRequest(v)) {
                            return;
                        }
                        if (mOnItemClickListener != null && !isScrolling) {
                            mOnItemClickListener.onItemClick(currentPosition, GalleryView.this);
                        }
                    }
                });
            }
        }
        mPagerAdapter = new ViewPagerAdapter(views, isLoop && pageCount > 1 ? Integer.MAX_VALUE : pageCount);
        mViewPager = new DirectionViewPager(getContext(),isVertical,allowManual);
        removeAllViews();
        addWaterLightView();
        addViewPager();
        start();
    }

    public void showFragmentPager(FragmentManager fm){
        isLoop = false;
        pageCount = mFragments.size();
        mPagerAdapter = new FragmentPagerAdapter(fm) {

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return mFragments.get(arg0);
            }
        };
        mViewPager = new DirectionViewPager(getContext(),isVertical,allowManual);
        mViewPager.setId(ViewUtil.generateViewId());
        removeAllViews();
        addWaterLightView();
        addViewPager();
        mViewPager.setOffscreenPageLimit(pageCount);
        start();
    }

    private void addWaterLightView(){
        mWaterLightView = getWaterLightView();
        LayoutParams lp;
        if (mWaterLightView.getLayoutParams() instanceof LayoutParams) {
            lp = (LayoutParams) mWaterLightView.getLayoutParams();
        } else {
            lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        switch (mDotPosition){
            case UP:
                lp.addRule(ALIGN_PARENT_TOP,TRUE);
                lp.topMargin = (int) mDotViewMargin;
                break;
            case DOWN:
                lp.addRule(ALIGN_PARENT_BOTTOM,TRUE);
                lp.bottomMargin = (int) mDotViewMargin;
                break;
            case LEFT:
                lp.addRule(ALIGN_PARENT_LEFT,TRUE);
                lp.leftMargin = (int) mDotViewMargin;
                break;
            case RIGHT:
                lp.addRule(ALIGN_PARENT_RIGHT,TRUE);
                lp.rightMargin = (int) mDotViewMargin;
                break;
        }
        mWaterLightView.setLayoutParams(lp);
        mWaterLightView.setAllowAuto(allowAuto);
        mWaterLightView.setDuration(duration);
        mWaterLightView.setDirection(direction);
        mWaterLightView.setCount(pageCount);
        mWaterLightView.setOnDotPositionChange(new WaterLightDot.OnDotPositionChangeListener() {

            @Override
            public void onDotPositionChange(int offset) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + offset,allowManual);
                currentPosition = mWaterLightView.getCurrentPosition();
                checkEnd();
            }
        });
        addView(mWaterLightView);
        if (!isShowDot) {
            mWaterLightView.setVisibility(INVISIBLE);
        }
    }

    private boolean checkEnd(){
        int endPosition = direction == WaterLightDot.Direction.DOWN || direction == WaterLightDot.Direction.RIGHT
                ? pageCount - 1 : 0;
        if (!isLoop && currentPosition == endPosition) {
            stop();
            if (mOnAutoStopListener != null) {
                mOnAutoStopListener.onAutoStop(GalleryView.this);
            }
            return true;
        }else{
            return false;
        }
    }

    //子类可覆写此方法定义流水灯视图
    public WaterLightView getWaterLightView() {
        WaterLightDot wld = new WaterLightDot(getContext());
        wld.setSelectDotColor(selectDotColor);
        wld.setDefaultDotColor(defaultDotColor);
        wld.setSelectDotRadius(selectDotRadius);
        wld.setDefaultDotRadius(defaultDotRadius);
        wld.setMargin(DensityUtil.dip2px(5));
        wld.setBackgroundColor(0);
        return wld;
    }

    private void addViewPager(){
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (mOnScrollListener != null) {
                    if (positionOffset != 0) {
                        mOnScrollListener.onScroll();
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                mWaterLightView.setCurrentPosition(mViewPager.getCurrentItem() % pageCount);
                currentPosition = mWaterLightView.getCurrentPosition();
                if (mOnItemSelectListener != null) {
                    mOnItemSelectListener.onItemSelect(currentPosition, GalleryView.this);
                }
                GalleryView.this.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                    isScrolling = true;
                    stop();
                } else if (state == ViewPager.SCROLL_STATE_SETTLING) {
                    stop();
                    isScrolling = true;
                } else if (state == ViewPager.SCROLL_STATE_IDLE) {
                    if (mOnStateChangeListener != null) {
                        mOnStateChangeListener.onStateChange(state);
                    }
                    isScrolling = false;
                    if (!checkEnd()) {
                        start();
                    }
                }
            }
        });
        mViewPager.setBackgroundResource(android.R.color.holo_blue_bright);
        addView(mViewPager, 0);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(isLoop ? 200 * pageCount + currentPosition : currentPosition, false);
        Log.i(TAG,mViewPager.getAdapter().getCount() + "");
    }

    protected void onPageSelected(int position) {

    }

    public void start(){
        if(allowAuto && mWaterLightView != null){
            mWaterLightView.start(duration);
        }
    }

    public void stop() {
        if(mWaterLightView != null){
            mWaterLightView.stop();
        }
    }

    public View getItemView(int position) {
        if(position < pageCount){
            return views.get(position);
        }
        return null;
    }

    public interface OnItemClickListener{
        void onItemClick(final int position, View v);
    }

    public interface OnAutoStopListener{
        void onAutoStop(View v);     //非循环情况下，自动滚动到末端结束时回调
    }

    public interface OnItemSelectListener{
        void onItemSelect(int position, View v);
    }

    private OnItemClickListener mOnItemClickListener;

    private OnAutoStopListener mOnAutoStopListener;

    private OnItemSelectListener mOnItemSelectListener;

    public void setOnItemClickListener(OnItemClickListener l){
        mOnItemClickListener = l;
    }

    public void setOnStopListener(OnAutoStopListener l){
        mOnAutoStopListener = l;
    }

    public void setOnItemSelectListener(OnItemSelectListener l){
        mOnItemSelectListener = l;
    }

    public interface OnStateChangeListener {
        void onStateChange(final int state);
    }

    public interface OnScrollListener {
        void onScroll();
    }

    public void setOnStateChangeListener(OnStateChangeListener onStateChangeListener) {
        mOnStateChangeListener = onStateChangeListener;
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        mOnScrollListener = onScrollListener;
    }

    private OnScrollListener mOnScrollListener;

    private OnStateChangeListener mOnStateChangeListener;

    public void changeView(boolean shouldUp) {
        int position = currentPosition;
        if (shouldUp) {
            mViewPager.setCurrentItem(position - 1);
        } else {
            mViewPager.setCurrentItem(position + 1);
        }
    }

    private class FixedSpeedScroller extends Scroller {
        private int mDuration = 0;

        public FixedSpeedScroller(Context context) {
            super(context);
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        public void setmDuration(int time) {
            mDuration = time;
        }

        public int getmDuration() {
            return mDuration;
        }

    }

    public void setViewPagerScrollSpeed(int duration) {
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(mViewPager.getContext(), new AccelerateInterpolator());
            scroller.setmDuration(duration);
            mScroller.set(mViewPager, scroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
