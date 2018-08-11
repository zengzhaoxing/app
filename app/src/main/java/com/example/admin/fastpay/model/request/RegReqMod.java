package com.example.admin.fastpay.model.request;

import com.zxz.www.base.model.BaseModel;

/**
 * Created by 10714 on 2017/12/26.
 */

public class RegReqMod extends BaseModel {
    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private String name;

    private String phone;

    private String password;

    private String code;
}

