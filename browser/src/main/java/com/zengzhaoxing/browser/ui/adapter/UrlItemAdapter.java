package com.zengzhaoxing.browser.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zengzhaoxing.browser.R;
import com.zengzhaoxing.browser.bean.UrlBean;
import com.zxz.www.base.utils.ViewUtil;

import java.util.List;

public class UrlItemAdapter extends BaseAdapter {

    public UrlItemAdapter(List<UrlBean> urlBeans) {
        mUrlBeans = urlBeans;
    }

    private List<UrlBean> mUrlBeans;

    @Override
    public int getCount() {
        return mUrlBeans != null ? mUrlBeans.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_url, parent,false);
        }

        TextView urlTv = convertView.findViewById(R.id.url_tv);
        TextView urlTitleTv = convertView.findViewById(R.id.url_title_tv);
        ImageView imageView = convertView.findViewById(R.id.url_iv);
        UrlBean bean = mUrlBeans.get(position);
        urlTv.setText(bean.getUrl());
        urlTitleTv.setText(bean.getTitle());
        ViewUtil.LoadImg(imageView,bean.getIconUrl(),R.mipmap.icon);
        Log.i("zxz", bean.toJson());
        return convertView;
    }

}
