package com.zengzhaoxing.browser.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zengzhaoxing.browser.Constants;
import com.zengzhaoxing.browser.R;
import com.zengzhaoxing.browser.bean.BaiduSuggestion;
import com.zengzhaoxing.browser.net.BaiduParser;
import com.zengzhaoxing.browser.ui.adapter.BDSuggestionAdapter;
import com.zxz.www.base.app.BaseFragment;
import com.zxz.www.base.net.request.JsonRequester;
import com.zxz.www.base.net.request.RequesterFactory;
import com.zxz.www.base.utils.JsonUtil;
import com.zxz.www.base.utils.KeyBoardUtil;
import com.zxz.www.base.utils.SPUtil;
import com.zxz.www.base.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.clear_ll)
    LinearLayout clearLl;
    @BindView(R.id.history_ll)
    RelativeLayout historyLl;

    private String mUrl;

    private String mAlternativeUrl;

    private BDSuggestionAdapter mBDSuggestionAdapter;

    private List<String> mHistories;

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
                    } else if (isUrlNotHttp(url)) {
                        searchTv.setText(R.string.enter);
                    } else {
                        searchTv.setText(R.string.search);
                        final JsonRequester requester = RequesterFactory.getDefaultRequesterFactory()
                                .createGetRequester(BAI_DU_SEARCH + searchEt.getText().toString(), null, BaiduSuggestion.class);
                        requester.setIParser(new BaiduParser());
                        requester.setListener(new JsonRequester.OnResponseListener<BaiduSuggestion>() {
                            @Override
                            public void onResponse(BaiduSuggestion response, int resCode) {
                                if (historyLl == null) {
                                    return;
                                }
                                historyLl.setVisibility(View.GONE);
                                if (response != null && response.getS() != null && response.getS().size() > 0) {
                                    mBDSuggestionAdapter.setStrings(response.getS());
                                } else if (mHistories != null && !mHistories.isEmpty()) {
                                    mBDSuggestionAdapter.setStrings(mHistories);
                                    historyLl.setVisibility(View.VISIBLE);
                                } else {
                                    mBDSuggestionAdapter.setStrings(null);
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
        mHistories = getHistories();
        historyLl.setVisibility(mHistories.isEmpty() ? View.GONE : View.VISIBLE);
        mBDSuggestionAdapter.setStrings(mHistories);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = (String) mBDSuggestionAdapter.getItem(position);
                search(text);
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.search_tv,R.id.clear_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search_tv:
                search(searchEt.getText().toString());
                break;
            case R.id.clear_ll:
                SPUtil.remove(SEARCH_HISTORY);
                historyLl.setVisibility(View.GONE);
                mBDSuggestionAdapter.setStrings(null);
                break;
        }
    }

    private void search(String text) {
        String history;
        mAlternativeUrl = SEARCH_EXTRA + text;
        if (isHttpUrl(text)) {
            mUrl = text;
            history = text;
        } else if (isUrlNotHttp(text)) {
            mUrl = HTTP + text;
            history = mUrl;
        } else {
            mUrl = SEARCH_EXTRA + text;
            history = text;
        }
        if (mHistories.contains(history)) {
            mHistories.remove(history);
        }
        mHistories.add(0,history);
        SPUtil.put(SEARCH_HISTORY,JsonUtil.listToJson(mHistories));
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
            bundle.putStringArray(Constants.DEFAULT_WEB, new String[]{mUrl,mAlternativeUrl});
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

    private List<String> getHistories() {
        String s = (String) SPUtil.get(SEARCH_HISTORY,"");
        if (s.isEmpty()) {
            return new ArrayList<>();
        } else {
            return JsonUtil.jsonToList(s);
        }
    }


    private static final String SEARCH_HISTORY = "SEARCH_HISTORY";

}
