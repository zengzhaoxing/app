package com.zxz.www.base.utils;

import android.os.Handler;
import java.util.TimerTask;


public class SpecialTask {

    public final static int FOREVER = -100;
    private final int PERIOD;
    private final int ALL_TIME;
    private MyTask mMyTask;
    private int mCurrentTime;
    private OnEndListener mOnEndListener;
    private OnPeriodListener onPeriodListener;
    private Handler mHandler;

    private boolean mIsStart;

    /**
     *
     * @param handler           相应UI线程的activity
     * @param onEndListener      allTime结束监听器 ，Task结束时触发onEnd（），手动调用stop时不会触发onEnd（）
     * @param onPeriodListener   过period后监听器，隔period秒触发一次onPeriod（）
     * @param period             隔period秒触发一次onPeriod（）,以毫秒为单位
     * @param allTime            如果该值为 FOREVER,则要手动调用stop（）停止Task，以毫秒为单位
     */
    public SpecialTask(final Handler handler, OnEndListener onEndListener, OnPeriodListener onPeriodListener, int period, int allTime){
        mMyTask = new MyTask();
        mOnEndListener = onEndListener;
        this.onPeriodListener = onPeriodListener;
        mHandler = handler;
        PERIOD = period;
        ALL_TIME = allTime;
    }

    public SpecialTask(OnEndListener onEndListener, OnPeriodListener onPeriodListener, int period, int allTime){
        this(new Handler(), onEndListener, onPeriodListener, period, allTime);
    }

    public static SpecialTask newOneTimeTask(OnPeriodListener finishListener){
        return new SpecialTask(new Handler(),null,finishListener,1,1);
    }

    public static SpecialTask newForeverTask(OnPeriodListener onPeriodListener ,int time){
        return new SpecialTask(new Handler(),null,onPeriodListener,time,FOREVER);
    }

    public void start(){
        startDelay(0);
    }

    public void startDelay(int delay){

        mCurrentTime = ALL_TIME;
        mMyTask.start(new TimerTask(){
            @Override
            public void run() {
                if(mHandler == null) return;
                mIsStart = true;
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mCurrentTime >= PERIOD) {
                            mCurrentTime -= PERIOD;
                            if (onPeriodListener != null){
                                onPeriodListener.onPeriod(mCurrentTime);
                            }
                        } else if (0 <= mCurrentTime && mCurrentTime < PERIOD) {
                            if (mOnEndListener != null) {
                                mOnEndListener.onEnd();
                            }
                            mCurrentTime = -1;
                            mMyTask.stop();
                        } else if (mCurrentTime == FOREVER) {
                            if (onPeriodListener != null)
                                onPeriodListener.onPeriod(mCurrentTime);
                        }
                    }
                });
            }
        },delay,PERIOD);
    }

    public void stop(){
        mIsStart = false;
        mMyTask.stop();
    }

    public boolean isStart(){
        return mIsStart;
    }

    public interface OnPeriodListener {
        void onPeriod(int currentTime);
    }

    public interface OnEndListener {
        void onEnd();
    }
}


