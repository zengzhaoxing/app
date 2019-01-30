package com.zengzhaoxing.browser.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zengzhaoxing.browser.Config;
import com.zengzhaoxing.browser.R;
import com.zengzhaoxing.browser.bean.UrlBean;
import com.zxz.www.base.app.BaseApp;
import com.zxz.www.base.utils.ViewUtil;
import com.zxz.www.base.view.ImageTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class WindowHomeFragment extends WindowChildFragment {

    Unbinder unbinder;
    @BindView(R.id.url_gv)
    GridView urlGv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_window_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        final List<UrlBean> beans = Config.getUrlList();
        urlGv.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {

                return beans.size();
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(mBaseActivity).inflate(R.layout.item_url_icon,parent,false);
                }
                UrlBean bean = beans.get(position);
                ImageView imageView = convertView.findViewById(R.id.url_iv);
                TextView textView = convertView.findViewById(R.id.title_tv);
                Picasso.with(BaseApp.getContext()).load(bean.getIconUrl()).error(R.mipmap.icon).into(imageView);
                textView.setText(bean.getTitle());
                return convertView;
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.search_srl)
    public void onViewClicked() {
        SearchFragment fragment = new SearchFragment();
        mBaseActivity.openNewFragment(fragment);
    }


}
