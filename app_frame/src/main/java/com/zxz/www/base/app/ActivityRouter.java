package com.zxz.www.base.app;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

public class ActivityRouter extends UIRouter implements Application.ActivityLifecycleCallbacks {

    private static ActivityRouter mActivityRouter;

    public static ActivityRouter getRouter() {
        if (mActivityRouter == null) {
            mActivityRouter = new ActivityRouter();
        }
        return mActivityRouter;
    }

    private ActivityRouter() {

    }

    @Override
    public void openPage(Class<? extends UIPage> clz, Bundle bundle) {
        Activity activity = (Activity) getCurrentPage();
        Intent intent = new Intent(activity,clz);
        if (bundle != null) {
            intent.putExtra(ActivityRouter.class.getName(), bundle);
        }
        activity.startActivity(intent);
    }

    @Override
    protected void closeCurrentPageImpl() {
        ((Activity)getCurrentPage()).finish();
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        mUIPages.push((UIPage) activity);
        if (mUIPages.size() == 1) {
            onRouterReady((UIPage) activity);
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        mUIPages.pop();
        if (mUIPages.size() == 0) {
            onRouterDestroy();
        }
    }
}
