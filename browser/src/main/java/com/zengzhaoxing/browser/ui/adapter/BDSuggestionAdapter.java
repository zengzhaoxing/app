package com.zengzhaoxing.browser.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zengzhaoxing.browser.R;

import java.util.ArrayList;
import java.util.List;

public class BDSuggestionAdapter extends BaseAdapter {


    public void setStrings(List<String> strings) {
        mStrings = strings;
        notifyDataSetChanged();
    }

    private List<String> mStrings;

    @Override
    public int getCount() {
        return mStrings == null ? 0 : mStrings.size();
    }

    @Override
    public Object getItem(int position) {
        return mStrings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_suggestion, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.item_tv);
        textView.setText(mStrings.get(position));
        return convertView;
    }

}
