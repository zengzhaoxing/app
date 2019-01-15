//package com.zengzhaoxing.browser.view.web;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.content.Context;
//import android.content.pm.ActivityInfo;
//import android.graphics.Bitmap;
//import android.support.annotation.Nullable;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.webkit.WebChromeClient;
//import android.webkit.WebView;
//import android.widget.FrameLayout;
//
//public class MyWebChromeClient extends WebChromeClient {
//
//    public MyWebChromeClient(Activity activity, MyWebView myWebView) {
//        mActivity = activity;
//        mMyWebView = myWebView;
//    }
//
//    private Activity mActivity;
//
//    private MyWebView mMyWebView;
//
//
//
//    @Override
//    public void onProgressChanged(WebView view, int newProgress) {
//        if (mProgressBar == null) {
//            return;
//        }
//        if (newProgress == 100) {
//            mProgressBar.setVisibility(View.GONE);
//            mProgressBar.setProgress(0);
//        } else {
//            mProgressBar.setVisibility(View.VISIBLE);
//            if (newProgress < 5) {
//                newProgress = mProgressBar.getProgress() + 1;
//            }
//            mProgressBar.setProgress(newProgress);
//        }
//    }
//
//    @Override
//    public void onShowCustomView(View view, CustomViewCallback callback) {
//        showCustomView(view, callback);
//    }
//
//    @Override
//    public void onHideCustomView() {
//        hideCustomView();
//    }
//
//    @Nullable
//    @Override
//    public View getVideoLoadingProgressView() {
//        return mLoadingView;
//    }
//
//    /**
//     * 视频播放全屏
//     **/
//    private void showCustomView(View view, WebChromeClient.CustomViewCallback callback) {
//        // if a view already exists then immediately terminate the new one
//
//        if (customView != null) {
//            callback.onCustomViewHidden();
//            return;
//        }
//        mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        FrameLayout decor = (FrameLayout) mActivity.getWindow().getDecorView();
//        decor.setBackgroundResource(android.R.color.black);
//        fullscreenContainer = new FullscreenHolder(mActivity);
//        fullscreenContainer.addView(view, COVER_SCREEN_PARAMS);
//        decor.addView(fullscreenContainer, COVER_SCREEN_PARAMS);
//        customView = view;
//        setStatusBarVisibility(false);
//        setVisibility(View.INVISIBLE);
//        customViewCallback = callback;
//    }
//
//    /**
//     * 隐藏视频全屏
//     */
//    private void hideCustomView() {
//        if (customView == null) {
//            return;
//        }
//        mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        setStatusBarVisibility(true);
//        FrameLayout decor = (FrameLayout) mActivity.getWindow().getDecorView();
//        decor.removeView(fullscreenContainer);
//        fullscreenContainer = null;
//        customView = null;
//        customViewCallback.onCustomViewHidden();
//        setVisibility(View.VISIBLE);
//    }
//
//
//    /**
//     * 全屏容器界面
//     */
//    static class FullscreenHolder extends FrameLayout {
//
//        public FullscreenHolder(Context ctx) {
//            super(ctx);
//            setBackgroundColor(ctx.getResources().getColor(android.R.color.black));
//        }
//
//        @SuppressLint("ClickableViewAccessibility")
//        @Override
//        public boolean onTouchEvent(MotionEvent evt) {
//            return true;
//        }
//    }
//
//    private void setStatusBarVisibility(boolean visible) {
////        int flag = visible ? 0 : WindowManager.LayoutParams.FLAG_FULLSCREEN;
////        mActivity.getWindow().setFlags(flag, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//    }
//
//    /**
//     * 视频全屏参数
//     */
//    protected static final FrameLayout.LayoutParams COVER_SCREEN_PARAMS = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//    private View customView;
//    private FrameLayout fullscreenContainer;
//    private WebChromeClient.CustomViewCallback customViewCallback;
//
//
//
//}
