package com.example.admin.fastpay.model.request;

import com.zxz.www.base.model.BaseModel;

/**
 * Created by 曾宪梓 on 2018/1/14.
 */

public class CodePayReq extends BaseModel {

    public CodePayReq(String verificationCode, String transactionId) {
        this.verificationCode = verificationCode;
        this.transactionId = transactionId;
    }

    private String verificationCode;
    private String transactionId;

}
