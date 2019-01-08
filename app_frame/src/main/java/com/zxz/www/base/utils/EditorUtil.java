package com.zxz.www.base.utils;

import android.content.ClipboardManager;
import android.content.Context;

import com.zxz.www.base.app.BaseApp;

public class EditorUtil {

    public static void copy(String text) {
        ClipboardManager cm = (ClipboardManager) BaseApp.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText(text);
        ToastUtil.toast("复制成功");
    }

}
