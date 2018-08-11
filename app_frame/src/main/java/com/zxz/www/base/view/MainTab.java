package com.zxz.www.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zxz.www.base.R;
import com.zxz.www.base.utils.ResUtil;
import com.zxz.www.base.utils.ViewUtil;

import java.util.ArrayList;

/**
 * Created by 曾宪梓 on 2018/3/20.
 */

public class MainTab extends LinearLayout {

    private ArrayList<ImageTextView> mViews = new ArrayList<>();

    private ArrayList<Integer> textIDs = new ArrayList<>();

    public MainTab(Context context) {
        this(context, null, 0);
    }

    public MainTab(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        setGravity(Gravity.CENTER_VERTICAL);
    }

    public void refresh() {
        if (mViews != null) {
            for (ImageTextView view : mViews) {
                view.setText(ResUtil.getString(textIDs.get(mViews.indexOf(view))));
            }
        }
    }

    public void addTab(int textId,int imgId,int disableImgId,int textColor,int disableColor) {
        final ImageTextView imageTextView = new ImageTextView(getContext());
        imageTextView.setText(textId);
        imageTextView.setTextSize(10);
        imageTextView.setTextColor(textColor);
        imageTextView.setDisableTextColor(disableColor);
        imageTextView.setImageRes(imgId);
        imageTextView.setDisableImageRes(disableImgId);
        LayoutParams lp = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.weight = 1;
        imageTextView.setLayoutParams(lp);
        mViews.add(imageTextView);
        textIDs.add(textId);
        final int position = mViews.size() - 1;
        imageTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentPosition(position);
                if (mOnItemSelectListener != null) {
                    mOnItemSelectListener.onItemSelect(position, ViewUtil.isDoubleRequest(v));
                }
            }
        });
        addView(imageTextView);
    }

    public void setCurrentPosition(int position) {
        if (mViews.size() > position) {
            for (ImageTextView view : mViews) {
                view.setmIsEnable(false);
            }
            mViews.get(position).setmIsEnable(true);
        }
    }

    public interface OnItemSelectListener {
        void onItemSelect(int position, boolean isDoubleClick);
    }

    public void setOnItemSelectListener(OnItemSelectListener onItemSelectListener) {
        mOnItemSelectListener = onItemSelectListener;
    }

    private OnItemSelectListener mOnItemSelectListener;

    public void showDot(int dot,int position) {
        if (mViews.size() > position) {
            ImageTextView view = mViews.get(position);
            view.showDot(dot);
        }
    }

    public void hideDot(int position) {
        if (mViews.size() > position) {
            ImageTextView view = mViews.get(position);
            view.hideDot();
        }
    }

}
