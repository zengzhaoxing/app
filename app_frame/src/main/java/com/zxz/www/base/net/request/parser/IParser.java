package com.zxz.www.base.net.request.parser;

public interface IParser<T> {

    T parser(byte[] bytes) throws Exception;

}
