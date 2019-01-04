package com.zxz.www.base.net.request.parser;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;

public class DefaultListModelParser<T> extends StringParser<ArrayList<T>> {

    @Override
    public ArrayList<T> parser(String s) {
        ArrayList<T> lcs = new ArrayList<>();
        Class mResponseClass = geModelClass();
        JsonParser parser = new JsonParser();
        JsonArray Jarray = parser.parse(s).getAsJsonArray();
        for (JsonElement obj : Jarray) {
            T cse = (T) mGson.fromJson(obj, mResponseClass);
            lcs.add(cse);
        }
        return lcs;
    }
}
