package com.zxz.www.base.utils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by igola-xiangzi on 2016/1/22.
 */
public class MyTask {
    private Timer mTimer;
    private TimerTask mTimerTask;
    private int mDelay;
    private int mPeriod;

    public MyTask(){
        mTimer = new Timer(true);
    }

    public void start(TimerTask  timerTask,int delay,int period){
        if (mTimer != null) {
            stop();
            mDelay = delay;
            mPeriod = period;
            mTimerTask = timerTask;
            mTimer.schedule(mTimerTask, mDelay, mPeriod);
        }
    }

    public void stop(){
          if(mTimerTask != null) {
              mTimerTask.cancel();
              mTimerTask = null;
          }
    }
}


