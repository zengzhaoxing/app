package com.zxz.www.base.utils;

/**
 * Created by igola on 2017/10/20.
 */

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.zxz.www.base.app.BaseApp;

//打开或关闭软键盘
public class KeyBoardUtil {

    public static void openKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) BaseApp.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public static void closeKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) BaseApp.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

}
