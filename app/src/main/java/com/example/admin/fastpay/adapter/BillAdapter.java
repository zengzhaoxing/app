package com.example.admin.fastpay.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.admin.fastpay.R;
import com.example.admin.fastpay.model.response.BillResp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sun on 2017/5/14.
 */

public class BillAdapter extends BaseAdapter {

    private Context context;
    private List<BillResp.DataBean> mDataList;
    private List<BillResp.DataBean> mCurrentList;

    public BillAdapter(Context context) {
        this.context = context;
        mDataList = new ArrayList<>();
        mCurrentList = new ArrayList<>();
    }

    public void setAllData(List<BillResp.DataBean> dataList) {
        mDataList.clear();
        mDataList.addAll(dataList);
    }

    public List<BillResp.DataBean> getCurrentData() {
        return mCurrentList;
    }

    public void selectData(String time) {
        if(mDataList==null) {
            return;
        }
        mCurrentList.clear();
        for (int i = 0; i < mDataList.size(); i++) {
            BillResp.DataBean dataBean = mDataList.get(i);
            if (dataBean != null) {
                if (dataBean.getCreated_at() != null) {
                    if (dataBean.getCreated_at().getDate() != null) {
                        if (dataBean.getCreated_at().getDate().contains(time)) {
                            mCurrentList.add(dataBean);
                        }
                    } else {
                    }
                } else {
                }
            } else {
            }

        }
        notifyDataSetChanged();
    }

    public void selectData(List<String> selection, List<String> state) {
        if(mDataList==null) {
            return;
        }
        mCurrentList.clear();
        boolean isSelected;
        BillResp.DataBean dataBean;
        for (int i = 0; i < mDataList.size(); i++) {
            isSelected = false;
            dataBean = mDataList.get(i);

            if (selection != null)
                for (int a = 0; a < selection.size(); a++) {
                    if (processBean(dataBean).contains(selection.get(a))) {
                        if (state == null || state.size() == 0) {
                            mCurrentList.add(dataBean);
                        } else {
                            isSelected = true;
                        }
                        break;
                    }
                }

            if (state != null)
                for (int b = 0; b < state.size(); b++) {
                    if (processBean(dataBean).contains(state.get(b))) {
                        if (selection == null || selection.size() == 0) {
                            mCurrentList.add(dataBean);
                            break;
                        } else {
                            if (isSelected) {
                                mCurrentList.add(dataBean);
                                break;
                            }
                        }
                    }
                }
        }
        notifyDataSetChanged();
    }

    private String processBean(BillResp.DataBean dataBean) {
        String s = "";

        int type = dataBean.getType();
        if (type == 101 || type == 102 || type == 103 || type == 104 || type == 105
                || type == 305 || type == 301 || type == 501 || type == 503 || type == 601 || type == 603 || type == 801 || type == 803
                || type == 901 || type == 903|| type == 504) {
            s = s + "支付宝支付收款";
        } else if (type == 201 || type == 202 || type == 203 || type == 306 || type == 302 || type == 502 || type == 602 || type == 604
                || type == 802 || type == 804 || type == 904|| type == 505) {
            s = s + "微信支付收款";
        } else if (type == 303 || type == 307) {
            s = s + "京东支付收款";
        } else if (type == 401 || type == 402) {
            s = s + "银联支付收款";
        } else if (type == 304) {
            s = s + "翼支付支付收款";
        } else if ( type == 902) {
            s = s + "快捷大额支付收款";
        }

        if (dataBean.getPay_status() == 1) {
            s = s + "收款成功";
        } else {
            s = s + "未支付";
        }

        return s;
    }

    @Override
    public int getCount() {
        return mCurrentList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCurrentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_bill_listview, null);
            viewHolder.ll_bill_item = (LinearLayout) convertView.findViewById(R.id.ll_bill_item);
            viewHolder.iv_bill = (ImageView) convertView.findViewById(R.id.iv_bill);
            viewHolder.tv_zhifu_leixing = (TextView) convertView.findViewById(R.id.tv_zhifu_leixing);
            viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.tv_jine = (TextView) convertView.findViewById(R.id.tv_jine);
            viewHolder.tv_zhuangtai = (TextView) convertView.findViewById(R.id.tv_zhuangtai);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        BillResp.DataBean dataBean = mCurrentList.get(position);

        init(dataBean, viewHolder, position);

        return convertView;
    }

    private void init(BillResp.DataBean dataBean, ViewHolder viewHolder, int position) {

        int type = dataBean.getType();

        if (type == 101 || type == 102 || type == 103 || type == 104 || type == 105 || type == 305 || type == 301 || type == 501
                || type == 503 || type == 601 || type == 603 || type == 801 || type == 803
                || type == 901 || type == 903|| type == 504) {
            viewHolder.tv_zhifu_leixing.setText("支付宝支付收款");
            viewHolder.iv_bill.setImageResource(R.drawable.zhifubao);
        } else if (type == 201 || type == 202 || type == 203 || type == 306 || type == 302 || type == 502 || type == 602 || type == 604
                || type == 802 || type == 804 || type == 904|| type == 505) {
            viewHolder.tv_zhifu_leixing.setText("微信支付收款");
            viewHolder.iv_bill.setImageResource(R.drawable.weixin);
        } else if (type == 303 || type == 307) {
            viewHolder.tv_zhifu_leixing.setText("京东支付收款");
            viewHolder.iv_bill.setImageResource(R.drawable.jindong);
        } else if (type == 401 || type == 402) {
            viewHolder.tv_zhifu_leixing.setText("银联支付收款");
            viewHolder.iv_bill.setImageResource(R.drawable.yinlian);
        } else if (type == 304) {
            viewHolder.tv_zhifu_leixing.setText("翼支付支付收款");
            viewHolder.iv_bill.setImageResource(R.drawable.yizhifu);
        } else if ( type == 902 ){
            viewHolder.tv_zhifu_leixing.setText("快捷大额支付收款");
            viewHolder.iv_bill.setImageResource(R.drawable.yinlian);
        }

        if (dataBean.getPay_status() == 1) {
            viewHolder.tv_zhuangtai.setText("收款成功");
            viewHolder.tv_zhuangtai.setTextColor(context.getResources().getColor(R.color.text_chenggong));

            viewHolder.tv_jine.setText("￥ " + dataBean.getTotal_amount());
            viewHolder.tv_jine.setTextColor(context.getResources().getColor(R.color.bill_text_hei));

        } else {
            viewHolder.tv_zhuangtai.setText("未支付");
            viewHolder.tv_zhuangtai.setTextColor(context.getResources().getColor(R.color.bill_text));

            viewHolder.tv_jine.setText("￥ " + dataBean.getTotal_amount());
            viewHolder.tv_jine.setTextColor(context.getResources().getColor(R.color.bill_text));
        }

//        if (mCurrentList.size() == position + 1) {
//            viewHolder.tv_time.setText("00:00:00");
//        } else {
//            String substring = dataBean.getCreated_at().getDate().substring(11, 19);
//            viewHolder.tv_time.setText(substring);
//        }
        String substring = dataBean.getCreated_at().getDate().substring(11, 19);
        viewHolder.tv_time.setText(substring);

    }

    public void setData(List<BillResp.DataBean> data) {
        mCurrentList.clear();
        mCurrentList.addAll(data);
        notifyDataSetChanged();
    }

    public void setLoadMoreData(List<BillResp.DataBean> loadMoreData) {
        mCurrentList.addAll(loadMoreData);
        notifyDataSetChanged();
    }

    class ViewHolder {
        LinearLayout ll_bill_item;
        ImageView iv_bill;
        TextView tv_zhifu_leixing;
        TextView tv_time;
        TextView tv_jine;
        TextView tv_zhuangtai;
    }
}
