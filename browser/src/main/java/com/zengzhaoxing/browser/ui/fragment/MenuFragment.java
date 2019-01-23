package com.zengzhaoxing.browser.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zengzhaoxing.browser.MainActivity;
import com.zengzhaoxing.browser.R;
import com.zengzhaoxing.browser.presenter.UrlCollectPresenter;
import com.zxz.www.base.app.SlideFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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
                mBaseActivity.openNewFragmentWithDefaultAnim(new DownLoadFragment());
                break;
            case R.id.clean_view:
                break;
            case R.id.exit_view:
                mBaseActivity.finish();
                break;
        }
    }
}
