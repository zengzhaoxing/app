package com.zxz.www.base.utils;

import android.support.design.widget.BaseTransientBottomBar;
import android.view.View;
import android.widget.Toast;

import com.zxz.www.base.app.BaseApp;


public class ToastUtil {

    public static void toast(String text) {
        Toast.makeText(BaseApp.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    public static void toast(int resId) {
        Toast.makeText(BaseApp.getContext(), ResUtil.getString(resId), Toast.LENGTH_SHORT).show();
    }

    public static void toast(String text, int duration) {
        Toast toast = Toast.makeText(BaseApp.getContext(), text, Toast.LENGTH_SHORT);
        toast.setDuration(duration);
        toast.setText(text);
        toast.show();
    }

    public static void toast(View view, int duration) {
        Toast toast = new Toast(BaseApp.getContext());
        toast.setDuration(duration);
        toast.setView(view);
        toast.show();
    }

    public static void toast(View view) {
        Toast toast = new Toast(BaseApp.getContext());
        toast.setView(view);
        toast.show();
    }

    public static void toast(String text,int gravity,int x,int y) {
        Toast toast = Toast.makeText(BaseApp.getContext(), text, Toast.LENGTH_SHORT);
        toast.setText(text);
        toast.setGravity(gravity,x,y);
        toast.show();
    }

    public static void toast(View view,int gravity,int x,int y) {
        Toast toast = new Toast(BaseApp.getContext());
        toast.setView(view);
        toast.setGravity(gravity,x,y);
        toast.show();
    }

}
