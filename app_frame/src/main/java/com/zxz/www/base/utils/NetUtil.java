package com.zxz.www.base.utils;

import android.os.Handler;
import android.os.Message;

import com.zxz.www.base.app.BaseApp;

import java.util.ArrayList;

public class NetUtil {

    private static NetSpeedTimer TIMER;


    public static void registerNetSpeed(OnNetSpeedListener onNetSpeedListener) {
        if (onNetSpeedListener != null) {
            mOnNetSpeedListeners.add(onNetSpeedListener);
        }
        if (TIMER == null) {
            Handler handler = new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    switch (msg.what) {
                        case NetSpeedTimer.NET_SPEED_TIMER_DEFAULT:
                            for (OnNetSpeedListener listener : mOnNetSpeedListeners) {
                                listener.onSpeed((String) msg.obj);
                            }
                            return true;
                    }
                    return false;
                }
            });
            TIMER = new NetSpeedTimer(BaseApp.getContext(), new NetSpeed(), handler).setDelayTime(1000).setPeriodTime(2000);
            TIMER.startSpeedTimer();
        }
    }

    public interface OnNetSpeedListener {
        void onSpeed(String speed);
    }

    public static void unRegisterNetSpeed(OnNetSpeedListener onNetSpeedListener) {
        mOnNetSpeedListeners.remove(onNetSpeedListener);
        if (mOnNetSpeedListeners.isEmpty()) {
            TIMER.stopSpeedTimer();
            TIMER = null;
        }
    }

    private static ArrayList<OnNetSpeedListener> mOnNetSpeedListeners = new ArrayList<>();

}
