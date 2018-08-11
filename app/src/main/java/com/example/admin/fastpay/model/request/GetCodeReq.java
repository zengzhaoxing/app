package com.example.admin.fastpay.model.request;

import com.zxz.www.base.model.BaseModel;

/**
 * Created by 10714 on 2017/12/26.
 */

public class GetCodeReq extends BaseModel {

    private String phone;

    private String captcha;

    private String rand;


    public GetCodeReq(String phone, String captcha, String rand) {
        this.phone = phone;
        this.captcha = captcha;
        this.rand = rand;
    }
}
