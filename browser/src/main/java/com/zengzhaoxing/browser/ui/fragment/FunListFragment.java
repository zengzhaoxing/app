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

import com.zhaoxing.view.sharpview.SharpLinearLayout;
import com.zxz.www.base.app.BaseActivity;
import com.zxz.www.base.app.SlideFragment;
import com.zxz.www.base.utils.DensityUtil;
import com.zxz.www.base.utils.EditorUtil;
import com.zxz.www.base.utils.FileUtil;
import com.zxz.www.base.utils.IntentUtil;
import com.zxz.www.base.utils.ResUtil;

public class FunListFragment extends SlideFragment implements View.OnClickListener {

    public static final int FUN_OPEN_IN_BACKGROUND = 1;

    public static final int FUN_OPEN_IN_NEW_WINDOW = 2;

    public static final int FUN_COPY_URL = 3;

    public static final int FUN_SELECT_TEXT = 4;

    public static final int FUN_SAVE_IMG = 5;

    public static final int FUN_LOOK_IMG = 6;

    public static final int FUN_SHARE_IMG = 7;

    public static final int FUN_SHARE_URL = 8;

    private String getFunName(int fun) {
        switch (fun) {
            case FUN_OPEN_IN_BACKGROUND:
                return "后台窗口打开";
            case FUN_OPEN_IN_NEW_WINDOW:
                return "新窗口打开";
            case FUN_COPY_URL:
                return "复制网页链接";
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
            default:
                return null;
        }
    }

    public static void open(BaseActivity activity,String url,int... fun) {
        FunListFragment fragment = new FunListFragment();
        Bundle bundle = new Bundle();
        bundle.putIntArray(int[].class.getName(), fun);
        bundle.putString(String.class.getName(), url);
        fragment.setArguments(bundle);
        activity.openNewFragment(fragment);
    }

    private String mUrl;

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
        int[] funs = getArguments().getIntArray(int[].class.getName());
        mUrl = getArguments().getString(String.class.getName());
        for (int i = 0; funs != null && i < funs.length; i++) {

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
            textView.setText(getFunName(funs[i]));
            textView.setTag(funs[i]);
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
                break;
            case FUN_OPEN_IN_NEW_WINDOW:
                break;
            case FUN_COPY_URL:
                EditorUtil.copy(mUrl);
                break;
            case FUN_SELECT_TEXT:
                break;
            case FUN_SAVE_IMG:
                FileUtil.getInstance().saveImg(mUrl);
                break;
            case FUN_LOOK_IMG:
                break;
            case FUN_SHARE_IMG:
                IntentUtil.sendImg(mUrl,mBaseActivity);
                break;
            case FUN_SHARE_URL:
                IntentUtil.sendText(mUrl,mBaseActivity);
                break;
            default:
                break;
        }
        mBaseActivity.closeCurrentFragment();
    }
}
