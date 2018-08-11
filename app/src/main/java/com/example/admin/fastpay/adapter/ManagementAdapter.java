package com.example.admin.fastpay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.admin.fastpay.R;
import com.example.admin.fastpay.model.Device;

import java.util.List;

/**
 * Created by admin on 2017/7/22.
 */

public class ManagementAdapter extends BaseAdapter {

    private List<Device> list;
    private Context context;

    public ManagementAdapter(List<Device> list, Context context) {
        this.list = list;
        this.context = context;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_annex_lv,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.annex_lv_name);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText("设备号："+list.get(position).getMachineCode());

        return convertView;
    }


    class ViewHolder{


        TextView name;



    }
}
