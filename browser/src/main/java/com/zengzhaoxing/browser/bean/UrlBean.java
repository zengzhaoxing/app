package com.zengzhaoxing.browser.bean;

import com.zengzhaoxing.browser.Constants;
import com.zxz.www.base.model.BaseModel;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import static com.zengzhaoxing.browser.Constants.SPILT;

@Entity
public class UrlBean extends BaseModel {

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        if (title == null && url != null) {
            return url;
        }
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIconUrl() {
        if (iconUrl == null && url != null) {
            int begin = url.indexOf(SPILT) + SPILT.length();
            int end = url.indexOf('/', begin);
            return url.substring(0, end) + '/' + Constants.WEB_ICON_SUFFIX;
        }
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    private String url;

    private String title;

    private String iconUrl;

    public long getTime() {
        return time;
    }

    private long time = System.currentTimeMillis();

    public boolean isCollect() {
        return isCollect;
    }

    public void setCollect(boolean collect) {
        isCollect = collect;
    }

    public boolean getIsCollect() {
        return this.isCollect;
    }

    public void setIsCollect(boolean isCollect) {
        this.isCollect = isCollect;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private boolean isCollect;

    @Id(autoincrement = true)
    private Long id;

    @Generated(hash = 1115952939)
    public UrlBean(String url, String title, String iconUrl, long time,
            boolean isCollect, Long id) {
        this.url = url;
        this.title = title;
        this.iconUrl = iconUrl;
        this.time = time;
        this.isCollect = isCollect;
        this.id = id;
    }

    @Generated(hash = 241588977)
    public UrlBean() {
    }

    public UrlBean copy() {
        UrlBean urlBean = new UrlBean();
        urlBean.iconUrl = iconUrl;
        urlBean.url = url;
        urlBean.title = title;
        urlBean.time = time;
        urlBean.isCollect = isCollect;
        return urlBean;
    }



    
}
