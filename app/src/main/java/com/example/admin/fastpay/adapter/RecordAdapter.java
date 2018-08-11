package com.example.admin.fastpay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.admin.fastpay.R;
import com.example.admin.fastpay.model.response.RecordResp;

import java.util.List;

/**
 * Created by admin on 2017/8/7.
 */

public class RecordAdapter extends BaseAdapter {

    private Context context;
    private List<RecordResp.Record> list;

    public RecordAdapter(Context context, List<RecordResp.Record> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_record_lv,parent,false);
            viewHolder.time = (TextView) convertView.findViewById(R.id.record_lv_time);
            viewHolder.total = (TextView) convertView.findViewById(R.id.record_lv_total);
            viewHolder.amount = (TextView) convertView.findViewById(R.id.record_lv_amount);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String substring = list.get(position).getCreatedAt().substring(0, 10);

        viewHolder.time.setText(substring);
        viewHolder.total.setText(list.get(position).getTotalAmount()+"元");
        viewHolder.amount.setText(list.get(position).getAmount()+"元");
        return convertView;
    }

    class ViewHolder{
        TextView time;
        TextView total;
        TextView amount;
    }
}
