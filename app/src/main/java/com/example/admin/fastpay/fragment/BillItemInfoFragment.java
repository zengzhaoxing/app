package com.example.admin.fastpay.fragment;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.fastpay.R;
import com.example.admin.fastpay.model.response.BillResp;
import com.zxz.www.base.app.BaseActivity;
import com.zxz.www.base.app.BaseFragment;

public class BillItemInfoFragment extends BaseFragment {

    private TextView tv_item_jine;
    private TextView tv_item_time;
    private TextView tv_item_mode;
    private TextView tv_item_number;
    private TextView tv_item_state;

    private static final String BILL = "bill_info";

    public static void showFragment(BaseActivity activity,BillResp.DataBean data) {
        BillItemInfoFragment fragment = new BillItemInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(BILL, data);
        fragment.setArguments(bundle);
        activity.openNewFragmentWithDefaultAnim(fragment);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill_item_info,container,false);
        tv_item_jine = (TextView) view.findViewById(R.id.tv_item_jine);
        tv_item_time = (TextView) view.findViewById(R.id.tv_item_time);
        tv_item_mode = (TextView) view.findViewById(R.id.tv_item_mode);
        tv_item_number = (TextView) view.findViewById(R.id.tv_item_number);
        tv_item_state = (TextView) view.findViewById(R.id.tv_item_state);
        ImageView back = (ImageView) view.findViewById(R.id.back11);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
        initData();
        return view;
    }

    private void initData() {
        BillResp.DataBean data = (BillResp.DataBean) getArguments().getSerializable(BILL);

        tv_item_jine.setText("￥ " + data.getTotal_amount());

        String substring = data.getCreated_at().getDate().substring(0, 19);
        tv_item_time.setText(substring);

        int type = data.getType();
        if(type == 101){
            tv_item_mode.setText("支付宝-当面付支付");
        } else if(type == 102){
            tv_item_mode.setText("支付宝-口碑支付");
        }else if(type == 103){
            tv_item_mode.setText("支付宝-机具枪支付");
        }else if(type == 104){
            tv_item_mode.setText("支付宝-固定金额支付");
        }else if(type == 105) {
            tv_item_mode.setText("支付宝-（口碑）机具枪支付");
        }else if(type == 201){
            tv_item_mode.setText("微信-支付二维码支付");
        }else if(type == 202){
            tv_item_mode.setText("微信-机具枪支付");
        }else if(type == 203) {
            tv_item_mode.setText("微信-固定金额支付");
        }else if(type == 301){
            tv_item_mode.setText("平安-支付二维码支付");
        }else if(type == 302){
            tv_item_mode.setText("平安-微信支付");
        }else if(type == 303){
            tv_item_mode.setText("平安-京东支付");
        }else if(type == 304){
            tv_item_mode.setText("平安-翼支付支付");
        }else if(type == 305){
            tv_item_mode.setText("平安-支付宝机具枪支付");
        }else if(type == 306){
            tv_item_mode.setText("平安-微信机具枪支付");
        }else if(type == 307){
            tv_item_mode.setText("平安-京东机具枪支付");
        }else if(type == 401){
            tv_item_mode.setText("银联-支付二维码支付");
        }else if(type == 402){
            tv_item_mode.setText("银联-支付机具枪支付");
        }else if(type == 501){
            tv_item_mode.setText("民生-支付宝支付");
        }else if(type == 502){
            tv_item_mode.setText("民生-微信支付");
        }else if(type == 503){
            tv_item_mode.setText("民生-QQ钱包支付");
        }else if(type == 601){
            tv_item_mode.setText("浦发-支付宝支付");
        }else if(type == 602){
            tv_item_mode.setText("浦发-微信支付");
        }else if(type == 603){
            tv_item_mode.setText("浦发-支付宝机具枪支付");
        }else if(type == 604){
            tv_item_mode.setText("浦发-微信机具枪支付");
        }else if(type == 801){
            tv_item_mode.setText("微众-支付宝二维码支付");
        }else if(type == 802){
            tv_item_mode.setText("微众-微信二维码支付");
        }else if(type == 803){
            tv_item_mode.setText("微众-支付宝机具枪支付");
        }else if(type == 804){
            tv_item_mode.setText("微众-微信机具枪支付");
        }else if(type == 901){
            tv_item_mode.setText("中信-支付宝二维码支付");
        }else if(type == 902){
            tv_item_mode.setText("快捷大额支付");
        }else if(type == 903){
            tv_item_mode.setText("中信-支付宝机具枪支付");
        }else if(type == 904){
            tv_item_mode.setText("中信-微信机具枪支付");
        }else if(type == 504){
            tv_item_mode.setText("支付宝-机具枪支付");
        }else if(type == 505){
            tv_item_mode.setText("微信-机具枪支付");
        }

        tv_item_number.setText(data.getOut_trade_no());

        if(data.getPay_status() == 1){
            tv_item_state.setText("收款成功");
            tv_item_state.setTextColor(this.getResources().getColor(R.color.text_chenggong));
        }else{
            tv_item_state.setText("未支付");
            tv_item_state.setTextColor(this.getResources().getColor(R.color.text_content));
        }
    }

}

