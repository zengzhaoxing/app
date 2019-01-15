package com.zengzhaoxing.browser.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DebugUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zengzhaoxing.browser.MainActivity;
import com.zengzhaoxing.browser.bean.UrlBean;
import com.zengzhaoxing.browser.presenter.UrlCollectPresenter;
import com.zhaoxing.view.sharpview.SharpLinearLayout;
import com.zxz.www.base.app.BaseActivity;
import com.zxz.www.base.app.SlideFragment;
import com.zxz.www.base.utils.DensityUtil;
import com.zxz.www.base.utils.EditorUtil;
import com.zxz.www.base.utils.FileUtil;
import com.zxz.www.base.utils.IntentUtil;
import com.zxz.www.base.utils.ResUtil;
import com.zxz.www.base.utils.ToastUtil;

import java.util.ArrayList;

public class FunListFragment extends SlideFragment implements View.OnClickListener {

    public static final int FUN_OPEN_IN_BACKGROUND = 1;

    public static final int FUN_OPEN_IN_NEW_WINDOW = 2;

    public static final int FUN_COPY_URL = 3;

    public static final int FUN_SELECT_TEXT = 4;

    public static final int FUN_SAVE_IMG = 5;

    public static final int FUN_LOOK_IMG = 6;

    public static final int FUN_SHARE_IMG = 7;

    public static final int FUN_SHARE_URL = 8;

    public static final int FUN_COPY_TEXT = 9;

    public static final int FUN_DELETE_HISTORY = 10;

    public static final int FUN_DELETE_COLLECT = 11;

    public static final int FUN_ADD_COLLECT = 12;

    private String getFunName(int fun) {
        switch (fun) {
            case FUN_OPEN_IN_BACKGROUND:
                return "后台窗口打开";
            case FUN_OPEN_IN_NEW_WINDOW:
                return "新窗口打开";
            case FUN_COPY_URL:
                return "复制链接地址";
            case FUN_SELECT_TEXT:
                return "选择文本";
            case FUN_SAVE_IMG:
                return "保存图片";
            case FUN_LOOK_IMG:
                return "查看图片";
            case FUN_SHARE_IMG:
                return "分享图片";
            case FUN_SHARE_URL:
                return "分享连接";
            case FUN_COPY_TEXT:
                return "复制链接文字";
            case FUN_DELETE_HISTORY:
                return "删除该历史记录";
            case FUN_DELETE_COLLECT:
                return "删除该收藏";
            case FUN_ADD_COLLECT:
                return "添至收藏";
            default:
                return null;
        }
    }

    public static void open(BaseActivity activity,Bundle bundle) {
        FunListFragment fragment = new FunListFragment();
        fragment.setArguments(bundle);
        activity.openNewFragment(fragment);
    }

    private String mUrl;

    private String mTitle;

    private String mSrc;

    private UrlBean mUrlBean;

    @Override
    public View getSlideView(ViewGroup parent) {
        RelativeLayout relativeLayout = new RelativeLayout(getActivity());
        int padding = DensityUtil.dip2px(10);
        relativeLayout.setPadding(padding,padding,padding,padding);
        SharpLinearLayout linearLayout = new SharpLinearLayout(getActivity());
        linearLayout.getRenderProxy().setBackgroundColor(ResUtil.getColor(com.zxz.www.base.R.color.white));
        linearLayout.getRenderProxy().setRadius(DensityUtil.dip2px(4));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        relativeLayout.addView(linearLayout);
        Bundle bundle = getArguments();
        mUrl = bundle.getString("url");
        mTitle = bundle.getString("title");
        mSrc = bundle.getString("src");
        mUrlBean = (UrlBean) bundle.getSerializable(UrlBean.class.getName());
        ArrayList<Integer> fun = new ArrayList<>();
        if (mUrl != null) {
            fun.add(FUN_OPEN_IN_NEW_WINDOW);
            fun.add(FUN_OPEN_IN_BACKGROUND);
            fun.add(FUN_SHARE_URL);
            fun.add(FUN_COPY_URL);
        }
        if (mTitle != null) {
            fun.add(FUN_COPY_TEXT);
        }
        if (mSrc != null) {
            fun.add(FUN_SAVE_IMG);
            fun.add(FUN_LOOK_IMG);
            fun.add(FUN_SHARE_IMG);
        }
        if (mUrlBean != null) {
            fun.add(FUN_OPEN_IN_BACKGROUND);
            fun.add(FUN_SHARE_URL);
            fun.add(FUN_COPY_URL);
            if (mUrlBean.isCollect()) {
                fun.add(FUN_DELETE_COLLECT);
            } else {
                fun.add(FUN_ADD_COLLECT);
                fun.add(FUN_DELETE_HISTORY);
            }
        }
        if (fun.size() == 0) {
            mBaseActivity.closeCurrentFragment();
        }
        for (int i = 0; i < fun.size(); i++) {
            if (i != 0) {
                View view = new View(getActivity());
                view.setBackgroundResource(com.zxz.www.base.R.color.text_mid_black);
                linearLayout.addView(view,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,DensityUtil.dip2px(0.5F)));
            }
            TextView textView = new TextView(getActivity());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(55));
            lp.leftMargin = DensityUtil.dip2px(20);
            textView.setLayoutParams(lp);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setTextSize(14);
            textView.setTextColor(ResUtil.getColor(com.zxz.www.base.R.color.text_black));
            textView.setText(getFunName(fun.get(i)));
            textView.setTag(fun.get(i));
            textView.setOnClickListener(this);
            linearLayout.addView(textView);
        }
        return relativeLayout;
    }


    @Override
    public void onClick(View v) {
        int fun = (int) v.getTag();
        switch (fun) {
            case FUN_OPEN_IN_BACKGROUND:
                ((MainActivity)mBaseActivity).getHome().openBackWindow(mUrlBean);
                if (mUrlBean != null) {
                    ToastUtil.toast("已在后台中打开");
                }
                break;
            case FUN_OPEN_IN_NEW_WINDOW:
                UrlBean bean = new UrlBean();
                bean.setUrl(mUrl);
                ((MainActivity)mBaseActivity).getHome().openNewWindow(bean);
                break;
            case FUN_COPY_URL:
                EditorUtil.copy(mUrl);
                break;
            case FUN_COPY_TEXT:
                EditorUtil.copy(mTitle);
                break;
            case FUN_SELECT_TEXT:
                break;
            case FUN_SAVE_IMG:
                FileUtil.getInstance().saveImg(mSrc);
                break;
            case FUN_LOOK_IMG:
                break;
            case FUN_SHARE_IMG:
                mBaseActivity.showLoadingView(com.zxz.www.base.R.color.black_30);
                IntentUtil.sendImg(mSrc,mBaseActivity);
                break;
            case FUN_SHARE_URL:
                IntentUtil.sendText(mUrl,mBaseActivity);
                break;
            case FUN_DELETE_HISTORY:
            case FUN_DELETE_COLLECT:
                UrlCollectPresenter.getInstance().delete(mUrlBean);
                break;
            case FUN_ADD_COLLECT:
                UrlCollectPresenter.getInstance().addCollect(mUrlBean);
                break;
            default:
        }
        mBaseActivity.closeCurrentFragment();
    }
}
