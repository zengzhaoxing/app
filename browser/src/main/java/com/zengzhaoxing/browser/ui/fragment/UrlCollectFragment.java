package com.zengzhaoxing.browser.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zengzhaoxing.browser.MainActivity;
import com.zengzhaoxing.browser.R;
import com.zengzhaoxing.browser.bean.UrlBean;
import com.zengzhaoxing.browser.presenter.UrlCollectPresenter;
import com.zengzhaoxing.browser.ui.dialog.NoticeDialog;
import com.zhaoxing.view.sharpview.SharpImageView;
import com.zxz.www.base.app.BaseActivity;
import com.zxz.www.base.app.BaseFragment;
import com.zxz.www.base.app.ListFragment;
import com.zxz.www.base.utils.ResUtil;
import com.zxz.www.base.utils.ViewUtil;
import com.zxz.www.base.view.TitleView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UrlCollectFragment extends ListFragment<UrlBean,UrlCollectFragment.ViewHolder> {

    public static void show(BaseActivity activity, boolean isCollect) {
        UrlCollectFragment fragment = new UrlCollectFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(boolean.class.getName(), isCollect);
        fragment.setArguments(bundle);
        activity.openNewFragmentWithDefaultAnim(fragment);
    }

    private boolean mIsCollect;


    @Override
    protected void onBindViewHolder(UrlCollectFragment.ViewHolder viewHolder, UrlBean bean, int position) {
        viewHolder.urlTv.setText(bean.getUrl());
        viewHolder.urlTitleTv.setText(bean.getTitle());
        ViewUtil.LoadImg(viewHolder.urlIv, bean.getIconUrl(), R.mipmap.icon);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_url;
    }

    @Override
    public boolean onLongClick(View v) {
        UrlBean bean = (UrlBean) v.getTag();
        Bundle bundle = new Bundle();
        bundle.putSerializable(UrlBean.class.getName(), bean);
        FunListFragment.open(mBaseActivity,bundle);
        return true;
    }

    @Override
    public void onClick(View v) {
        UrlBean bean = (UrlBean) v.getTag();
        mBaseActivity.closeCurrentFragment();
        ((MainActivity)mBaseActivity).getHome().openNewWindow(bean);
    }

    @Override
    protected void refreshTitle(TitleView titleView) {
        mIsCollect = getArguments().getBoolean(boolean.class.getName());
        setList(mIsCollect ? UrlCollectPresenter.getInstance().getAllCollect() : UrlCollectPresenter.getInstance().getAllHistory());
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
                        setList(null);
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
    protected void onTopFragmentExit(Class<? extends BaseFragment> topFragmentClass, Bundle params) {
        super.onTopFragmentExit(topFragmentClass, params);
        setList(mIsCollect ? UrlCollectPresenter.getInstance().getAllCollect() : UrlCollectPresenter.getInstance().getAllHistory());
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.url_iv)
        SharpImageView urlIv;
        @BindView(R.id.url_title_tv)
        TextView urlTitleTv;
        @BindView(R.id.url_tv)
        TextView urlTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
