package com.zengzhaoxing.browser.view.web;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.GeolocationPermissions;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.zengzhaoxing.browser.MainActivity;
import com.zengzhaoxing.browser.R;
import com.zengzhaoxing.browser.ui.dialog.NoticeDialog;
import com.zxz.www.base.utils.NetUtil;
import com.zxz.www.base.utils.PermissionUtil;
import com.zxz.www.base.utils.ResUtil;
import com.zxz.www.base.utils.StringUtil;
import com.zxz.www.base.view.LoadingView;

public class MyWebView extends WebView implements NetUtil.OnNetSpeedListener {

    private MainActivity mActivity;

    private LoadingView mLoadingView;


    /**
     * 视频全屏参数
     */
    protected static final FrameLayout.LayoutParams COVER_SCREEN_PARAMS = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    private View customView;
    private FrameLayout fullscreenContainer;
    private WebChromeClient.CustomViewCallback customViewCallback;

    public MyWebView(Context context) {
        super(context);
        init();
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        NetUtil.unRegisterNetSpeed(this);
    }

    @Override
    public boolean canGoBack() {
        return customView != null;
    }

    @Override
    public void goBack() {
        if (customView != null) {
            hideCustomView();
        } else {
            super.goBack();
        }
    }

    private void init() {
        mActivity = (MainActivity) getContext();
        mLoadingView = new LoadingView(mActivity);
        mLoadingView.setBackgroundResource(R.color.transparent);
        mLoadingView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        NetUtil.registerNetSpeed(this);
        WebSettings webSettings = getSettings();
        webSettings.setDomStorageEnabled(true);//主要是这句
        webSettings.setJavaScriptEnabled(true);//启用js
        webSettings.setBlockNetworkImage(false);//解决图片不显示
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setUseWideViewPort(true); // 关键点
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setSupportZoom(true); // 支持缩放
        webSettings.setLoadWithOverviewMode(true);
//        webSettings.setUserAgentString(ResUtil.getString(R.string.browser_name));
        webSettings.setSaveFormData(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setSavePassword(true);
        super.setWebChromeClient(new WebChromeClient());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

//        String agent = webSettings.getUserAgentString();
//        webSettings.setUserAgentString(agent.replace("Chrome", ResUtil.getString(R.string.browser_name)));

        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        //该方法解决的问题是打开浏览器不调用系统浏览器，直接用webview打开
        super.setWebChromeClient(new WebChromeClient() {



            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 10) {
                    newProgress += 1;
                }
                mClient.onProgressChanged(view,newProgress);
            }

            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                showCustomView(view, callback);
            }

            @Override
            public void onHideCustomView() {
                hideCustomView();
            }

            @Nullable
            @Override
            public View getVideoLoadingProgressView() {
                return mLoadingView;
            }


            @Override
            public void onReceivedTitle(WebView view, String title) {
                if (mClient != null) {
                    mClient.onReceivedTitle(view,title);
                }
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(final String origin, final GeolocationPermissions.Callback callback) {
                PermissionUtil.requestPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION, new PermissionUtil.OnPermissionRequest() {
                    @Override
                    public void onResult(boolean isAllow) {
                        NoticeDialog noticeDialog = new NoticeDialog(mActivity);
                        noticeDialog.show("允许“" + origin + "”" + "访问您当前的地理位置信息", new NoticeDialog.OnDialogClickListener() {
                            @Override
                            public void onOkClick() {
                                callback.invoke(origin, true, true);
                            }
                        });
                    }
                });
            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onPermissionRequest(PermissionRequest request) {
                request.grant(request.getResources());
            }

            // For Android 3.0+
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                ActivityLifeCycle.getInstance().openFileChooser(uploadMsg,acceptType);
            }

            // For Android < 3.0
            public void openFileChooser(ValueCallback<Uri> uploadMsgs) {
                ActivityLifeCycle.getInstance().openFileChooser(uploadMsgs);
            }

            // For Android  > 4.1.1
           //    @Override
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                ActivityLifeCycle.getInstance().openFileChooser(uploadMsg,acceptType,capture);
            }

            // For Android  >= 5.0
            @Override
            public boolean onShowFileChooser(WebView webView,
                                             ValueCallback<Uri[]> filePathCallback,
                                             WebChromeClient.FileChooserParams fileChooserParams) {
                ActivityLifeCycle.getInstance().onShowFileChooser(webView,filePathCallback,fileChooserParams);
                return true;
            }
        });
    }

    private WebChromeClient mClient;

    @Override
    public void setWebChromeClient(WebChromeClient client) {
        mClient = client;
    }


    /**
     * 视频播放全屏
     **/
    private void showCustomView(View view, WebChromeClient.CustomViewCallback callback) {
        // if a view already exists then immediately terminate the new one

        if (customView != null) {
            callback.onCustomViewHidden();
            return;
        }
        mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        FrameLayout decor = (FrameLayout) mActivity.getWindow().getDecorView();
        decor.setBackgroundResource(android.R.color.black);
        fullscreenContainer = new FullscreenHolder(mActivity);
        fullscreenContainer.addView(view, COVER_SCREEN_PARAMS);
        decor.addView(fullscreenContainer, COVER_SCREEN_PARAMS);
        customView = view;
        setStatusBarVisibility(false);
        setVisibility(View.INVISIBLE);
        customViewCallback = callback;
    }

    /**
     * 隐藏视频全屏
     */
    private void hideCustomView() {
        if (customView == null) {
            return;
        }
        mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setStatusBarVisibility(true);
        FrameLayout decor = (FrameLayout) mActivity.getWindow().getDecorView();
        decor.removeView(fullscreenContainer);
        fullscreenContainer = null;
        customView = null;
        customViewCallback.onCustomViewHidden();
        setVisibility(View.VISIBLE);
    }

    @Override
    public void onSpeed(String speed) {
        mLoadingView.setLoadingText(speed);
    }


    /**
     * 全屏容器界面
     */
    static class FullscreenHolder extends FrameLayout {

        public FullscreenHolder(Context ctx) {
            super(ctx);
            setBackgroundColor(ctx.getResources().getColor(android.R.color.black));
        }

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouchEvent(MotionEvent evt) {
            return true;
        }
    }

    private void setStatusBarVisibility(boolean visible) {
        int flag = visible ? 0 : WindowManager.LayoutParams.FLAG_FULLSCREEN;
        mActivity.getWindow().setFlags(flag, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


}
