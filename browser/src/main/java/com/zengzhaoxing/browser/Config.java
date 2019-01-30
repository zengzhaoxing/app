package com.zengzhaoxing.browser;

import com.zengzhaoxing.browser.bean.UrlBean;

import java.util.ArrayList;
import java.util.List;

public class Config {

    public static List<UrlBean> getUrlList() {
        if (URL_LIST == null) {
            URL_LIST = new ArrayList<>();
            URL_LIST.add(new UrlBean("https://www.sogou.com/", "搜狗"));
            URL_LIST.add(new UrlBean("https://www.baidu.com/", "百度"));
            URL_LIST.add(new UrlBean("https://www.taobao.com/", "淘宝"));
            URL_LIST.add(new UrlBean("https://www.sina.com.cn/", "新浪"));
            URL_LIST.add(new UrlBean("https://www.toutiao.com/", "头条"));
            URL_LIST.add(new UrlBean("https://www.tmall.com/", "天猫"));
            URL_LIST.add(new UrlBean("https://www.jd.com/", "京东"));
            URL_LIST.add(new UrlBean("https://www.zhihu.com/", "知乎"));
//            URL_LIST.add(new UrlBean("https://www.ctrip.com/", "携程"));
//            URL_LIST.add(new UrlBean("https://www.igola.com/", "igola"));
//            URL_LIST.add(new UrlBean("https://www.sogou.com/", "搜狗"));
//            URL_LIST.add(new UrlBean("https://www.sogou.com/", "搜狗"));
//            URL_LIST.add(new UrlBean("https://www.sogou.com/", "搜狗"));
//            URL_LIST.add(new UrlBean("https://www.sogou.com/", "搜狗"));
//            URL_LIST.add(new UrlBean("https://www.sogou.com/", "搜狗"));
//            URL_LIST.add(new UrlBean("https://www.sogou.com/", "搜狗"));
//            URL_LIST.add(new UrlBean("https://www.sogou.com/", "搜狗"));
//            URL_LIST.add(new UrlBean("https://www.sogou.com/", "搜狗"));
//            URL_LIST.add(new UrlBean("https://www.sogou.com/", "搜狗"));
//            URL_LIST.add(new UrlBean("https://www.sogou.com/", "搜狗"));
        }
        return URL_LIST;
    }

    private static List<UrlBean> URL_LIST;



}
