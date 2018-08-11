package com.example.admin.fastpay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.fastpay.R;
import com.example.admin.fastpay.model.response.RecordResp;
import com.zxz.www.base.app.BaseActivity;
import com.zxz.www.base.app.BaseFragment;

public class RecordDetailFragment extends BaseFragment {


    private static final String RECORD = "record";

    public static void showFragment(BaseActivity activity, RecordResp.Record record) {
        RecordDetailFragment fragment = new RecordDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(RECORD, record);
        fragment.setArguments(bundle);
        activity.openNewFragmentWithDefaultAnim(fragment);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record_detical, container, false);
        TextView totalmoney = (TextView) view.findViewById(R.id.record_totalmoney);
        TextView money = (TextView) view.findViewById(R.id.record_money);
        TextView time = (TextView) view.findViewById(R.id.record_time);
        TextView number = (TextView) view.findViewById(R.id.record_munber);
        ImageView back = (ImageView) view.findViewById(R.id.back36);


        RecordResp.Record order = (RecordResp.Record) getArguments().getSerializable(RECORD);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
        totalmoney.setText(order.getTotalAmount()+"元");
        money.setText(order.getAmount()+"元");
        time.setText(order.getCreatedAt());
        number.setText(order.getTransferNo());
        return view;
    }

}
