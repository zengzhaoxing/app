package com.zengzhaoxing.browser.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zengzhaoxing.browser.R;

public class NoticeDialog extends Dialog {

    public NoticeDialog(@NonNull Context context) {
        super(context);
    }

    public NoticeDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected NoticeDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setDimAmount(0.2f);
        setContentView(R.layout.dialog_notice);
        findViewById(R.id.yes_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mListener != null) {
                    mListener.onOkClick();
                }
            }
        });
        findViewById(R.id.no_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        ((TextView)findViewById(R.id.title_tv)).setText(msg);
    }

    OnDialogClickListener mListener;

    private int msg;

    public void show(int msgID,OnDialogClickListener listener) {
        mListener = listener;
        msg = msgID;
        super.show();
    }

    public interface OnDialogClickListener {
        void onOkClick();
    }

}
