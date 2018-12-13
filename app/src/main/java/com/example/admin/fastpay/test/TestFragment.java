package com.example.admin.fastpay.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.fastpay.R;
import com.zxz.www.base.adapter.ViewPagerAdapter;
import com.zxz.www.base.app.MainFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TestFragment extends MainFragment {


    Unbinder unbinder;
    @BindView(R.id.gv2)
    TextSelector gv2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        unbinder = ButterKnife.bind(this, view);
        List<View> viewList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            View view1 = new MyView(getActivity());
            view1.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return false;
                }
            });
            if (i == 0) {
                view1.setBackgroundResource(R.color.red);
            }
            if (i == 1) {
                view1.setBackgroundResource(R.color.blue);
            }
            if (i == 2) {
                view1.setBackgroundResource(R.color.yellow);
            }
            viewList.add(view1);
        }
        gv2.setAdapter(new ViewPagerAdapter(viewList,viewList.size()));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
