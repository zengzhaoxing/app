package com.zengzhaoxing.browser.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zengzhaoxing.browser.MainActivity;
import com.zengzhaoxing.browser.R;
import com.zengzhaoxing.browser.presenter.DownPresenter;
import com.zengzhaoxing.browser.presenter.UrlCollectPresenter;
import com.zengzhaoxing.browser.ui.dialog.NoticeDialog;
import com.zxz.www.base.app.SlideFragment;
import com.zxz.www.base.utils.SPUtil;
import com.zxz.www.base.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.zengzhaoxing.browser.ui.fragment.SearchFragment.SEARCH_HISTORY;

public class MenuFragment extends SlideFragment {

    Unbinder unbinder;
    @BindView(R.id.add_collect_view)
    LinearLayout addCollectView;

    @Override
    public View getSlideView(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.fra_menu, parent, false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        boolean isEnable = ((MainActivity) mBaseActivity).getHome().getUrlBean() != null;
        addCollectView.setEnabled(isEnable);
        addCollectView.getChildAt(0).setEnabled(isEnable);
        addCollectView.getChildAt(1).setEnabled(isEnable);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.add_collect_view, R.id.collect_view, R.id.history_view, R.id.download_view, R.id.exit_view, R.id.clean_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_collect_view:
                mBaseActivity.closeCurrentFragment();
                UrlCollectPresenter.getInstance().addCollect(((MainActivity)mBaseActivity).getHome().getUrlBean());
                break;
            case R.id.collect_view:
                mBaseActivity.closeCurrentFragment();
                UrlCollectFragment.show(mBaseActivity,true);
                break;
            case R.id.history_view:
                mBaseActivity.closeCurrentFragment();
                UrlCollectFragment.show(mBaseActivity,false);
                break;
            case R.id.download_view:
                mBaseActivity.closeCurrentFragment();
                mBaseActivity.openNewFragmentWithDefaultAnim(new DownFragment());
                break;
            case R.id.clean_view:
                NoticeDialog dialog = new NoticeDialog(mBaseActivity);
                dialog.show(R.string.q_clean_record, new NoticeDialog.OnDialogClickListener() {
                    @Override
                    public void onOkClick() {
                        UrlCollectPresenter.getInstance().deleteAllHistory();
                        DownPresenter.getInstance().delete(false);
                        SPUtil.remove(SEARCH_HISTORY);
                        ToastUtil.toast(R.string.clean_succeed);
                        mBaseActivity.closeCurrentFragment();
                    }
                });
                break;
            case R.id.exit_view:
                mBaseActivity.finish();
                break;
        }
    }
}
