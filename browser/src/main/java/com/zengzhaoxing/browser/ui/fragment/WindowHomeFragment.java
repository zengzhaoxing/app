package com.zengzhaoxing.browser.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zengzhaoxing.browser.Config;
import com.zengzhaoxing.browser.R;
import com.zengzhaoxing.browser.bean.UrlBean;
import com.zengzhaoxing.browser.ui.dialog.AddUrlDlg;
import com.zhaoxing.view.sharpview.SharpImageView;
import com.zxz.www.base.app.BaseApp;
import com.zxz.www.base.utils.SPUtil;
import com.zxz.www.base.utils.ViewUtil;
import com.zxz.www.base.view.ImageTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class WindowHomeFragment extends WindowChildFragment implements View.OnClickListener {

    Unbinder unbinder;
    @BindView(R.id.url_gv)
    GridView urlGv;

    private static final String URL_LIST = "WindowHomeFragment.URL_LIST";

    List<UrlBean> beans;

    BaseAdapter mAdapter;

    private boolean mDleStatus;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_window_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        beans = SPUtil.getList(URL_LIST,UrlBean.class);
        if (beans == null) {
            beans = new ArrayList<>();
            beans.add(new UrlBean("https://www.sogou.com/", "搜狗"));
            beans.add(new UrlBean("https://www.baidu.com/", "百度"));
            beans.add(new UrlBean("https://www.taobao.com/", "淘宝"));
            beans.add(new UrlBean("https://www.sina.com.cn/", "新浪"));
            beans.add(new UrlBean("https://www.toutiao.com/", "头条"));
            beans.add(new UrlBean("https://www.tmall.com/", "天猫"));
            beans.add(new UrlBean("https://www.jd.com/", "京东"));
            beans.add(new UrlBean("https://www.zhihu.com/", "知乎"));
            SPUtil.putList(URL_LIST,beans);
        }
        mAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return mDleStatus ? beans.size() : beans.size() + 1;
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
                    convertView = LayoutInflater.from(mBaseActivity).inflate(R.layout.item_url_icon, parent, false);
                }
                SharpImageView imageView = convertView.findViewById(R.id.url_iv);
                TextView textView = convertView.findViewById(R.id.title_tv);
                ImageView delIv = convertView.findViewById(R.id.del_iv);
                delIv.setVisibility(mDleStatus ? View.VISIBLE : View.GONE);
                delIv.setOnClickListener(WindowHomeFragment.this);
                if (position >= beans.size()) {
                    Picasso.with(BaseApp.getContext()).load("dd").error(R.drawable.add_url).into(imageView);
                    textView.setText(R.string.add);
                    delIv.setTag(null);
                } else {
                    UrlBean bean = beans.get(position);
                    Picasso.with(BaseApp.getContext()).load(bean.getIconUrl()).error(R.mipmap.icon).into(imageView);
                    textView.setText(bean.getTitle());
                    delIv.setTag(bean);
                }
                return convertView;
            }
        };
        urlGv.setAdapter(mAdapter);
        urlGv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (!mDleStatus && position < mAdapter.getCount()) {
                    mDleStatus = true;
                    mAdapter.notifyDataSetChanged();
                }
                return true;
            }
        });
        urlGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mDleStatus) {
                    return;
                }
                if (position >= beans.size()) {
                    AddUrlDlg dlg = new AddUrlDlg(mBaseActivity);
                    dlg.show(new AddUrlDlg.OnAddListener() {
                        @Override
                        public void OnAdd(String url, String title) {
                            beans.add(new UrlBean(url, title));
                            mAdapter.notifyDataSetChanged();
                            SPUtil.putList(URL_LIST,beans);
                        }
                    });
                } else {
                    UrlBean bean = beans.get(position);
                    mWindowFragment.openNew(bean.getUrl());
                }
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

    @Override
    public void onClick(View v) {
        UrlBean bean = (UrlBean) v.getTag();
        beans.remove(bean);
        SPUtil.putList(URL_LIST,beans);
        mAdapter.notifyDataSetChanged();
    }


    @Override
    protected boolean handleBackEvent() {
        if (mDleStatus) {
            mDleStatus = false;
            mAdapter.notifyDataSetChanged();
            return true;
        }
        return super.handleBackEvent();
    }
}
