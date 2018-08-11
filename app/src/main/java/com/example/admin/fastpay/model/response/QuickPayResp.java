package com.example.admin.fastpay.model.response;

import com.zxz.www.base.model.BaseModel;

/**
 * Created by 曾宪梓 on 2018/1/13.
 */

public class QuickPayResp extends ResponseModel {

    /**
     * code : 201
     * message : 处理失败
     */

    private String code;
    private String message;
    private String status;

    public String getUrl() {
        return url;
    }

    private String url;

    public String getSessionId() {
        return session;
    }

    private String session;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message == null ? status : message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean isSuccess() {
        return "200".equals(code);
    }
}
