package com.example.admin.fastpay.model.response;

import com.zxz.www.base.model.BaseModel;

/**
 * Created by 曾宪梓 on 2018/1/1.
 */

public class BindCardResp extends BaseModel {

    private boolean success;

    public String getMessage() {
        return message  == null ? "绑定失败" : message;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getStatus() {
        return status;
    }

    private String message;

    private int status;
}
