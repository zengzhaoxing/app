package com.example.admin.fastpay.model;

import com.zxz.www.base.model.BaseModel;

/**
 * Created by admin on 2017/7/17.
 */

public class MenuModel extends BaseModel{
    private String name;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    private String icon_url;

    public MenuModel(String name, String url, String icon_url) {
        this.name = name;
        this.url = url;
        this.icon_url = icon_url;
    }
}
