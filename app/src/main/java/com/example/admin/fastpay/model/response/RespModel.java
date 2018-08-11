package com.example.admin.fastpay.model.response;

import com.zxz.www.base.model.BaseModel;

/**
 * Created by 曾宪梓 on 2017/12/31.
 */

public class RespModel extends BaseModel {

    public String success;

    public String message;

    public boolean isSuccess() {
        return "true".equals(success) || "1".equals(success);
    }

}
