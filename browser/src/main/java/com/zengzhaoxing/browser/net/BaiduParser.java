package com.zengzhaoxing.browser.net;

import android.util.Log;

import com.google.gson.Gson;
import com.zengzhaoxing.browser.bean.BaiduSuggestion;
import com.zxz.www.base.net.request.parser.IParser;
import com.zxz.www.base.net.request.parser.StringParser;

import java.nio.charset.Charset;

public class BaiduParser implements IParser<BaiduSuggestion> {

    private Gson mGson = new Gson();

    @Override
    public final BaiduSuggestion parser(byte[] bytes) {
        String s = new String(bytes, Charset.forName("GBK"));
        s = s.substring(17, s.length() - 2);
        return mGson.fromJson(s, BaiduSuggestion.class);
    }


}
