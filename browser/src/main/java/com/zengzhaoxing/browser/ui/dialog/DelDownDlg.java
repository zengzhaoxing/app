package com.zengzhaoxing.browser.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zengzhaoxing.browser.R;
import com.zengzhaoxing.browser.presenter.DownPresenter;

public class DelDownDlg extends Dialog {

    public DelDownDlg(@NonNull Context context) {
        super(context);
    }

    public DelDownDlg(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected DelDownDlg(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setDimAmount(0.2f);
        setContentView(R.layout.dlg_del_down);
        final ImageView imageView = findViewById(R.id.check_iv);
        findViewById(R.id.yes_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownPresenter.getInstance(null).delete(imageView.isSelected(),mPos);
                dismiss();
            }
        });
        findViewById(R.id.no_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        findViewById(R.id.check_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setSelected(!imageView.isSelected());
                imageView.setImageResource(imageView.isSelected() ? R.drawable.check : R.drawable.check_off);
            }
        });
        ((TextView)findViewById(R.id.title_tv)).setText(mPos != null && mPos.length > 0 ? R.string.q_delete_down : R.string.q_delete_all_down);
    }

    private int[] mPos;

    public void show(int... pos) {
        mPos = pos;
        show();
    }

}
