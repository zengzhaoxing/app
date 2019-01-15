package com.zengzhaoxing.browser.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import com.zengzhaoxing.browser.R;
import com.zengzhaoxing.browser.ui.fragment.WindowFragment;
import com.zxz.www.base.adapter.ViewPagerAdapter;
import com.zxz.www.base.view.gallery.GalleryView;

import java.util.ArrayList;
import java.util.List;

public class SwitchWindowAdapter extends PagerAdapter implements View.OnClickListener {

    public SwitchWindowAdapter(List<WindowFragment> windowFragments, Context context) {
        mWindowFragments = windowFragments;
        mImageViews = new ImageView[5];
        for (int i = 0; i < mImageViews.length; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setTag(i);
            imageView.setOnClickListener(this);
            mImageViews[i] = imageView;
        }
    }

    private List<WindowFragment> mWindowFragments;


    private ImageView[] mImageViews;

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
        int index = position % mImageViews.length;
        ImageView view = mImageViews[index];
        ViewParent vp = view.getParent();
        if (vp != null) {
            ViewGroup parent = (ViewGroup) vp;
            parent.removeView(view);
            view.setTag(position);
        }
        Bitmap bitmap = mWindowFragments.get(position).getBitmap();
        if (bitmap != null) {
            view.setImageBitmap(bitmap);
        } else {
            view.setImageResource(R.color.white);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        int index = position % mImageViews.length;
        ImageView view = mImageViews[index];
        container.removeView(view);
    }

    public void refresh(int p) {
        int index = p % mImageViews.length;
        for (int i = 0; mImageViews != null && i < mImageViews.length; i++) {
            int pi = p + i - index;
            if (pi < getCount()) {
                Bitmap bitmap = mWindowFragments.get(pi).getBitmap();
                if (bitmap != null) {
                    mImageViews[i].setImageBitmap(bitmap);
                } else {
                    mImageViews[i].setImageResource(R.color.white);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick((Integer) v.getTag());
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    private OnItemClickListener mOnItemClickListener;



}
