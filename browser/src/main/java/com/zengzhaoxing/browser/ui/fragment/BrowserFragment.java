package com.zengzhaoxing.browser.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zengzhaoxing.browser.Constants;
import com.zengzhaoxing.browser.MainActivity;
import com.zengzhaoxing.browser.R;
import com.zengzhaoxing.browser.bean.FileBean;
import com.zengzhaoxing.browser.bean.UrlBean;
import com.zengzhaoxing.browser.presenter.DownPresenter;
import com.zengzhaoxing.browser.presenter.UrlCollectPresenter;
import com.zengzhaoxing.browser.ui.dialog.NoticeDialog;
import com.zengzhaoxing.browser.view.web.MyWebView;
import com.zxz.www.base.utils.ResUtil;
import com.zxz.www.base.utils.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.zengzhaoxing.browser.Constants.BLANK;
import static com.zengzhaoxing.browser.Constants.HTTP;
import static com.zengzhaoxing.browser.Constants.HTTPS;
import static com.zengzhaoxing.browser.Constants.getKeyWord;

public class BrowserFragment extends WindowChildFragment implements View.OnLongClickListener, DownloadListener {


    @BindView(R.id.web_view)
    MyWebView webView;
    @BindView(R.id.search_pb)
    ProgressBar searchPb;
    @BindView(R.id.error_ll)
    LinearLayout errorLl;
    @BindView(R.id.title_tv)
    TextView titleTv;

    Unbinder unbinder;

    //是否是重定向
    private long lastTime = 0;

    private int duration = 1500;

    public UrlBean getUrlBean() {
        return mUrlBean;
    }

    private UrlBean mUrlBean;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fra_main_browser, container, false);
        unbinder = ButterKnife.bind(this, view);
        //该方法解决的问题是打开浏览器不调用系统浏览器，直接用webview打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if ((url.startsWith(HTTP) || url.startsWith(HTTPS))) {
                    long time = System.currentTimeMillis();
                    if (time - lastTime > duration && !StringUtil.isEqual(url, BLANK) && !StringUtil.isEqual(url, mUrlBean.getUrl())) {
                        mWindowFragment.openNew(url);
                    } else {
                        view.loadUrl(url);
                        if (titleTv != null) {
                            titleTv.setText(url);
                        }
                    }
                    lastTime = time;
                }
                return true;
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
        webView.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (searchPb != null) {
                    searchPb.setProgress(newProgress);
                    searchPb.setVisibility(newProgress == 100 ? View.GONE : View.VISIBLE);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                mUrlBean.setTitle(title);
                UrlCollectPresenter.getInstance().addHistory(mUrlBean);
                if (titleTv != null) {
                    if (!Constants.BLANK.equals(title)) {
                        titleTv.setText(title);
                    }
                    errorLl.setVisibility(View.GONE);
                }
            }
        });
        lastTime = System.currentTimeMillis();
        mUrlBean = (UrlBean) getArguments().getSerializable(UrlBean.class.getName());
        titleTv.setText(mUrlBean.getUrl());
        searchPb.setVisibility(View.VISIBLE);
        searchPb.setProgress(1);
        UrlCollectPresenter.getInstance().addHistory(mUrlBean);
        webView.loadUrl(mUrlBean.getUrl());
        webView.setOnLongClickListener(this);
        ((MainActivity) getActivity()).getHome().onBrowserCreate(mWindowFragment);
        webView.setDownloadListener(this);
        return view;
    }

    @Override
    protected boolean handleBackEvent() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return false;
    }

    @Override
    protected Bundle onExit() {
        return super.onExit();
    }

    @OnClick({R.id.refresh_tv, R.id.error_ll, R.id.search_srl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.refresh_tv:
                webView.reload();
                break;
            case R.id.error_ll:
                webView.goBack();
                break;
            case R.id.search_srl:
                SearchFragment.show(mBaseActivity,getKeyWord(mUrlBean.getUrl()));
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

    public String getTitle() {
        if (errorLl.isShown()) {
            return "出错了";
        } else if (webView == null || StringUtil.isBlank(webView.getTitle())) {
            return "加载中...";
        }
        return webView.getTitle();
    }

    private NoticeDialog mDialog;

    @Override
    public void onDownloadStart(final String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
        String name;
        if (!StringUtil.isBlank(contentDisposition) && contentDisposition.contains("filename=")) {
            name = contentDisposition.split("filename=")[1];
            if (name.contains(";")) {
                name = name.substring(0, name.indexOf(";"));
            }
            name = name.replace("\"", "");
        } else {
            name = url.substring(url.lastIndexOf('/') + 1);
            if (name.contains("?")) {
                name = name.substring(0, name.indexOf('?'));
            }
        }
        if (mDialog == null) {
            mDialog = new NoticeDialog(getActivity());
        }
        if (!mDialog.isShowing()) {
            final String finalName = name;
            mDialog.show(String.format(ResUtil.getString(R.string.q_download), name), new NoticeDialog.OnDialogClickListener() {
                @Override
                public void onOkClick() {
                    FileBean bean = new FileBean();
                    bean.setUrl(url);
                    bean.setName(finalName);
                    DownPresenter.getInstance(getActivity()).startNew(bean);
                }
            });
        }
    }

}
