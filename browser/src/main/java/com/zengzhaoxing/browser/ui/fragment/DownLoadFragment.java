package com.zengzhaoxing.browser.ui.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zengzhaoxing.browser.R;
import com.zengzhaoxing.browser.bean.FileBean;
import com.zengzhaoxing.browser.presenter.DownLoadPresenter;
import com.zengzhaoxing.browser.view.ProgressView;
import com.zxz.www.base.app.ListFragment;
import com.zxz.www.base.net.download.Downloader;
import com.zxz.www.base.utils.MathUtil;
import com.zxz.www.base.view.TitleView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DownLoadFragment extends ListFragment<Downloader,DownLoadFragment.ViewHolder> implements Downloader.DownLoadListener {


    @Override
    protected void onBindViewHolder(ViewHolder viewHolder, Downloader bean, int position) {
        viewHolder.titleTv.setText(bean.getFileName());
        String sum = MathUtil.getFormatSize(bean.getContentLength());
        String curr = MathUtil.getFormatSize(bean.getDownLoadLength());
        if (bean.isComplete()) {
            viewHolder.descTv.setText(sum);
            viewHolder.downloadPv.setProgress(100);
        } else {
            viewHolder.descTv.setText(curr + "/" + sum);
            viewHolder.downloadPv.setProgress((int) (bean.getCurrProgress() * 100));
        }
        if (bean.isPause()) {
            viewHolder.downloadPv.pause();
        } else {
            viewHolder.downloadPv.start();
        }
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_download;
    }

    @Override
    protected void refreshTitle(TitleView titleView) {
        titleView.getTitleTv().setText(R.string.menu_download);
        setList(DownLoadPresenter.getInstance().getDownloaders());
        DownLoadPresenter.getInstance().setDownLoadListener(this);
    }

    @Override
    public void onDownLoad(int progress, Downloader downloader) {
        mAdapter.notifyItemChanged(mList.indexOf(downloader));
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.title_tv)
        TextView titleTv;
        @BindView(R.id.desc_tv)
        TextView descTv;
        @BindView(R.id.download_pv)
        ProgressView downloadPv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
