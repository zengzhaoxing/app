package com.zxz.www.base.utils;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zxz.www.base.app.BaseApp;
import com.zxz.www.base.model.BaseModel;

import java.util.ArrayList;
import java.util.List;

public class SPUtil {

    private static String FILE_NAME = SPUtil.class.getName();

    private static Gson GSON = new Gson();

    public static void put(String key,Object object) {
        if(object == null){
            return;
        }
        Context context = BaseApp.getContext();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else if (object instanceof BaseModel) {
            editor.putString(key, GSON.toJson(object));
        } else {
            editor.putString(key, object.toString());
        }
        editor.apply();
    }

    public static Object get(String key,Object defaultObject) {
        Context context = BaseApp.getContext();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        } else if (defaultObject instanceof Class) {
            String s = sp.getString(key, null);
            if (s != null) {
                return GSON.fromJson(s, (Class) defaultObject);
            } else {
                return null;
            }
        } else {
            return defaultObject;
        }
    }

    public static void remove(String key) {
        Context context = BaseApp.getContext();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.apply();
    }

    public static void putList(String key,List<? extends BaseModel> list) {
        Context context = BaseApp.getContext();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key,GSON.toJson(list));
        editor.apply();
    }

    public static <T> List<T> getList(String key,Class<T> clz) {
        Context context = BaseApp.getContext();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        String s = sp.getString(key, null);
        List<T> list = GSON.fromJson(s, new TypeToken<List<T>>() {}.getType());
        if (list == null || list.size() ==0) {
            return null;
        } else {
            List<T> tList = new ArrayList<>();
            for (Object o : list) {
                tList.add(GSON.fromJson(GSON.toJson(o), clz));
            }
            return tList;
        }
    }

}