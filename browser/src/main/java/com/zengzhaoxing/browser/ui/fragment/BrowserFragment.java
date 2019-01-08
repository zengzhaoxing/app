package com.zengzhaoxing.browser.ui.fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    @BindView(R.id.ahead_iv)
    ImageView aheadIv;
    @BindView(R.id.reload_tv)
    SharpTextView reloadTv;
    @BindView(R.id.title_tv)
    TextView titleTv;

    Unbinder unbinder;

    private String mOriginUrl = DEFAULT_WEB;

    private String mCurrUrl;

    MainActivity mMainActivity;

    //是否是重定向
    private long lastTime = 0;

    private int duration = 1500;

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
                    long time = System.currentTimeMillis();
                    if (time - lastTime > duration && !StringUtil.isEqual(url, view.getUrl())) {
                       openNew(url);
                    } else {
                        mCurrUrl = url;
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
            mOriginUrl = bundle.getString(DEFAULT_WEB);
        }
        lastTime = System.currentTimeMillis();
        mCurrUrl = mOriginUrl;
        webView.loadUrl(mOriginUrl);
        aheadIv.setEnabled(!mMainActivity.mFragmentStack.isEmpty());

        webView.setOnLongClickListener(this);

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
    protected void onTopFragmentExit(Class<? extends BaseFragment> topFragmentClass, final Bundle params) {
        if (topFragmentClass == BrowserFragment.class) {
            aheadIv.setEnabled(!mMainActivity.mFragmentStack.isEmpty());
        } else if (topFragmentClass == SearchFragment.class && params != null) {
            errorLl.post(new Runnable() {
                @Override
                public void run() {
                    openNew(params.getString(DEFAULT_WEB));
                }
            });

        }
    }

    @Override
    protected Bundle onExit() {
        mMainActivity.mFragmentStack.add(this);
        return super.onExit();
    }

    @OnClick({R.id.refresh_tv, R.id.reload_tv, R.id.back_iv, R.id.ahead_iv, R.id.home_iv, R.id.window_tv,R.id.menu_iv,R.id.search_srl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.refresh_tv:
            case R.id.reload_tv:
                webView.loadUrl(mOriginUrl);
                break;
            case R.id.back_iv:
                mBaseActivity.onBackPressed();
                break;
            case R.id.ahead_iv:{
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
            case R.id.menu_iv:
                break;
            case R.id.search_srl:
                SearchFragment fragment = new SearchFragment();
                Bundle bundle = new Bundle();
                if (StringUtil.isEqual(mOriginUrl, mCurrUrl)) {
                    bundle.putString(DEFAULT_WEB, getKeyWord(mOriginUrl));
                } else {
                    bundle.putString(DEFAULT_WEB, mCurrUrl);
                }
                fragment.setArguments(bundle);
                mMainActivity.openNewFragment(fragment);
                break;
        }
    }

    private void openNew(String url) {
        BrowserFragment fragment = new BrowserFragment();
        Bundle bundle = new Bundle();
        bundle.putString(DEFAULT_WEB, url);
        fragment.setArguments(bundle);
        mMainActivity.mFragmentStack.removeAllElements();
        mBaseActivity.openNewFragment(fragment);
    }

    @Override
    public boolean onLongClick(View v) {
        WebView.HitTestResult result = webView.getHitTestResult();
        if (null == result)
            return false;
        int type = result.getType();
        String url = result.getExtra();
        switch (type) {
            case WebView.HitTestResult.EDIT_TEXT_TYPE: // 选中的文字类型
                break;
            case WebView.HitTestResult.PHONE_TYPE: // 处理拨号
                break;
            case WebView.HitTestResult.EMAIL_TYPE: // 处理Email
                break;
            case WebView.HitTestResult.GEO_TYPE: // 　地图类型
                break;
            case WebView.HitTestResult.SRC_ANCHOR_TYPE: // 超链接
                FunListFragment.open(mMainActivity,url,FunListFragment.FUN_OPEN_IN_BACKGROUND,
                        FunListFragment.FUN_OPEN_IN_NEW_WINDOW,FunListFragment.FUN_COPY_URL, FunListFragment.FUN_SHARE_URL);
                break;
            case WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE: // 带有链接的图片类型
            case WebView.HitTestResult.IMAGE_TYPE: // 处理长按图片的菜单项
                FunListFragment.open(mMainActivity,url,FunListFragment.FUN_SAVE_IMG,FunListFragment.FUN_SHARE_IMG,FunListFragment.FUN_LOOK_IMG);
                break;
        }
        return false;
    }
}
