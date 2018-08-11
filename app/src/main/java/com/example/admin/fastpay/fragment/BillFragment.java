package com.example.admin.fastpay.fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.admin.fastpay.R;
import com.example.admin.fastpay.adapter.BillAdapter;
import com.example.admin.fastpay.model.response.BillResp;
import com.example.admin.fastpay.customdialog.DatePickerDialog;
import com.example.admin.fastpay.customdialog.DateUtil;
import com.example.admin.fastpay.customdialog.ScreenPickerDialog;
import com.example.admin.fastpay.model.request.PageReq;
import com.example.admin.fastpay.net.RequestCaller;
import com.example.admin.fastpay.utils.UrlBase;
import com.zxz.www.base.app.BaseFragment;
import com.zxz.www.base.net.request.JsonRequester;
import com.zxz.www.base.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BillFragment extends BaseFragment implements View.OnClickListener {

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


    private static final int SELECT_TIME=1;
    private static final int SELECTION=2;
    private int mSelectState;
    private List<BillResp.DataBean> data1;
    private BillAdapter billAdapter;
    private MaterialRefreshLayout refresh;

    private Dialog dateDialog;
    private int year;
    private int month;
    private int day;

    private int page;

    private int[] mSelectTimeTerm;
    private List<String>[] mSelectionTerm;
    private boolean mHasSelect;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill,container,false);
        ListView lv_bill = (ListView) view.findViewById(R.id.lv_bill);
        LinearLayout ll_bill_datatime = (LinearLayout) view.findViewById(R.id.ll_bill_datatime);
        LinearLayout ll_bill_screen = (LinearLayout) view.findViewById(R.id.ll_bill_screen);
        TextView tv_bill_tishi = (TextView) view.findViewById(R.id.tv_bill_tishi);
        refresh = (MaterialRefreshLayout)view.findViewById(R.id.refresh);
        ImageView back = (ImageView) view.findViewById(R.id.back13);
        ImageView search = (ImageView) view.findViewById(R.id.search);
        refresh.setLoadMore(true);
        ll_bill_datatime.setOnClickListener(this);
        ll_bill_screen.setOnClickListener(this);
        lv_bill.setEmptyView(tv_bill_tishi);
        page = 1;
        requestData("1");
        data1 = new ArrayList<>();
        billAdapter = new BillAdapter(mBaseActivity);
        lv_bill.setAdapter(billAdapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
        lv_bill.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BillResp.DataBean dataBean = billAdapter.getCurrentData().get(position);
                BillItemInfoFragment.showFragment(mBaseActivity, dataBean);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBaseActivity.openNewFragmentWithDefaultAnim(new SearchBillFragment());
            }
        });
        refresh.setMaterialRefreshListener(new MyMaterialRefreshListener());
        return view;
    }

    //设置日期筛选dialog
    private void dataDialog() {
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        day = c.get(Calendar.DAY_OF_MONTH);

        final List<Integer> date = DateUtil.getDateForString(year + "-" + month + "-" + day);

        DatePickerDialog.Builder builder = new DatePickerDialog.Builder(mBaseActivity);

        builder.setOnDateSelectedListener(new DatePickerDialog.OnDateSelectedListener() {
            @Override
            public void onDateSelected(int[] dates) {
                mSelectTimeTerm = dates;
                doSelectTime();
                mHasSelect = true;
            }
        })
                //设置弹出dialog时停留的日期
                .setSelectYear(date.get(0) - 1)//设置年
                .setSelectMonth(date.get(1) - 1)//设置月
                .setSelectDay(date.get(2) - 1);//设置日

        dateDialog = builder.create();
        dateDialog.show();
    }

    private void doSelectTime() {
        if (mSelectTimeTerm == null) {
            return;
        }
        String s = "";
        if (mSelectTimeTerm[1] < 10) {
            if (mSelectTimeTerm[2] < 10) {
                s = mSelectTimeTerm[0] + "-" + "0" + mSelectTimeTerm[1] + "-" + "0" + mSelectTimeTerm[2];
            } else {
                s = mSelectTimeTerm[0] + "-" + "0" + mSelectTimeTerm[1] + "-" + mSelectTimeTerm[2];
            }
        } else {
            if (mSelectTimeTerm[2] < 10) {
                s = mSelectTimeTerm[0] + "-" + mSelectTimeTerm[1] + "-" + "0" + mSelectTimeTerm[2];
            } else {
                s = mSelectTimeTerm[0] + "-" + mSelectTimeTerm[1] + "-" + mSelectTimeTerm[2];
            }
        }
        if (mSelectTimeTerm[0] < year) {
            billAdapter.selectData(s);
            refresh.setLoadMore(false);
        } else if (mSelectTimeTerm[0] == year && mSelectTimeTerm[1] < month) {
            billAdapter.selectData(s);
            refresh.setLoadMore(false);
        } else if (mSelectTimeTerm[0] == year && mSelectTimeTerm[1] == month && mSelectTimeTerm[2] <= day) {
            billAdapter.selectData(s);
            refresh.setLoadMore(false);
        } else {
            ToastUtil.toast("选择日期不能大于当前日期");
        }
    }

    public class MyMaterialRefreshListener extends MaterialRefreshListener {

        @Override
        public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
            state = STATE_REFRESH;
            page = 1;
            requestData("1");
        }

        @Override
        public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
            state = STATE_MORE;
            requestData(String.valueOf(page + 1));
        }
    }

    private PageReq mPageReq = new PageReq();

    public void requestData(final String page) {

        mPageReq.setPage(page);
        RequestCaller.post(UrlBase.ZHANGDAN, mPageReq, BillResp.class, new JsonRequester.OnResponseListener<BillResp>() {
            @Override
            public void onResponse(BillResp response, int resCode) {
                if (response != null && response.getData() != null && refresh != null) {
                    data1.clear();
                    data1.addAll(response.getData());
                    if (data1 == null || data1.size() == 0) {
                        if (state == STATE_REFRESH) {
                            refresh.finishRefresh();
                        } else if (state == STATE_MORE) {
                            refresh.finishRefreshLoadMore();
                        }
                    } else {
                        if (TextUtils.isEmpty(page)) {
                            billAdapter.setAllData(data1);
                            if (mHasSelect) {
                                if(mSelectState==SELECT_TIME) {
                                    doSelectTime();
                                }else if(mSelectState==SELECTION) {
                                    doSelection();
                                }
                                mHasSelect = false;
                            }
                        } else {
                            showData();
                        }
                    }
                }
            }
        });
    }

    private void showData() {
        switch (state) {
            case STATE_NORMAL://正常，正在加载数据 第一页
                //设置适配器
                //1.设置适配器-item布局，绑定数据，传入数据
                billAdapter.setData(data1);
                break;

            case STATE_REFRESH: //加载第一页，把原来的数据清空
                refresh.setLoadMore(true);
                billAdapter.setData(data1);
                refresh.finishRefresh();//结束下拉刷新...
                break;

            case STATE_MORE://把得到的数据加载到原来的集合中
                //1.把数据添加到原来集合的末尾
                billAdapter.setLoadMoreData(data1);
                page++;
                refresh.finishRefreshLoadMore();
                break;
        }

        state = STATE_NORMAL;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_bill_datatime:
                mSelectTimeTerm = null;
                mSelectionTerm=null;
                mSelectState = SELECT_TIME;
                requestData("");
                dataDialog();
                break;
            case R.id.ll_bill_screen:
                mSelectTimeTerm = null;
                mSelectionTerm=null;
                mSelectState = SELECTION;
                requestData("");
                ScreenPickerDialog.Builder builder = new ScreenPickerDialog.Builder(mBaseActivity);
                builder.setOnDateSelectedListener(new ScreenPickerDialog.OnDateSelectedListener() {
                    @Override
                    public void onDateSelected(List<String> mData, List<String> stateData) {
                        mSelectionTerm =  new List[]{mData,stateData};
                        doSelection();
                        mHasSelect = true;
                    }
                });
                dateDialog = builder.create();
                dateDialog.show();
                break;
        }
    }


    private void doSelection() {
        if(mSelectionTerm==null) {
            return;
        }
        refresh.setLoadMore(false);
        billAdapter.selectData(mSelectionTerm[0], mSelectionTerm[1]);
    }

}



