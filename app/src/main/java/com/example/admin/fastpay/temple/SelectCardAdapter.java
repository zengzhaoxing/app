//package com.example.admin.fastpay.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//import com.example.admin.fastpay.R;
//import com.example.admin.fastpay.temple.BankCradBean;
//
//import java.util.List;
//
///**
// * Created by admin on 2017/8/3.
// */
//
//public class SelectCardAdapter extends BaseAdapter{
//    private Context context;
//    private List<BankCradBean.Datum> list;
//
//    public SelectCardAdapter(Context context, List<BankCradBean.Datum> list) {
//        this.context = context;
//        this.list = list;
//    }
//
//    @Override
//    public int getCount() {
//        return list.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return list.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        final ViewHolder holder;
//        if (convertView==null){
//            convertView = LayoutInflater.from(context).inflate(R.layout.layout_selectcard_detail,parent,false);
//            holder = new ViewHolder();
//            holder.name = (TextView) convertView.findViewById(R.id.selectcard_card_name);
//            holder.number = (TextView) convertView.findViewById(R.id.selectcard_card_number);
//            convertView.setTag(holder);
//        }else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//        holder.name.setText(list.get(position).getBankName());
//        StringBuilder builder = new StringBuilder(list.get(position).getCardNo());
//        builder.delete(0,15);
//        holder.number.setText("尾号"+builder.toString()+"银行卡");
//        return convertView;
//    }
//
//    class ViewHolder{
//        TextView name;
//        TextView number;
//
//    }
//}
