package com.example.admin.fastpay.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.admin.fastpay.R;
import com.example.admin.fastpay.model.StoreInfo;

import java.util.List;

/**
 * Created by sun on 2017/5/24.
 */

public class MenDianAdapter extends BaseAdapter {

    private Context context;
    private List<StoreInfo.DataBean> dataBeen;

    public MenDianAdapter(Context context, List<StoreInfo.DataBean> dataBeen) {
        this.context = context;
        this.dataBeen = dataBeen;
    }

    @Override
    public int getCount() {
        return dataBeen == null ? 0 :dataBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return dataBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.listview_mendian,null);
            viewHolder.tv_store_name = (TextView) convertView.findViewById(R.id.tv_store_name);
            viewHolder.tv_store_id = (TextView) convertView.findViewById(R.id.tv_store_id);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        StoreInfo.DataBean bean = dataBeen.get(position);

        viewHolder.tv_store_name.setText(bean.getStore_name());
        viewHolder.tv_store_id.setText(bean.getStore_id());

        return convertView;
    }

    class ViewHolder{
        TextView tv_store_name;
        TextView tv_store_id;
    }
}
