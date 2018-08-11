package com.example.admin.fastpay.view;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.admin.fastpay.R;
import com.example.admin.fastpay.utils.Util;


/**
 * 自定义搜索界面
 */
public class EditSearchView extends FrameLayout implements Filter.FilterListener {
    private boolean mIsSearchOpen;
    private boolean mClearingFocus;
    private View mSearchLayout;
    private View view;
    private ListView mSuggestionsListView;
    private EditText mSearchSrcTextView;
    private ImageButton mEmptyBtn;
    private RelativeLayout mSearchTopBar;
    private CharSequence mOldQueryText;
    private CharSequence mUserQuery;
    private EditSearchView.OnQueryTextListener mOnQueryChangeListener;
    private EditSearchView.SearchViewListener mSearchViewListener;
    private BaseAdapter mAdapter;
    private EditSearchView.SavedState mSavedState;
    private Context mContext;
    private boolean shouldAnimate;
    private final OnClickListener mOnClickListener;
//    private SearchBillFragment searchActivity;

    public EditSearchView(Context context) {
        this(context, (AttributeSet) null);
    }

    public EditSearchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);
//        searchActivity = new SearchBillFragment();
        this.mIsSearchOpen = false;
        this.mOnClickListener = new OnClickListener() {
            public void onClick(View v) {
                if (v == mEmptyBtn) {
//                    searchActivity.deleteAll();
                    EditSearchView.this.mSearchSrcTextView.setText(null);
                } else if (v == mSearchSrcTextView) {
//                    searchActivity.deleteAll();
                    EditSearchView.this.showSuggestions();
                }
            }
        };
        this.mContext = context;
        this.shouldAnimate = true;
        this.initiateView();
    }

    private void initiateView() {
        LayoutInflater.from(this.mContext).inflate(R.layout.search_view_layout, this, true);
        this.mSearchLayout = this.findViewById(R.id.search_layout);
        this.mSearchTopBar = (RelativeLayout) this.mSearchLayout.findViewById(R.id.search_top_bar);
        this.mSuggestionsListView = (ListView) this.mSearchLayout.findViewById(R.id.suggestion_list);
        this.mSearchSrcTextView = (EditText) this.mSearchLayout.findViewById(R.id.searchTextView);
        this.mEmptyBtn = (ImageButton) this.mSearchLayout.findViewById(R.id.action_empty_btn);
        this.mSearchSrcTextView.setOnClickListener(this.mOnClickListener);
        this.mEmptyBtn.setOnClickListener(this.mOnClickListener);
        this.initSearchView();
        this.mSuggestionsListView.setVisibility(GONE);
    }

    private void initSearchView() {

        this.mSearchSrcTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    hideKeyboard(v);
                }
                return true;
            }
        });
        this.mSearchSrcTextView.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                EditSearchView.this.mUserQuery = s;
                EditSearchView.this.startFilter(s);
                EditSearchView.this.onTextChanged(s);
            }

            public void afterTextChanged(Editable s) {
            }
        });
        this.mSearchSrcTextView.setOnFocusChangeListener(new OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    EditSearchView.this.showKeyboard(EditSearchView.this.mSearchSrcTextView);
                    EditSearchView.this.showSuggestions();
                }

            }
        });

    }

    private void startFilter(CharSequence s) {
        if (this.mAdapter != null && this.mAdapter instanceof Filterable) {
            ((Filterable) this.mAdapter).getFilter().filter(s, this);
        }
    }

    private void onTextChanged(CharSequence newText) {
        Editable text = this.mSearchSrcTextView.getText();
        this.mUserQuery = text;
        boolean hasText = !TextUtils.isEmpty(text);
        if (hasText) {
            this.mEmptyBtn.setVisibility(VISIBLE);
        } else {
            this.mEmptyBtn.setVisibility(GONE);
        }

        if (this.mOnQueryChangeListener != null && !TextUtils.equals(newText, this.mOldQueryText)) {
            this.mOnQueryChangeListener.onQueryTextChange(newText.toString());
        }

        this.mOldQueryText = newText.toString();
    }

    private void onSubmitQuery() {
        Editable query = this.mSearchSrcTextView.getText();
        if (query != null && TextUtils.getTrimmedLength(query) > 0 &&
                (this.mOnQueryChangeListener == null ||
                        !this.mOnQueryChangeListener.onQueryTextSubmit(query.toString()))) {
            this.mSearchSrcTextView.setText(null);

        }
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void showKeyboard(View view) {
        if (Build.VERSION.SDK_INT <= 10 && view.hasFocus()) {
            view.clearFocus();
        }
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, 0);
    }


    public void setBackgroundColor(int color) {
        this.mSearchTopBar.setBackgroundColor(color);
    }


    public void showSuggestions() {
        if (this.mAdapter != null && this.mAdapter.getCount() > 0
                && this.mSuggestionsListView.getVisibility() == GONE) {
            this.mSuggestionsListView.setVisibility(VISIBLE);
        }
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        this.mSuggestionsListView.setOnItemClickListener(listener);
    }

    public void setAdapter(BaseAdapter adapter) {
        this.mAdapter = adapter;
        this.mSuggestionsListView.setAdapter(adapter);
        this.startFilter(this.mSearchSrcTextView.getText());
    }

    public void dismissSuggestions() {
        if (this.mSuggestionsListView.getVisibility() == VISIBLE) {
            this.mSuggestionsListView.setVisibility(GONE);
        }
    }

    public void setQuery(CharSequence query, boolean submit) {
        this.mSearchSrcTextView.setText(query);
        if (query != null) {
            this.mSearchSrcTextView.setSelection(this.mSearchSrcTextView.length());
            this.mUserQuery = query;
        }

        if (submit && !TextUtils.isEmpty(query)) {
            this.onSubmitQuery();
        }

    }

    public void setMenuItem(View view) {
        this.view = view;
        EditSearchView.this.showSearch();
    }

    public boolean isSearchOpen() {
        return this.mIsSearchOpen;
    }

    public void showSearch() {
        if (this.shouldAnimate) {
            this.showSearch(true);
        } else {
            this.showSearch(false);
        }

    }

    public void showSearch(boolean animate) {
        if (!this.isSearchOpen()) {
            this.mSearchSrcTextView.setText(null);
            this.mSearchSrcTextView.requestFocus();
            if (animate) {
                Util.fadeInView(this.mSearchLayout, 150, new Util.AnimationListener() {
                    public boolean onAnimationStart(View view) {
                        return false;
                    }

                    public boolean onAnimationEnd(View view) {
                        if (EditSearchView.this.mSearchViewListener != null) {
                            EditSearchView.this.mSearchViewListener.onSearchViewShown();
                        }

                        return false;
                    }

                    public boolean onAnimationCancel(View view) {
                        return false;
                    }
                });
            } else {
                this.mSearchLayout.setVisibility(VISIBLE);
                if (this.mSearchViewListener != null) {
                    this.mSearchViewListener.onSearchViewShown();
                }
            }

            this.mIsSearchOpen = true;
        }
    }

    public void setOnQueryTextListener(EditSearchView.OnQueryTextListener listener) {
        this.mOnQueryChangeListener = listener;
    }

    public void setOnSearchViewListener(EditSearchView.SearchViewListener listener) {
        this.mSearchViewListener = listener;
    }

    public void onFilterComplete(int count) {
        if (count > 0) {
            this.showSuggestions();
        } else {
            this.dismissSuggestions();
        }
    }

    public boolean requestFocus(int direction, Rect previouslyFocusedRect) {
        return this.mClearingFocus ? false : (!this.isFocusable() ? false : this.mSearchSrcTextView.requestFocus(direction, previouslyFocusedRect));
    }

    public void clearFocus() {
        this.mClearingFocus = true;
        this.hideKeyboard(this);
        super.clearFocus();
        this.mSearchSrcTextView.clearFocus();
        this.mClearingFocus = false;
    }

    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        this.mSavedState = new EditSearchView.SavedState(superState);
        this.mSavedState.query = this.mUserQuery != null ? this.mUserQuery.toString() : null;
        this.mSavedState.isSearchOpen = this.mIsSearchOpen;
        return this.mSavedState;
    }

    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof EditSearchView.SavedState)) {
            super.onRestoreInstanceState(state);
        } else {
            this.mSavedState = (EditSearchView.SavedState) state;
            if (this.mSavedState.isSearchOpen) {
                this.showSearch(false);
                this.setQuery(this.mSavedState.query, false);
            }
            super.onRestoreInstanceState(this.mSavedState.getSuperState());
        }
    }

    public interface SearchViewListener {
        void onSearchViewShown();

        void onSearchViewClosed();
    }

    public interface OnQueryTextListener {
        boolean onQueryTextSubmit(String var1);

        boolean onQueryTextChange(String var1);
    }

    static class SavedState extends BaseSavedState {
        String query;
        boolean isSearchOpen;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.query = in.readString();
            this.isSearchOpen = in.readInt() == 1;
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeString(query);
            out.writeInt(isSearchOpen ? 1 : 0);
        }

        //required field that makes Parcelables from a Parcel
        public static final Creator<SavedState> CREATOR =
                new Creator<SavedState>() {
                    public SavedState createFromParcel(Parcel in) {
                        return new SavedState(in);
                    }

                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };
    }
}
