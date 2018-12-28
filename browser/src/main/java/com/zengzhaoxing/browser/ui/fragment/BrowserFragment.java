package com.zengzhaoxing.browser.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zengzhaoxing.browser.MainActivity;
import com.zengzhaoxing.browser.R;
import com.zhaoxing.view.sharpview.SharpTextView;
import com.zxz.www.base.app.BaseFragment;
import com.zxz.www.base.app.MainFragment;
import com.zxz.www.base.model.ResponseModel;
import com.zxz.www.base.net.request.OnResponseListener;
import com.zxz.www.base.net.request.RequestUtil;
import com.zxz.www.base.utils.KeyBoardUtil;
import com.zxz.www.base.utils.StringUtil;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class BrowserFragment extends BaseFragment {

    private static final String DEFAULT_WEB = "https://m.baidu.com/";

    private static final String SEARCH_EXTRA = "https://www.baidu.com/s?ie=UTF-8&wd=";

    private static final String HTTP = "http://";

    private static final String HTTPS = "https://";

    private static final String BAI_DU_SEARCH = "http://suggestion.baidu.com/su?wd=";

    private static final String BLANK = "about:blank";


    @BindView(R.id.web_view)
    WebView webView;
    Unbinder unbinder;
    @BindView(R.id.search_et)
    EditText searchEt;
    @BindView(R.id.search_tv)
    TextView searchTv;
    @BindView(R.id.search_pb)
    ProgressBar searchPb;
    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.search_iv)
    ImageView searchIv;
    @BindView(R.id.error_ll)
    LinearLayout errorLl;
    @BindView(R.id.ahead_iv)
    ImageView aheadIv;
    @BindView(R.id.reload_tv)
    SharpTextView reloadTv;

    private String mUrl;

    MainActivity mMainActivity;



    /**
     * 视频全屏参数
     */
    protected static final FrameLayout.LayoutParams COVER_SCREEN_PARAMS = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    private View customView;
    private FrameLayout fullscreenContainer;
    private WebChromeClient.CustomViewCallback customViewCallback;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fra_main_browser, container, false);
        unbinder = ButterKnife.bind(this, view);
        mMainActivity = (MainActivity) mBaseActivity;
        WebSettings webSettings = webView.getSettings();
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 不加载缓存内容
        webView.setWebChromeClient(new WebChromeClient());//这行最好不要丢掉
        //该方法解决的问题是打开浏览器不调用系统浏览器，直接用webview打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith(HTTP) || url.startsWith(HTTPS)) {
                    BrowserFragment fragment = new BrowserFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(DEFAULT_WEB,url);
                    fragment.setArguments(bundle);
                    mMainActivity.mFragmentStack.removeAllElements();
                    mBaseActivity.openNewFragment(fragment);
                }
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                errorLl.setVisibility(View.GONE);
                if (!listView.isShown()) {
                    searchEt.setText(url);
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (!listView.isShown()) {
                    searchEt.setText(view.getTitle());
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                view.loadUrl(BLANK);
                errorLl.setVisibility(View.VISIBLE);
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (searchPb == null) {
                    return;
                }
                if (newProgress == 100) {
                    searchPb.setVisibility(View.GONE);
                    searchPb.setProgress(0);
                } else {
                    searchPb.setVisibility(View.VISIBLE);
                    if (newProgress < 5) {
                        newProgress = searchPb.getProgress() + 1;
                    }
                    searchPb.setProgress(newProgress);
                }
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
                FrameLayout frameLayout = new FrameLayout(mBaseActivity);
                frameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                return frameLayout;
            }
        });
        clearEtFocus();
        Bundle bundle = getArguments();
        String url;
        if (bundle != null && bundle.containsKey(DEFAULT_WEB)) {
            url = bundle.getString(DEFAULT_WEB);
        } else {
            url = DEFAULT_WEB;
        }
        webView.loadUrl(url);
        aheadIv.setImageResource(mMainActivity.mFragmentStack.isEmpty() ? R.drawable.arrow_right_gray : R.drawable.arrow_right_blue);
        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (listView.isShown()) {
                    String url = searchEt.getText().toString();
                    if (isHttpUrl(url)) {
                        searchTv.setText(R.string.enter);
                        mUrl = url;
                    } else if (isUrlNotHttp(url)) {
                        searchTv.setText(R.string.enter);
                        mUrl = HTTP + url;
                    } else {
                        searchTv.setText(R.string.search);
                        mUrl = SEARCH_EXTRA + url;
                        RequestUtil.get(BAI_DU_SEARCH + searchEt.getText().toString(), null, ResponseModel.class, new OnResponseListener<ResponseModel>() {
                            @Override
                            public void onResponse(ResponseModel responseModel, int i) {
                            }
                        });
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        searchEt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                listView.setVisibility(View.VISIBLE);
                if (!searchEt.hasFocus()) {
                    searchEt.setText(webView.getUrl());
                    searchEt.setSelection(0, searchEt.length());
                }
                return false;
            }
        });
        return view;
    }

    /**
     * 视频播放全屏
     **/
    private void showCustomView(View view, WebChromeClient.CustomViewCallback callback) {
        // if a view already exists then immediately terminate the new one
        mBaseActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        if (customView != null) {
            callback.onCustomViewHidden();
            return;
        }

        FrameLayout decor = (FrameLayout)mBaseActivity.getWindow().getDecorView();
        decor.setBackgroundResource(android.R.color.black);
        fullscreenContainer = new FullscreenHolder(mBaseActivity);
        fullscreenContainer.addView(view, COVER_SCREEN_PARAMS);
        decor.addView(fullscreenContainer, COVER_SCREEN_PARAMS);
        customView = view;
        setStatusBarVisibility(false);
        customViewCallback = callback;
    }

    /**
     * 隐藏视频全屏
     */
    private void hideCustomView() {
        if (customView == null) {
            return;
        }
        mBaseActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setStatusBarVisibility(true);
        FrameLayout decor = (FrameLayout) mBaseActivity.getWindow().getDecorView();
        decor.removeView(fullscreenContainer);
        fullscreenContainer = null;
        customView = null;
        customViewCallback.onCustomViewHidden();
        webView.setVisibility(View.VISIBLE);
    }

    private boolean isHttpUrl(String url) {
        return url.startsWith(HTTPS) || url.startsWith(HTTP);
    }

    private boolean isUrlNotHttp(String url) {
        return !StringUtil.isBlank(url) && url.startsWith("www.") && url.length() > 5;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        webView.destroy();
        super.onDestroy();
    }

    @Override
    protected boolean handleBackEvent() {
        if (customView != null) {
            hideCustomView();
            return true;
        } else if (listView.isShown()) {
            clearEtFocus();
            return true;
        } else if (webView.canGoBack()) {
            errorLl.setVisibility(View.GONE);
            webView.goBack();
            return true;
        }
        return false;
    }

    @Override
    protected void onTopFragmentExit(Class<? extends BaseFragment> topFragmentClass, Bundle params) {
        super.onTopFragmentExit(topFragmentClass, params);
        aheadIv.setImageResource(mMainActivity.mFragmentStack.isEmpty() ? R.drawable.arrow_right_gray : R.drawable.arrow_right_blue);
    }

    @Override
    protected Bundle onExit() {
        mMainActivity.mFragmentStack.add(this);
        return super.onExit();
    }

    @OnClick({R.id.search_tv, R.id.reload_tv,R.id.back_iv, R.id.ahead_iv, R.id.home_iv, R.id.window_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search_tv:
                if (listView.isShown() && !StringUtil.isEqual(mUrl, webView.getUrl())) {
                    webView.loadUrl(mUrl);
                } else {
                    webView.reload();
                }
                clearEtFocus();
                break;
            case R.id.reload_tv:
                webView.goBack();
                break;
            case R.id.back_iv:
                mBaseActivity.onBackPressed();
                break;
            case R.id.ahead_iv:
                if (!mMainActivity.mFragmentStack.isEmpty()) {
                    BaseFragment fragment = mMainActivity.mFragmentStack.pop();
                    mBaseActivity.openNewFragment(fragment);
                }
                break;
            case R.id.home_iv:
                mBaseActivity.goHome();
                break;
            case R.id.window_tv:
                break;
        }
    }

    private void clearEtFocus() {
        listView.setVisibility(View.GONE);
        searchTv.setText(R.string.refresh);
        searchEt.setText(webView.getTitle());
        KeyBoardUtil.closeKeyboard(searchEt);
        searchEt.clearFocus();
        webView.requestFocus();
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
        mBaseActivity.getWindow().setFlags(flag, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

}
