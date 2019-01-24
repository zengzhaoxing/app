package com.zengzhaoxing.browser.ui.fragment;

import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zengzhaoxing.browser.R;
import com.zengzhaoxing.browser.bean.FileBean;
import com.zengzhaoxing.browser.presenter.DownPresenter;
import com.zengzhaoxing.browser.ui.dialog.DelDownDlg;
import com.zengzhaoxing.browser.view.ProgressView;
import com.zxz.www.base.app.ListFragment;
import com.zxz.www.base.utils.MathUtil;
import com.zxz.www.base.utils.ResUtil;
import com.zxz.www.base.utils.ViewUtil;
import com.zxz.www.base.view.TitleView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DownFragment extends ListFragment<FileBean,DownFragment.ViewHolder> implements  DownPresenter.OnUpDateUiListener, DialogInterface.OnDismissListener {


    @Override
    protected void onBindViewHolder(ViewHolder viewHolder, FileBean bean, int position) {
        viewHolder.titleTv.setText(bean.getName());
        String sum = MathUtil.getFormatSize(bean.getContentLength());
        String curr = MathUtil.getFormatSize(bean.getDownLoadLength());
        viewHolder.downloadPv.setProgress(bean.getProgress());
        if (bean.isComplete()) {
            viewHolder.descTv.setText(sum);
        } else {
            viewHolder.descTv.setText(curr + "/" + sum);
        }
        if (DownPresenter.getInstance(getActivity()).isDowning(bean)) {
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
    public void onClick(View v) {
        if (ViewUtil.isDoubleRequest(v)) {
            return;
        }
        FileBean bean = (FileBean) v.getTag();
        DownPresenter.getInstance(getActivity()).exChangeDownStatus(bean);
    }

    @Override
    protected void refreshTitle(TitleView titleView) {
        DownPresenter.getInstance(getActivity()).setOnUpDateUiListener(this);
        titleView.getTitleTv().setText(R.string.menu_download);
        titleView.getRightTv().setTextColor(ResUtil.getColor(R.color.blue));
        titleView.getRightTv().setText(R.string.clean_all);
        setList(DownPresenter.getInstance(getActivity()).getFileBeans());
        titleView.getRightTv().setVisibility(mList != null && mList.size() > 0 ? View.VISIBLE : View.GONE);
        titleView.getRightTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DelDownDlg delDownDlg = new DelDownDlg(getActivity());
                delDownDlg.setOnDismissListener(DownFragment.this);
                delDownDlg.show(null);
            }
        });
    }

    @Override
    public void onUpDateUi(FileBean bean) {
        if (mAdapter != null && mList != null) {
            mAdapter.notifyItemChanged(mList.indexOf(bean));
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        mAdapter.notifyDataSetChanged();
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
