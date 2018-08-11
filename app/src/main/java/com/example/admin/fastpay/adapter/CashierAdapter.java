package com.example.admin.fastpay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.admin.fastpay.R;
import com.example.admin.fastpay.model.response.CashierResp;

import java.util.List;

/**
 * Created by admin on 2017/7/15.
 */

public class CashierAdapter extends BaseAdapter {
    private Context context;
    private List<CashierResp.Cashier> list;

    public CashierAdapter(Context context, List<CashierResp.Cashier> list) {
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
        final  ViewHolder holder;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_cashier_lv,parent,false);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.cashier_lv_name);
            holder.money = (TextView) convertView.findViewById(R.id.cashier_lv_daymoney);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.name.setText(list.get(position).getName());
        holder.money.setText("今日收入："+list.get(position).getAmount()+"元");

        return convertView;
    }


    class ViewHolder{


        TextView name,money;



    }
}
