package com.zengzhaoxing.browser.ui.fragment;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zengzhaoxing.browser.MainActivity;
import com.zengzhaoxing.browser.R;
import com.zengzhaoxing.browser.bean.FileBean;
import com.zengzhaoxing.browser.bean.UrlBean;
import com.zengzhaoxing.browser.presenter.DownPresenter;
import com.zengzhaoxing.browser.ui.dialog.DelDownDlg;
import com.zengzhaoxing.browser.view.ProgressView;
import com.zxz.www.base.app.BaseFragment;
import com.zxz.www.base.app.ListFragment;
import com.zxz.www.base.utils.IntentUtil;
import com.zxz.www.base.utils.MathUtil;
import com.zxz.www.base.utils.NetSpeed;
import com.zxz.www.base.utils.ResUtil;
import com.zxz.www.base.utils.ViewUtil;
import com.zxz.www.base.view.TitleView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DownFragment extends ListFragment<FileBean,DownFragment.ViewHolder> implements  DownPresenter.OnUpDateUiListener {

    NetSpeed mNetSpeed;

    @Override
    protected void onBindViewHolder(ViewHolder viewHolder, FileBean bean, int position) {
        if (mNetSpeed == null) {
            mNetSpeed = new NetSpeed();
        }
        viewHolder.titleTv.setText(bean.getName());
        String sum = MathUtil.getFormatSize(bean.getContentLength());
        String curr = MathUtil.getFormatSize(bean.getDownLoadLength());
        viewHolder.downloadPv.setProgress(bean.getProgress());
        Drawable drawable = bean.getDrawable();
        if (drawable != null) {
            viewHolder.iconIv.setImageDrawable(bean.getDrawable());
        }else{
            viewHolder.iconIv.setImageResource(R.drawable.file);
        }
        if (bean.isComplete()) {
            viewHolder.descTv.setText(sum);
        } else if(!DownPresenter.getInstance().isDowning(bean)){
            viewHolder.descTv.setText(curr + "/" + sum + "  |  " + ResUtil.getString(R.string.paused));
        } else{
            viewHolder.descTv.setText(curr + "/" + sum + "  |  " + mNetSpeed.getNetSpeed());
        }
        if (DownPresenter.getInstance().isDowning(bean)) {
            viewHolder.downloadPv.start();
        } else {
            viewHolder.downloadPv.pause();
        }
        viewHolder.downloadPv.setTag(bean);
    }


    @Override
    protected int getItemLayoutId() {
        return R.layout.item_download;
    }

    @Override
    protected void onItemClick(FileBean bean) {
        if (bean.isComplete()) {
            IntentUtil.openFile(mBaseActivity,new File(bean.getPath()));
        } else {
            DownPresenter.getInstance().exChangeDownStatus(bean);
        }
    }

    @Override
    protected void onItemLongClick(FileBean bean) {
        FunListFragment fragment = new FunListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(FileBean.class.getName(), bean);
        fragment.setArguments(bundle);
        mBaseActivity.openNewFragment(fragment);
    }

    @Override
    protected void refreshTitle(TitleView titleView) {
        DownPresenter.getInstance().setOnUpDateUiListener(this);
        titleView.getTitleTv().setText(R.string.menu_download);
        titleView.getRightTv().setTextColor(ResUtil.getColor(R.color.blue));
        titleView.getRightTv().setText(R.string.clean_all);
        setList(DownPresenter.getInstance().getFileBeans());
        titleView.getRightTv().setVisibility(mList != null && mList.size() > 0 ? View.VISIBLE : View.GONE);
        titleView.getRightTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DelDownDlg delDownDlg = new DelDownDlg(getActivity());
                delDownDlg.show(null);
            }
        });
    }

    @Override
    public void onUpDateUi(FileBean bean) {
        if (mAdapter != null && mList != null) {
            mRecyclerView.getItemAnimator().setChangeDuration(0);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDelete(int... pos) {
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.title_tv)
        TextView titleTv;
        @BindView(R.id.desc_tv)
        TextView descTv;
        @BindView(R.id.download_pv)
        ProgressView downloadPv;
        @BindView(R.id.icon_iv)
        ImageView iconIv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
