package com.zengzhaoxing.browser.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.zengzhaoxing.browser.R;
import com.zengzhaoxing.browser.presenter.UrlCollectPresenter;
import com.zengzhaoxing.browser.ui.adapter.UrlItemAdapter;
import com.zxz.www.base.app.BaseActivity;
import com.zxz.www.base.app.TitleFragment;
import com.zxz.www.base.utils.DensityUtil;
import com.zxz.www.base.view.TitleView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class UrlCollectFragment extends TitleFragment {


    @BindView(R.id.url_collect_lv)
    ListView urlCollectLv;
    Unbinder unbinder;

    public static void show(BaseActivity activity, boolean isCollect) {
        UrlCollectFragment fragment = new UrlCollectFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(boolean.class.getName(), isCollect);
        fragment.setArguments(bundle);
        activity.openNewFragmentWithDefaultAnim(fragment);
    }

    private boolean mIsCollect;

    private UrlItemAdapter mUrlItemAdapter;

    @Override
    public int getContentLayoutId() {
        mIsCollect = getArguments().getBoolean(boolean.class.getName());
        return R.layout.fra_url_collect;
    }

    @Override
    public void refreshTitle(TitleView titleView) {
        titleView.getTitleTv().setText(mIsCollect ? R.string.menu_collect : R.string.menu_history);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        mUrlItemAdapter = new UrlItemAdapter(mIsCollect ? UrlCollectPresenter.getInstance().getAllCollect() : UrlCollectPresenter.getInstance().getAllHistory());
        urlCollectLv.setAdapter(mUrlItemAdapter);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
