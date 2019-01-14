package com.zengzhaoxing.browser;

import com.zxz.www.base.utils.StringUtil;

public class Constants {

    public static final String DEFAULT_WEB = "https://www.sogou.com/";

    public static final String SEARCH_EXTRA = "https://www.baidu.com/s?ie=UTF-8&wd=";

    public static final String HTTP = "http://";

    public static final String HTTPS = "https://";

    public static final String BAI_DU_SEARCH = "http://suggestion.baidu.com/su?wd=";

    public static final String BLANK = "about:blank";

    public static String getKeyWord(String url) {
        if (url != null && url.startsWith(SEARCH_EXTRA)) {
            return url.replace(SEARCH_EXTRA, "");
        } else if (StringUtil.isEqual(url, DEFAULT_WEB)) {
            return null;
        }
        return url;
    }

}
