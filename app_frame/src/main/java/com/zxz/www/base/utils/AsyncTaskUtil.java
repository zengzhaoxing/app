package com.zxz.www.base.utils;

import android.os.AsyncTask;
import android.os.Build;
import android.os.AsyncTask;
import android.os.Build;


public class AsyncTaskUtil {

    public static <Params, Progress, Result> void executeAsyncTask(
            AsyncTask<Params, Progress, Result> task, Params... params) {
        if (Build.VERSION.SDK_INT >= 11) {
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        } else {
            task.execute(params);
        }
    }

}
