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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zengzhaoxing.browser.MainActivity;
import com.zengzhaoxing.browser.R;
import com.zengzhaoxing.browser.bean.BaiduSuggestion;
import com.zengzhaoxing.browser.net.BaiduParser;
import com.zengzhaoxing.browser.ui.adapter.BDSuggestionAdapter;
import com.zengzhaoxing.browser.view.MyWebView;
import com.zhaoxing.view.sharpview.SharpTextView;
import com.zxz.www.base.app.BaseFragment;
import com.zxz.www.base.app.MainFragment;
import com.zxz.www.base.model.ResponseModel;
import com.zxz.www.base.net.request.JsonRequester;
import com.zxz.www.base.net.request.OnResponseListener;
import com.zxz.www.base.net.request.RequestUtil;
import com.zxz.www.base.net.request.RequesterFactory;
import com.zxz.www.base.net.request.okhttp.OkHttpRequesterFactory;
import com.zxz.www.base.utils.KeyBoardUtil;
import com.zxz.www.base.utils.StringUtil;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.zengzhaoxing.browser.Constants.BLANK;
import static com.zengzhaoxing.browser.Constants.DEFAULT_WEB;
import static com.zengzhaoxing.browser.Constants.HTTP;
import static com.zengzhaoxing.browser.Constants.HTTPS;

public class BrowserFragment extends BaseFragment {


    @BindView(R.id.web_view)
    MyWebView webView;
    @BindView(R.id.search_pb)
    ProgressBar searchPb;
    @BindView(R.id.error_ll)
    LinearLayout errorLl;
    @BindView(R.id.ahead_iv)
    ImageView aheadIv;
    @BindView(R.id.reload_tv)
    SharpTextView reloadTv;
    @BindView(R.id.title_tv)
    TextView titleTv;

    Unbinder unbinder;

    private String mUrl = DEFAULT_WEB;

    MainActivity mMainActivity;

    //是否是重定向
    private boolean mRedirect;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fra_main_browser, container, false);
        unbinder = ButterKnife.bind(this, view);
        mMainActivity = (MainActivity) mBaseActivity;
        //该方法解决的问题是打开浏览器不调用系统浏览器，直接用webview打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if ((url.startsWith(HTTP) || url.startsWith(HTTPS))) {
                    if (!mRedirect && StringUtil.isEqual(mUrl, url)) {
                        BrowserFragment fragment = new BrowserFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString(DEFAULT_WEB, url);
                        fragment.setArguments(bundle);
                        mMainActivity.mFragmentStack.removeAllElements();
                        mBaseActivity.openNewFragment(fragment);
                    } else {
                        view.loadUrl(url);
                    }
                }
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mRedirect = true;
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRedirect = false;
                    }
                }, 500);
                if (titleTv != null) {
                    errorLl.setVisibility(View.GONE);
                    titleTv.setText(url);
                }
            }

            @Override
            public void onPageFinished(WebView view, final String url) {
                super.onPageFinished(view, url);
                if (titleTv != null) {
                    titleTv.setText(view.getTitle());
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                if (errorLl != null) {
                    view.loadUrl(BLANK);
                    errorLl.setVisibility(View.VISIBLE);
                }
            }
        });
        webView.setProgressBar(searchPb);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(DEFAULT_WEB)) {
            mUrl = bundle.getString(DEFAULT_WEB);
        }
        webView.loadUrl(mUrl);
        aheadIv.setImageResource(mMainActivity.mFragmentStack.isEmpty() ? R.drawable.arrow_right_gray : R.drawable.arrow_right_blue);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    protected boolean handleBackEvent() {
        if (webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return false;
    }

    @Override
    protected void onTopFragmentExit(Class<? extends BaseFragment> topFragmentClass, Bundle params) {
        if (topFragmentClass == BrowserFragment.class) {
            aheadIv.setImageResource(mMainActivity.mFragmentStack.isEmpty() ? R.drawable.arrow_right_gray : R.drawable.arrow_right_blue);
        } else if (topFragmentClass == SearchFragment.class && params != null) {
            mUrl = params.getString(DEFAULT_WEB);
            webView.loadUrl(mUrl);
        }
    }

    @Override
    protected Bundle onExit() {
        mMainActivity.mFragmentStack.add(this);
        return super.onExit();
    }

    @OnClick({R.id.refresh_tv, R.id.reload_tv, R.id.back_iv, R.id.ahead_iv, R.id.home_iv, R.id.window_tv,R.id.search_srl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.refresh_tv:
            case R.id.reload_tv:
                webView.loadUrl(mUrl);
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
                mMainActivity.mFragmentStack.removeAllElements();
                break;
            case R.id.window_tv:
                break;
            case R.id.search_srl:
                SearchFragment fragment = new SearchFragment();
                Bundle bundle = new Bundle();
                bundle.putString(DEFAULT_WEB, mUrl);
                fragment.setArguments(bundle);
                mMainActivity.openNewFragment(fragment);
                break;
        }
    }

}
