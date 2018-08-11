package com.example.admin.fastpay.jpush;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import daemonprocess.IServiceMyAidlInterface;

public class LocalService extends Service {
    private static final String TAG = "LocalService";
    LocalServiceBainder localBainder;
    LocalServiceConnection localConn;
    private int counter=  0 ;

    @Override
    public void onCreate() {
        super.onCreate();
        if (localBainder!=null){
            localBainder = new LocalServiceBainder();
        }
        localConn = new LocalServiceConnection();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startTimer();

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
       return localBainder;
    }

    class LocalServiceConnection implements ServiceConnection {


        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG,"onServiceDisconnected");
            LocalService.this.startService(new Intent(LocalService.this,RemoteService.class));
            LocalService.this.bindService(new Intent(LocalService.this,RemoteService.class)
            ,localConn, Context.BIND_IMPORTANT);
        }
    }

    class LocalServiceBainder extends IServiceMyAidlInterface.Stub{

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
            Log.d(TAG,"basicTypes");
        }
    }

    private Timer timer;
    private TimerTask timerTask;
    long oldTime = 0;

    public void startTimer(){
        timer = new Timer();
        initTimerTask();
        timer.schedule(timerTask,1000,1000);
    }
    public void stopTimer(){
        if (timer!=null){
            timer.cancel();
            timer = null;
        }
    }
    private void initTimerTask() {
        timerTask =new TimerTask() {
            @Override
            public void run() {
                Log.i(TAG,"int timer +++++ "+(counter++));
            }
        };
    }

}
