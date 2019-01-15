package com.zengzhaoxing.browser.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;

import com.zengzhaoxing.browser.R;
import com.zengzhaoxing.browser.bean.UrlBean;
import com.zengzhaoxing.browser.ui.fragment.WindowFragment;

import java.util.List;

public class SwitchWindowAdapter extends PagerAdapter implements View.OnClickListener {

    public SwitchWindowAdapter(List<WindowFragment> windowFragments, Context context) {
        mWindowFragments = windowFragments;
        mViews = new View[5];
        for (int i = 0; i < mViews.length; i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_window,null);
            view.setTag(i);
            view.findViewById(R.id.close_iv).setOnClickListener(this);
            view.setOnClickListener(this);
            mViews[i] = view;
        }
    }

    private List<WindowFragment> mWindowFragments;


    private View[] mViews;

    @Override
    public int getCount() {
        return mWindowFragments.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int index = position % mViews.length;
        View view = mViews[index];
        ViewParent vp = view.getParent();
        if (vp != null) {
            ViewGroup parent = (ViewGroup) vp;
            parent.removeView(view);
            view.setTag(position);
        }
        refresh(view,mWindowFragments.get(position));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        int index = position % mViews.length;
        View view = mViews[index];
        container.removeView(view);
    }

    public void refresh(int p) {
        int index = p % mViews.length;
        for (int i = 0; mViews != null && i < mViews.length; i++) {
            int pi = p + i - index;
            if (pi < getCount()) {
                refresh(mViews[i],mWindowFragments.get(pi));
            }
        }
    }

    private void refresh(View view,WindowFragment fragment) {
        Bitmap bitmap = fragment.getBitmap();
        ImageView imageView = view.findViewById(R.id.window_iv);
        TextView textView = view.findViewById(R.id.window_title_tv);
        UrlBean urlBean = fragment.getUrlBean();
        if (urlBean != null) {
            textView.setText(urlBean.getTitle());
        }else{
            textView.setText("首页");
        }
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setImageResource(R.color.white);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.window_item_view:
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick((Integer) v.getTag());
                }
                break;
            case R.id.close_iv:
                ViewGroup vp = (ViewGroup) v.getParent();
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onCloseClick((Integer) vp.getTag());
                }
                break;
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);

        void onCloseClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    private OnItemClickListener mOnItemClickListener;



}
