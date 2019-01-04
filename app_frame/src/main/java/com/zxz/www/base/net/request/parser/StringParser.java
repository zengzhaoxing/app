package com.zxz.www.base.net.request.parser;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

public abstract class StringParser<T> implements IParser<T> {

    protected Gson mGson = new Gson();

    @Override
    public final T parser(byte[] bytes) throws Exception{
        return parser(new String(bytes, Charset.forName("UTF-8")));
    }

    public abstract T parser(String s) throws Exception;

    public Class<T> geModelClass() {
        Type genType = getClass().getGenericSuperclass();
        Type type = ((ParameterizedType) genType).getActualTypeArguments()[0];
        return (Class<T>) type;
    }


}
