package com.zengzhaoxing.browser.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zengzhaoxing.browser.Constants;
import com.zengzhaoxing.browser.R;
import com.zengzhaoxing.browser.presenter.DownPresenter;
import com.zxz.www.base.utils.StringUtil;
import com.zxz.www.base.utils.ToastUtil;

public class AddUrlDlg extends Dialog{

    public AddUrlDlg(@NonNull Context context) {
        super(context);
    }

    public AddUrlDlg(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected AddUrlDlg(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getWindow() != null) {
            getWindow().setDimAmount(0.2f);
        }
        setContentView(R.layout.dlg_add_url);
        final EditText titleEt = findViewById(R.id.title_et);
        final EditText urlEt = findViewById(R.id.url_et);
        findViewById(R.id.yes_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEt.getText().toString();
                String url = urlEt.getText().toString();
                if (!StringUtil.isBlank(title) && !StringUtil.isBlank(url)) {
                    mOnAddListener.OnAdd(Constants.HTTP + url,title);
                    dismiss();
                } else {
                    ToastUtil.toast("输入不能为空");
                }
            }
        });
    }

    public void show(OnAddListener listener) {
        mOnAddListener = listener;
        super.show();
    }

    public interface OnAddListener {
        void OnAdd(String url,String title);
    }

    private OnAddListener mOnAddListener;

}
