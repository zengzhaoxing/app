package com.zengzhaoxing.browser.ui.fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zengzhaoxing.browser.MainActivity;
import com.zengzhaoxing.browser.R;
import com.zengzhaoxing.browser.view.MyWebView;
import com.zhaoxing.view.sharpview.SharpTextView;
import com.zxz.www.base.app.BaseFragment;
import com.zxz.www.base.utils.ResUtil;
import com.zxz.www.base.utils.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.zengzhaoxing.browser.Constants.BLANK;
import static com.zengzhaoxing.browser.Constants.DEFAULT_WEB;
import static com.zengzhaoxing.browser.Constants.HTTP;
import static com.zengzhaoxing.browser.Constants.HTTPS;
import static com.zengzhaoxing.browser.Constants.getKeyWord;

public class BrowserFragment extends BaseFragment implements View.OnLongClickListener {


    @BindView(R.id.web_view)
    MyWebView webView;
    @BindView(R.id.search_pb)
    ProgressBar searchPb;
    @BindView(R.id.error_ll)
    LinearLayout errorLl;
    @BindView(R.id.title_tv)
    TextView titleTv;

    BrowserFragment mPreBrowserFragment;

    WindowFragment mWindowFragment;

    Unbinder unbinder;

    private String mOriginUrl = DEFAULT_WEB;

    private String mAlternativeUrl;

    //是否是重定向
    private long lastTime = 0;

    private int duration = 1500;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fra_main_browser, container, false);
        unbinder = ButterKnife.bind(this, view);
        mWindowFragment = (WindowFragment) getParentFragment();
        //该方法解决的问题是打开浏览器不调用系统浏览器，直接用webview打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if ((url.startsWith(HTTP) || url.startsWith(HTTPS))) {
                    long time = System.currentTimeMillis();
                    if (time - lastTime > duration && !StringUtil.isEqual(url, BLANK)) {
                        mWindowFragment.openNew(new String[]{url, null});
                    } else {
                        view.loadUrl(url);
                    }
                    lastTime = time;
                }
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (titleTv != null) {
                    if (mWindowFragment.isHome()) {
                        titleTv.setText(R.string.input_key_work);
                        titleTv.setTextColor(ResUtil.getColor(R.color.text_gray));
                    } else {
                        titleTv.setText(url);
                        titleTv.setTextColor(ResUtil.getColor(R.color.text_black));
                    }
                    errorLl.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageFinished(WebView view, final String url) {
                super.onPageFinished(view, url);
                if (titleTv != null) {
                    if (mWindowFragment.isHome()) {
                        titleTv.setText(R.string.input_key_work);
                        titleTv.setTextColor(ResUtil.getColor(R.color.text_gray));
                    } else {
                        titleTv.setText(view.getTitle());
                        titleTv.setTextColor(ResUtil.getColor(R.color.text_black));
                    }
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
        webView.setHome(mWindowFragment.isHome());
        mOriginUrl = getArguments().getStringArray(DEFAULT_WEB)[0];
        mAlternativeUrl = getArguments().getStringArray(DEFAULT_WEB)[1];
        lastTime = System.currentTimeMillis();
        webView.loadUrl(mOriginUrl);
        webView.setOnLongClickListener(this);
        return view;
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
    protected void onTopFragmentExit(Class<? extends BaseFragment> topFragmentClass, final Bundle params) {
        if (topFragmentClass == SearchFragment.class && params != null) {
            errorLl.post(new Runnable() {
                @Override
                public void run() {
                    mWindowFragment.openNew(params.getStringArray(DEFAULT_WEB));
                }
            });
        }
    }

    @Override
    protected Bundle onExit() {
        return super.onExit();
    }

    @OnClick({R.id.refresh_tv, R.id.error_ll, R.id.search_srl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.refresh_tv:
            case R.id.error_ll:
                webView.clearHistory();
                webView.reload();
                break;
            case R.id.search_srl:
                SearchFragment fragment = new SearchFragment();
                Bundle bundle = new Bundle();
                bundle.putString(DEFAULT_WEB, getKeyWord(mOriginUrl));
                fragment.setArguments(bundle);
                mBaseActivity.openNewFragment(fragment);
                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        Message message = Message.obtain();
        message.setTarget(new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg != null && msg.getData() != null && webView != null) {
                    FunListFragment.open(mBaseActivity, msg.getData());
                }
                return false;
            }
        }));
        webView.requestFocusNodeHref(message);
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
