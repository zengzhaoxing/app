package com.zxz.www.base.utils;

import android.os.Looper;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

/**
 * Created by igola-xiangzi on 2016/1/22.
 */
public class MyTask {

    private Timer mTimer;

    private TimerTask mTimerTask;

    private Runnable mRunnable;

    public MyTask(){
        mTimer = new Timer(true);
    }

    public void start(Runnable runnable,int delay,int period){
        if (mTimer != null) {
            stop();
            mRunnable = runnable;
            mTimerTask = new MyTimerTask();
            mTimer.schedule(mTimerTask, delay, period);
        }
    }

    public void stop(){
          if(mTimerTask != null) {
              mTimerTask.cancel();
              mTimerTask = null;
          }
    }

    public class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            new android.os.Handler(Looper.getMainLooper()).post(mRunnable);
        }
    }
}


