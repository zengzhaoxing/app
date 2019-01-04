package com.zengzhaoxing.browser.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zengzhaoxing.browser.Constants;
import com.zengzhaoxing.browser.R;
import com.zengzhaoxing.browser.bean.BaiduSuggestion;
import com.zengzhaoxing.browser.net.BaiduParser;
import com.zengzhaoxing.browser.ui.adapter.BDSuggestionAdapter;
import com.zxz.www.base.app.BaseFragment;
import com.zxz.www.base.net.request.JsonRequester;
import com.zxz.www.base.net.request.RequesterFactory;
import com.zxz.www.base.utils.KeyBoardUtil;
import com.zxz.www.base.utils.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.zengzhaoxing.browser.Constants.BAI_DU_SEARCH;
import static com.zengzhaoxing.browser.Constants.DEFAULT_WEB;
import static com.zengzhaoxing.browser.Constants.HTTP;
import static com.zengzhaoxing.browser.Constants.HTTPS;
import static com.zengzhaoxing.browser.Constants.SEARCH_EXTRA;

public class SearchFragment extends BaseFragment {

    @BindView(R.id.search_et)
    EditText searchEt;
    @BindView(R.id.search_tv)
    TextView searchTv;
    @BindView(R.id.list_view)
    ListView listView;
    Unbinder unbinder;

    private String mUrl;

    private BDSuggestionAdapter mBDSuggestionAdapter;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fra_search, container, false);
        unbinder = ButterKnife.bind(this, view);
        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (listView.isShown()) {
                    String url = searchEt.getText().toString();
                    if (isHttpUrl(url)) {
                        searchTv.setText(R.string.enter);
                        mUrl = url;
                    } else if (isUrlNotHttp(url)) {
                        searchTv.setText(R.string.enter);
                        mUrl = HTTP + url;
                    } else {
                        searchTv.setText(R.string.search);
                        mUrl = SEARCH_EXTRA + url;
                        final JsonRequester requester = RequesterFactory.getDefaultRequesterFactory()
                                .createGetRequester(BAI_DU_SEARCH + searchEt.getText().toString(),null, BaiduSuggestion.class);
                        requester.setIParser(new BaiduParser());
                        requester.setListener(new JsonRequester.OnResponseListener<BaiduSuggestion>() {
                            @Override
                            public void onResponse(BaiduSuggestion response, int resCode) {
                                if (response != null) {
                                    mBDSuggestionAdapter.setStrings(response.getS());
                                }
                            }
                        });
                        requester.startRequest();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mBDSuggestionAdapter = new BDSuggestionAdapter();
        mUrl = getArguments().getString(DEFAULT_WEB);
        searchEt.setText(mUrl);
        searchEt.requestFocus();
        KeyBoardUtil.openKeyboard(searchEt);
        listView.setAdapter(mBDSuggestionAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                search(SEARCH_EXTRA + mBDSuggestionAdapter.getItem(position));
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.search_tv)
    public void onViewClicked() {
        search(mUrl);
    }

    private void search(String url) {
        mUrl = url;
        KeyBoardUtil.closeKeyboard(searchEt);
        goBack();
    }


    @Override
    protected boolean handleBackEvent() {
        mUrl = null;
        return super.handleBackEvent();
    }

    @Override
    protected Bundle onExit() {
        if (mUrl != null) {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.DEFAULT_WEB,mUrl);
            return bundle;
        }
        return null;
    }

    private boolean isHttpUrl(String url) {
        return url.startsWith(HTTPS) || url.startsWith(HTTP);
    }

    private boolean isUrlNotHttp(String url) {
        return !StringUtil.isBlank(url) && url.startsWith("www.") && url.length() > 5;
    }

}
