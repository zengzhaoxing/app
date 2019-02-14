package com.zengzhaoxing.browser.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zengzhaoxing.browser.R;
import com.zengzhaoxing.browser.presenter.DownPresenter;
import com.zengzhaoxing.browser.presenter.UrlCollectPresenter;
import com.zengzhaoxing.browser.ui.dialog.NoticeDialog;
import com.zxz.www.base.app.TitleFragment;
import com.zxz.www.base.utils.ResUtil;
import com.zxz.www.base.utils.SPUtil;
import com.zxz.www.base.utils.ToastUtil;
import com.zxz.www.base.view.TitleView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.zengzhaoxing.browser.ui.fragment.SearchFragment.SEARCH_HISTORY;

public class CleanDataFragment extends TitleFragment {
    @BindView(R.id.search_history_iv)
    ImageView searchHistoryIv;
    @BindView(R.id.search_history_ll)
    LinearLayout searchHistoryLl;
    @BindView(R.id.browser_history_iv)
    ImageView browserHistoryIv;
    @BindView(R.id.browser_history_ll)
    LinearLayout browserHistoryLl;
    @BindView(R.id.form_data_iv)
    ImageView formDataIv;
    @BindView(R.id.form_data_ll)
    LinearLayout formDataLl;
    @BindView(R.id.location_iv)
    ImageView locationIv;
    @BindView(R.id.location_ll)
    LinearLayout locationLl;
    @BindView(R.id.cookies_iv)
    ImageView cookiesIv;
    @BindView(R.id.cookies_ll)
    LinearLayout cookiesLl;
    @BindView(R.id.clean_tv)
    TextView cleanTv;
    Unbinder unbinder;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fra_clean_data;
    }

    @Override
    protected void refreshTitle(TitleView titleView) {
        titleView.getTitleTv().setText("清除浏览数据");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        searchHistoryLl.setSelected(true);
        browserHistoryLl.setSelected(true);
        formDataLl.setSelected(true);
        locationLl.setSelected(true);
        cookiesLl.setSelected(true);
        return rootView;
    }

    @OnClick({R.id.search_history_ll, R.id.browser_history_ll, R.id.form_data_ll, R.id.location_ll, R.id.cookies_ll, R.id.clean_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search_history_ll:
                searchHistoryLl.setSelected(!searchHistoryLl.isSelected());
                searchHistoryIv.setImageResource(searchHistoryLl.isSelected() ? R.drawable.check : R.drawable.check_off);
                renderBtn();
                break;
            case R.id.browser_history_ll:
                browserHistoryLl.setSelected(!browserHistoryLl.isSelected());
                browserHistoryIv.setImageResource(browserHistoryLl.isSelected() ? R.drawable.check : R.drawable.check_off);
                renderBtn();
                break;
            case R.id.form_data_ll:
                formDataLl.setSelected(!formDataLl.isSelected());
                formDataIv.setImageResource(formDataLl.isSelected() ? R.drawable.check : R.drawable.check_off);
                renderBtn();
                break;
            case R.id.location_ll:
                locationLl.setSelected(!locationLl.isSelected());
                locationIv.setImageResource(locationLl.isSelected() ? R.drawable.check : R.drawable.check_off);
                renderBtn();
                break;
            case R.id.cookies_ll:
                cookiesLl.setSelected(!cookiesLl.isSelected());
                cookiesIv.setImageResource(cookiesLl.isSelected() ? R.drawable.check : R.drawable.check_off);
                renderBtn();
                break;
            case R.id.clean_tv:
                NoticeDialog dialog = new NoticeDialog(mBaseActivity);
                dialog.show("清除所选数据？", new NoticeDialog.OnDialogClickListener() {
                    @Override
                    public void onOkClick() {
                        if (searchHistoryLl.isSelected()) {
                            SPUtil.remove(SEARCH_HISTORY);
                        }
                        if (browserHistoryLl.isSelected()) {
                            UrlCollectPresenter.getInstance().deleteAllHistory();
                        }
                        if (formDataLl.isSelected()) {

                        }
                        if (locationLl.isSelected()) {

                        }
                        if (cookiesLl.isSelected()) {
                            CookieSyncManager.createInstance(mBaseActivity);
                            CookieManager cookieManager = CookieManager.getInstance();
                            cookieManager.removeAllCookie();
                            CookieSyncManager.getInstance().sync();
                        }
                        ToastUtil.toast(R.string.clean_succeed);
                    }
                });
                break;
        }
    }

    private void renderBtn() {
        if (searchHistoryLl.isSelected() ||
                browserHistoryLl.isSelected() ||
                formDataLl.isSelected() ||
                locationLl.isSelected() ||
                cookiesLl.isSelected()) {
            cleanTv.setEnabled(true);
            cleanTv.setTextColor(ResUtil.getColor(R.color.red));
        } else {
            cleanTv.setEnabled(false);
            cleanTv.setTextColor(ResUtil.getColor(R.color.text_gray));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
