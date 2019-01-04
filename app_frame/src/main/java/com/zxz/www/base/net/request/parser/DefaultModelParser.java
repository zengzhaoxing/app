package com.zxz.www.base.net.request.parser;

import com.google.gson.Gson;

public class DefaultModelParser<T> extends StringParser<T> {


    @Override
    public T parser(String s) {
        Class mResponseClass = geModelClass();
        return (T) mGson.fromJson(s, mResponseClass);
    }



}
