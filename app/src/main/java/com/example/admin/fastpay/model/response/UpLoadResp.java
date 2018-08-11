package com.example.admin.fastpay.model.response;

import com.zxz.www.base.model.BaseModel;

/**
 * Created by 曾宪梓 on 2018/1/2.
 */

public class UpLoadResp extends BaseModel {

    /**
     * path : /uploads/appicon/20180102201426709.jpg
     * img_url : https://www.miaodaochina.cn/uploads/appicon/20180102201426709.jpg
     * status : 1
     */

    private String path;
    private String img_url;
    private int status;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
