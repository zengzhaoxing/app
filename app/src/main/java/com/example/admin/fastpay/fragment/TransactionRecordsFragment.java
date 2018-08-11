package com.example.admin.fastpay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.admin.fastpay.R;
import com.example.admin.fastpay.adapter.RecordAdapter;
import com.example.admin.fastpay.model.response.RecordResp;
import com.example.admin.fastpay.model.request.PageReq;
import com.example.admin.fastpay.net.RequestCaller;
import com.example.admin.fastpay.utils.UrlBase;
import com.zxz.www.base.app.BaseFragment;
import com.zxz.www.base.net.request.JsonRequester;

import java.util.ArrayList;
import java.util.List;

public class TransactionRecordsFragment extends BaseFragment {

    /**
     * 正常状态
     */
    private static final int STATE_NORMAL = 1;

    /**
     * 下拉刷新状态
     */
    private static final int STATE_REFRESH = 2;

    /**
     * 上拉刷新状态-加载更多状态
     */
    private static final int STATE_MORE = 3;

    /**
     * 当前状态
     */
    private int state = STATE_NORMAL;
    private ListView listView;
    private MaterialRefreshLayout materialRefreshLayout;
    private TextView tishi;
    private Button todaysettlement;
    private Button historysettlement;
    private ImageView back;
    private int page;
    //定义boolean的参数  判断请求今天或者历史   true为今日结算    false为历史结算
    private boolean time;
    private List<RecordResp.Record> totalList;
    private RecordAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_settlement, container, false);
        time = true;
        page = 1;

        requestToday(page+"");

        listView = (ListView) view.findViewById(R.id.lv_record);
        materialRefreshLayout = (MaterialRefreshLayout) view.findViewById(R.id.record_refresh);
        tishi = (TextView) view.findViewById(R.id.tv_record_tishi);
        totalList = new ArrayList<>();
        todaysettlement = (Button) view.findViewById(R.id.settlement_today);
        historysettlement = (Button) view.findViewById(R.id.settlement_history);

        back = (ImageView) view.findViewById(R.id.back17);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
        materialRefreshLayout.setLoadMore(true);
        materialRefreshLayout.setMaterialRefreshListener(new  MyMaterialRefreshListener());

        adapter = new RecordAdapter(mBaseActivity,totalList);
        listView.setAdapter(adapter);

        listView.setEmptyView(tishi);
        //listview的点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RecordResp.Record order = totalList.get(position);
                RecordDetailFragment.showFragment(mBaseActivity,order);
            }
        });


        todaysettlement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                todaysettlement.setTextColor(getResources().getColor(R.color.blue));
                historysettlement.setTextColor(getResources().getColor(R.color.white));
                todaysettlement.setBackgroundColor(getResources().getColor(R.color.white));
                historysettlement.setBackgroundColor(getResources().getColor(R.color.blue));

                time = true;
                //清楚数据源
                totalList.clear();
                //跳到第一个条目
                listView.setSelection(0);
                page = 1;
                //page=1  请求今天结算记录的接口
                requestToday(page+"");
            }
        });

        historysettlement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                historysettlement.setTextColor(getResources().getColor(R.color.blue));
                todaysettlement.setTextColor(getResources().getColor(R.color.white));
                todaysettlement.setBackgroundColor(getResources().getColor(R.color.blue));
                historysettlement.setBackgroundColor(getResources().getColor(R.color.white));
                time = false;
                totalList.clear();
                listView.setSelection(0);
                page = 1;
                requestAll(page+"");
            }
        });
         return view;

    }

    private void requestAll(String q) {

        RequestCaller.post(UrlBase.MDRECORD, new PageReq(q), RecordResp.class, new JsonRequester.OnResponseListener<RecordResp>() {
            @Override
            public void onResponse(RecordResp response, int resCode) {
                if (listView == null) {
                    return;
                }
                if (response != null && response.getData() != null) {
                    totalList.clear();
                    totalList.addAll(response.getData());
                    page++;
                    if (state == STATE_REFRESH) {
                        materialRefreshLayout.finishRefresh();//结束下拉刷新...
                    } else if (state == STATE_MORE) {
                        materialRefreshLayout.finishRefreshLoadMore();
                    }
                    adapter.notifyDataSetChanged();
                }
                if (state == STATE_REFRESH) {
                    materialRefreshLayout.finishRefresh();//结束下拉刷新...
                } else if (state == STATE_MORE) {
                    materialRefreshLayout.finishRefreshLoadMore();
                }
            }
        });
    }

    private void requestToday(String p) {

        RequestCaller.post(UrlBase.MDRECORD, new PageReq(p,"today"), RecordResp.class, new JsonRequester.OnResponseListener<RecordResp>() {
            @Override
            public void onResponse(RecordResp response, int resCode) {
                if (listView == null) {
                    return;
                }
                if (response != null && response.getData() != null) {
                    totalList.clear();
                    totalList.addAll(response.getData());
                    page++;
                    if (state == STATE_REFRESH) {
                        materialRefreshLayout.finishRefresh();//结束下拉刷新...
                    } else if (state == STATE_MORE) {
                        materialRefreshLayout.finishRefreshLoadMore();
                    }
                    adapter.notifyDataSetChanged();
                }
                if (state == STATE_REFRESH) {
                    materialRefreshLayout.finishRefresh();//结束下拉刷新...
                } else if (state == STATE_MORE) {
                    materialRefreshLayout.finishRefreshLoadMore();
                }
            }
        });
    }

    private class MyMaterialRefreshListener extends MaterialRefreshListener {

        @Override
        public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
            state = STATE_REFRESH;
            totalList.clear();
            page = 1;
            if (time){
                requestToday(page+"");
            }else if (!time){
                requestAll(page+"");
            }

        }

        /**
         * 加载更多
         *
         * @param materialRefreshLayout
         */
        @Override
        public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
            state = STATE_MORE;
            if (time){
                requestToday(page+"");
            }else if (!time){
                requestAll(page+"");
            }
        }
    }

}
