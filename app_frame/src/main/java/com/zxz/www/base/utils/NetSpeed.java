package com.zxz.www.base.utils;

import android.net.TrafficStats;
import android.util.Log;

import com.zxz.www.base.app.BaseApp;

public class NetSpeed {

    private static final String TAG = NetSpeed.class.getSimpleName();
    private double lastTotalRxBytes;
    private double lastTimeStamp;

    public NetSpeed() {
        lastTotalRxBytes = getTotalRxBytes();
        lastTimeStamp = System.currentTimeMillis();
    }

    public String getNetSpeed() {
        double nowTotalRxBytes = getTotalRxBytes();
        double nowTimeStamp = System.currentTimeMillis();
        double size = nowTotalRxBytes - lastTotalRxBytes;
        double time = (nowTimeStamp - lastTimeStamp) / 1000;
        double speed = size / time;
        lastTimeStamp = nowTimeStamp;
        lastTotalRxBytes = nowTotalRxBytes;
        return MathUtil.getFormatSize(speed) + "/s";
    }


    //getApplicationInfo().uid
    public long getTotalRxBytes() {
        return TrafficStats.getUidRxBytes(BaseApp.getContext().getApplicationInfo().uid) == TrafficStats.UNSUPPORTED ? 0 : TrafficStats.getTotalRxBytes();
    }


}
