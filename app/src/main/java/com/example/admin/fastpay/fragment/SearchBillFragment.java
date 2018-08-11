package com.example.admin.fastpay.fragment;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.fastpay.R;
import com.example.admin.fastpay.adapter.MyAdapter;
import com.example.admin.fastpay.model.response.BillResp;
import com.example.admin.fastpay.model.TokenModel;
import com.example.admin.fastpay.net.RequestCaller;
import com.example.admin.fastpay.utils.UrlBase;
import com.example.admin.fastpay.view.EditSearchView;
import com.zxz.www.base.app.BaseFragment;
import com.zxz.www.base.net.request.JsonRequester;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.text.Html.fromHtml;

public class SearchBillFragment extends BaseFragment {

    private EditSearchView searchView;
    private MyAdapter adapter;
    private String string = "";
    private List<BillResp.DataBean> currentData;
    private List<BillResp.DataBean> mCurrentData = new ArrayList<>();

    private class ViewHolder {
        private TextView text;
        ImageView iv_bill_search;
        TextView tv_zhifu_leixing_search;
        TextView tv_jine_search;
        TextView tv_zhuangtai_search;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_bill,container,false);
        ImageView iv_search = (ImageView) view.findViewById(R.id.iv_search);
        EditText searchTextView = (EditText) view.findViewById(R.id.searchTextView);
        this.searchView = (EditSearchView) view.findViewById(R.id.search_view);
        searchTextView.setInputType(InputType.TYPE_CLASS_NUMBER);
        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
        networkingRequest();
        this.initSearchEvent();
        return view;
    }

    private void networkingRequest() {

        RequestCaller.post(UrlBase.ZHANGDAN, new TokenModel(), BillResp.class, new JsonRequester.OnResponseListener<BillResp>() {
            @Override
            public void onResponse(BillResp response, int resCode) {
                if (response != null && response.getData() != null) {
                    currentData = response.getData();
                }
            }
        });
    }

    private void initSearchEvent() {
        final List<Object> items = new ArrayList<>();
        adapter = new MyAdapter(items) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder;
                if (convertView == null) {
                    convertView = View.inflate(mBaseActivity, R.layout.list_item, null);
                    holder = new ViewHolder();
                    holder.text = (TextView) convertView.findViewById(R.id.name);
                    holder.iv_bill_search = (ImageView) convertView.findViewById(R.id.iv_bill_search);
                    holder.tv_zhifu_leixing_search = (TextView) convertView.findViewById(R.id.tv_zhifu_leixing_search);
                    holder.tv_jine_search = (TextView) convertView.findViewById(R.id.tv_jine_search);
                    holder.tv_zhuangtai_search = (TextView) convertView.findViewById(R.id.tv_zhuangtai_search);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                String info = (String) adapter.getItem(position);
                BillResp.DataBean dataBean = currentData.get(position);
                if (!string.equals("") && string != null) {
                    int index = info.toLowerCase().indexOf(string.toLowerCase());
                    int length = string.length();
                    Spanned temp = fromHtml(info.substring(0, index)
                            + "<u><font color=#0022ff>"
                            + info.substring(index, index + length)
                            + "</font></u>"
                            + info.substring(index + length,
                            info.length()));
                    holder.text.setText(temp);
                } else {
                    holder.text.setText(info);
                }
                infoData(holder,dataBean);
                return convertView;
            }

            @Override
            public Filter getFilter() {
                Filter filter;
                filter = new Filter() {
                    @Override
                    protected FilterResults performFiltering(CharSequence constraint) {
                        FilterResults filterResults = new FilterResults();
                        if (!TextUtils.isEmpty(constraint)) {
                            Pattern pattern = Pattern.compile(constraint.toString().toLowerCase());
                            JSONArray array = new JSONArray();
                            mCurrentData.clear();
                            for (int i = 0; i < currentData.size(); i++) {
                                String data = currentData.get(i).getOut_trade_no();
                                Matcher matcher = pattern.matcher(data.toLowerCase());
                                if (matcher.find()) {
                                    array.put(data);
                                    mCurrentData.add(currentData.get(i));
                                }
                            }
                            JSONObject object = new JSONObject();
                            try {
                                object.put("data", array);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            filterResults.values = object;
                            filterResults.count = array.length();
                        }
                        return filterResults;
                    }

                    @Override
                    protected void publishResults(CharSequence constraint, FilterResults results) {
                        if (results.values != null) {
                            List<Object> list = new ArrayList<>();
                            JSONObject obj = (JSONObject) results.values;
                            if (obj.has("data")) {
                                try {
                                    JSONArray _ja = obj.getJSONArray("data");
                                    for (int i = 0; i < _ja.length(); i++) {
                                        String _info = (String) _ja.get(i);
                                        list.add(_info);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            adapter.setData(list);
                        }
                    }
                };
                return filter;
            }
        };
        searchView.setAdapter(adapter);
        searchView.setOnQueryTextListener(new EditSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String changeTxt) {
                string = changeTxt;
                return false;
            }
        });
        searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BillResp.DataBean dataBean = mCurrentData.get(position);
                BillItemInfoFragment.showFragment(mBaseActivity,dataBean);
            }
        });
    }

    private void infoData(ViewHolder holder, BillResp.DataBean info) {

        int type = info.getType();
        if (type == 101 || type == 102 || type == 103 || type == 104 || type == 105 || type == 305 || type == 301
                || type == 501 || type == 503 || type == 601 || type == 603 || type == 801 || type == 803
                || type == 901 || type == 903|| type == 504) {
            holder.tv_zhifu_leixing_search.setText("支付宝支付收款");
            holder.iv_bill_search.setImageResource(R.drawable.zhifubao);
        } else if (type == 201 || type == 202 || type == 203 || type == 306 || type == 302 || type == 502 || type == 602 || type == 604
                || type == 802 || type == 804 || type == 904|| type == 505) {
            holder.tv_zhifu_leixing_search.setText("微信支付收款");
            holder.iv_bill_search.setImageResource(R.drawable.weixin);
        } else if (type == 303 || type == 307) {
            holder.tv_zhifu_leixing_search.setText("京东支付收款");
            holder.iv_bill_search.setImageResource(R.drawable.jindong);
        } else if (type == 401 || type == 402) {
            holder.tv_zhifu_leixing_search.setText("银联支付收款");
            holder.iv_bill_search.setImageResource(R.drawable.yinlian);
        } else if (type == 304) {
            holder.tv_zhifu_leixing_search.setText("翼支付支付收款");
            holder.iv_bill_search.setImageResource(R.drawable.yizhifu);
        } else if (type == 902) {
            holder.tv_zhifu_leixing_search.setText("快捷大额支付收款");
            holder.iv_bill_search.setImageResource(R.drawable.yinlian);
        }

        if (info.getPay_status() == 1) {

            holder.tv_zhuangtai_search.setText("收款成功");
            holder.tv_zhuangtai_search.setTextColor(SearchBillFragment.this.getResources().getColor(R.color.text_chenggong));

            holder.tv_jine_search.setText("￥ " + info.getTotal_amount());
            holder.tv_jine_search.setTextColor(SearchBillFragment.this.getResources().getColor(R.color.bill_text_hei));


        } else {
            holder.tv_zhuangtai_search.setText("未支付");
            holder.tv_zhuangtai_search.setTextColor(SearchBillFragment.this.getResources().getColor(R.color.bill_text));

            holder.tv_jine_search.setText("￥ " + info.getTotal_amount());
            holder.tv_jine_search.setTextColor(SearchBillFragment.this.getResources().getColor(R.color.bill_text));
        }
    }

}
