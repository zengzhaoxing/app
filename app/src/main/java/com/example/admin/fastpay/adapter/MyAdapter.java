package com.example.admin.fastpay.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.List;

/**
 * Created by yjy on 2016/11/4.
 */
public class MyAdapter extends BaseAdapter implements Filterable {

    private List<Object> mData;

    public MyAdapter(List<Object> Data) {
        this.mData = Data;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return convertView;
    }

    public void setData(List<Object> Data) {
        this.mData = Data;
        this.notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}
