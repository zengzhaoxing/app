package com.zxz.www.base.view;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.zxz.www.base.utils.DensityUtil;
import com.zxz.www.base.utils.ViewUtil;


/**
 * Created by zengxianzi on 2016/8/1.
 */
abstract public class PullToRefreshLayout extends FrameLayout {

    //松手后可刷新的最小高度
    protected int mEnoughRefreshHeight = DensityUtil.dip2px(50);

    //下拉阻尼比
    private static final float MOVE_FACTOR = 0.8f;

    //当前下拉高度
    private float mPullOffset = 0;

    //上次Touch事件的x坐标
    private float mLastX = 0;

    //上次Touch事件的y坐标
    private float mLastY = 0;

    //是否正在刷新中
    private boolean mIsRefreshing = false;

    //是否开启下拉刷新功能
    private boolean mPullEnable = true;

    //子view中的列表控件（若有）
    private ViewGroup mListView;

    //PullToRefreshLayout的唯一子控件，PullToRefreshLayout在布局时只能含有一个控件
    View mContextView;

    //正在刷新时要显示的控件，要在初始化头部时时赋值
    View mRefreshView;

    //头部初始化（根据需要定制头部）
    abstract protected void initHeaher();

    //根据刷新状态刷新头部
    abstract protected void renderHeader(boolean canUpRefresh);

    public boolean isPullEnable() {
        return mPullEnable;
    }

    public void setPullEnable(boolean pullEnable) {
        mPullEnable = pullEnable;
    }

    public boolean isRefreshing() {
        return mIsRefreshing;
    }

    public PullToRefreshLayout(Context context) {
        super(context);
        initHeaher();
    }

    public PullToRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeaher();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (getChildCount() > 2) {
            try {
                throw new Exception("child view more than 2,you can add only one child view additional");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mContextView = getChildAt(1);
        mContextView.bringToFront();
        if (mContextView instanceof ViewGroup) {
            findListView((ViewGroup) mContextView);
        }
    }

    private void findListView(ViewGroup viewGroup) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof ListView || view instanceof RecyclerView) {
                mListView = (ViewGroup) view;
                break;
            } else if (view instanceof ViewGroup) {
                findListView((ViewGroup) view);
                if (mListView != null) {
                    break;
                }
            }
        }
    }

    public PullToRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaher();
    }

    boolean mIntercept;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mIsRefreshing) {
            return false;
        }
        if (!mPullEnable) {
            return super.dispatchTouchEvent(ev);
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                ViewUtil.setChildrenLongClickEnable(mListView,true);
                mIntercept = false;
                mLastX = (int) ev.getX();
                mLastY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY = 0;
                if (Math.abs(ev.getX() - mLastX) < Math.abs(ev.getY() - mLastY)) {
                    moveY = ev.getY() - mLastY;
                }
                if (Math.abs(moveY) > 3) {
                    moveY *= MOVE_FACTOR;
                    if (moveY > 0 && mPullOffset > 200) {
                        moveY = moveY * 140 / mPullOffset;
                    } else if (moveY > 0 && mPullOffset > 150) {
                        moveY = moveY * 0.7f;
                    } else if (moveY > 0 && mPullOffset > 100) {
                        moveY = moveY * 0.8f;
                    }
                    mPullOffset += moveY;
                }
                mLastX = (int) ev.getX();
                mLastY = (int) ev.getY();
                if (mListView != null && mListView.getScrollY() >= 0) {
                    mPullOffset = 0;
                }
                mIntercept = mPullOffset > 3;
                if (mIntercept) {
                    move((int) mPullOffset);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!mIntercept) {
                    mLastX = 0;
                    mLastY = 0;
                    mPullOffset = 0;
                    return super.dispatchTouchEvent(ev);
                }
                if (mContextView != null && isEnoughToRefresh()) {
                    setIsRefreshing(true);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (mOnRefreshListener != null && mRefreshView.getVisibility() == VISIBLE) {
                                mOnRefreshListener.onRefresh();
                            }
                        }
                    }, 1000);
                } else {
                    setIsRefreshing(false);
                }
                mLastX = 0;
                mLastY = 0;
                mPullOffset = 0;
                break;
        }
        if (mIntercept) {
            return mIntercept;
        }
        return super.dispatchTouchEvent(ev);
    }

    private synchronized void setIsRefreshing(boolean isRefreshing) {
        if (mContextView == null) {
            return;
        }
        mIsRefreshing = isRefreshing;
        if (mIsRefreshing) {
            move(mEnoughRefreshHeight);
            mRefreshView.setVisibility(VISIBLE);
        } else {
            move(0);
            mRefreshView.setVisibility(INVISIBLE);
        }
    }

    public void doRefreshFinish(){
        setIsRefreshing(false);
    }

    private boolean isEnoughToRefresh() {
        return mContextView.getTranslationY() > mEnoughRefreshHeight;
    }

    private void move(int d) {
        ViewUtil.setChildrenLongClickEnable(mListView, false);//防止长按事件
        renderHeader(isEnoughToRefresh());
        mContextView.setTranslationY(d);
    }

    public interface OnRefreshListener {
        void onRefresh();
    }

    public OnRefreshListener mOnRefreshListener;

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        mOnRefreshListener = onRefreshListener;
    }

}
