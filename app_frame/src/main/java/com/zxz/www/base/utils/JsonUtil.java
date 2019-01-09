package com.zxz.www.base.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonUtil {

    /**
     * List<T> 转 json 保存到数据库
     */
    public static <T> String listToJson(List<T> ts) {
        return new Gson().toJson(ts);
    }

    /**
     * json 转 List<T>
     */
    public static <T> List<T> jsonToList(String jsonString) {
        Type listType = new TypeToken<T>(){}.getType();
        return new Gson().<ArrayList<T>>fromJson(jsonString, listType);
    }

}
