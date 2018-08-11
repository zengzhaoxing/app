package com.example.admin.fastpay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.fastpay.R;
import com.example.admin.fastpay.model.MenuModel;

import java.util.List;

/**
 * Created by admin on 2017/7/17.
 */

public class MenuAdapter extends BaseAdapter {
    private Context context;
    private List<MenuModel> list;

    public MenuAdapter(Context context, List<MenuModel> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_homefragment_gridview,parent,false);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.grid_itemname);
            holder.icon = (ImageView) convertView.findViewById(R.id.grid_itempic);
            convertView.setTag(holder);


        }else {
            holder = (ViewHolder) convertView.getTag();


        }
        holder.name.setText(list.get(position).getName());
        Glide.with(context)
                .load(list.get(position).getIcon_url())
                .crossFade()
                .into(holder.icon);

        return convertView;
    }

    class ViewHolder{


        TextView name;
        ImageView icon;



    }
}
