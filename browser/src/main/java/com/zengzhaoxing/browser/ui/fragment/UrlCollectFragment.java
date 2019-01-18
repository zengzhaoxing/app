package com.zengzhaoxing.browser.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zengzhaoxing.browser.MainActivity;
import com.zengzhaoxing.browser.R;
import com.zengzhaoxing.browser.bean.UrlBean;
import com.zengzhaoxing.browser.presenter.UrlCollectPresenter;
import com.zengzhaoxing.browser.ui.adapter.UrlItemAdapter;
import com.zengzhaoxing.browser.ui.dialog.NoticeDialog;
import com.zxz.www.base.app.BaseActivity;
import com.zxz.www.base.app.BaseFragment;
import com.zxz.www.base.app.TitleFragment;
import com.zxz.www.base.utils.DensityUtil;
import com.zxz.www.base.utils.ResUtil;
import com.zxz.www.base.view.TitleView;

import java.util.List;

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
        titleView.getRightTv().setText(R.string.clean_all);
        titleView.getRightTv().setVisibility(View.VISIBLE);
        titleView.getRightTv().setTextColor(ResUtil.getColor(R.color.blue));
        titleView.getRightTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new NoticeDialog(mBaseActivity).show(mIsCollect ? R.string.q_clean_collect : R.string.q_clean_all, new NoticeDialog.OnDialogClickListener() {
                    @Override
                    public void onOkClick() {
                        mUrlItemAdapter.setUrlBeans(null);
                        if (mIsCollect) {
                            UrlCollectPresenter.getInstance().deleteAllCollect();
                        } else {
                            UrlCollectPresenter.getInstance().deleteAllHistory();
                        }
                    }
                });
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        mUrlItemAdapter = new UrlItemAdapter(mIsCollect ? UrlCollectPresenter.getInstance().getAllCollect() : UrlCollectPresenter.getInstance().getAllHistory());
        urlCollectLv.setAdapter(mUrlItemAdapter);
        urlCollectLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UrlBean bean = (UrlBean) mUrlItemAdapter.getItem(position);
                mBaseActivity.closeCurrentFragment();
                ((MainActivity)mBaseActivity).getHome().openNewWindow(bean);
            }
        });
        urlCollectLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                UrlBean bean = (UrlBean) mUrlItemAdapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable(UrlBean.class.getName(), bean);
                FunListFragment.open(mBaseActivity,bundle);
                return true;
            }
        });
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected void onTopFragmentExit(Class<? extends BaseFragment> topFragmentClass, Bundle params) {
        super.onTopFragmentExit(topFragmentClass, params);
        mUrlItemAdapter.setUrlBeans(mIsCollect ? UrlCollectPresenter.getInstance().getAllCollect() : UrlCollectPresenter.getInstance().getAllHistory());
    }
}
