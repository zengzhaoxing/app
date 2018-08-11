package com.example.admin.fastpay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.admin.fastpay.R;
import com.zxz.www.base.app.BaseActivity;
import com.zxz.www.base.app.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 曾宪梓 on 2018/1/7.
 */

public class H5Fragment extends BaseFragment {

    private static final String WEB_TITLE = "web_title";

    private static final String WEB_URL = "web_url";

    @BindView(R.id.webview_title)
    TextView mWebviewTitle;
    @BindView(R.id.progressBar1)
    ProgressBar mProgressBar1;
    @BindView(R.id.webview)
    WebView mWebview;

    Unbinder unbinder;

    public static void showH5Fragment(BaseActivity activity, String title, String url) {
        H5Fragment h5Fragment = new H5Fragment();
        Bundle bundle = new Bundle();
        bundle.putString(WEB_TITLE, title);
        bundle.putString(WEB_URL, url);
        h5Fragment.setArguments(bundle);
        activity.openNewFragmentWithDefaultAnim(h5Fragment);
    }

    public static void showH5Fragment(BaseActivity activity,String url) {
        showH5Fragment(activity, "秒到收款", url);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web_view, container, false);
        unbinder = ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        String title = bundle.getString(WEB_TITLE);
        String url = bundle.getString(WEB_URL);
        mWebview.loadUrl(url);
        //支持缩放
        mWebview.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        mWebview.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        mWebview.getSettings().setUseWideViewPort(true);
        //
        //自适应屏幕
        mWebview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebview.getSettings().setLoadWithOverviewMode(true);

        mWebview.getSettings().setJavaScriptEnabled(true);//启用js
        mWebview.getSettings().setBlockNetworkImage(false);//解决图片不显示

        //设置标题
        mWebviewTitle.setText(title);


        mWebview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        mWebview.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (mProgressBar1 == null) {
                    return;
                }
                if (newProgress==100) {
                    Log.i("tag", "网页加载完成了");
                    mProgressBar1.setVisibility(View.GONE);//加载完网页进度条消失
                }else {
                    Log.i("tag", "网页加载中......");
                    mProgressBar1.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    mProgressBar1.setProgress(newProgress);//设置进度值
                }
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.back35)
    public void onViewClicked() {
        goBack();
    }

    @Override
    protected boolean handleBackEvent() {
        if (mWebview != null && mWebview.canGoBack()) {
            mWebview.goBack();
            return true;
        } else {
            return super.handleBackEvent();
        }
    }


}
