package com.example.admin.fastpay.model.request;

import com.example.admin.fastpay.presenter.UserInfoManager;
import com.zxz.www.base.model.BaseModel;

/**
 * Created by 曾宪梓 on 2018/1/11.
 */

public class PageReq extends BaseModel {

    private String token = UserInfoManager.getToken();

    public PageReq() {
    }

    public void setPage(String page) {
        this.page = page;
    }

    public PageReq(String page) {
        this.page = page;
    }

    private String page;

    public PageReq(String page, String today) {
        this.page = page;
        this.today = today;
    }

    private String today;

}
