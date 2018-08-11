package com.example.admin.fastpay.model.response;

import com.zxz.www.base.model.BaseModel;

/**
 * Created by 曾宪梓 on 2018/1/9.
 */

public class Resp extends BaseModel {

    public int getStatus() {
        return status;
    }

    public boolean isSuccess() {
        return status != 0 || "支付成功".equals(msg);
    }

    public String getMsg() {
        return msg;
    }

    private int status;

    private String msg;

}
