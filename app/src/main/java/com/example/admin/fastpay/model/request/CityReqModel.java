package com.example.admin.fastpay.model.request;

import com.zxz.www.base.model.BaseModel;

/**
 * Created by 曾宪梓 on 2018/1/1.
 */

public class CityReqModel extends BaseModel {

    private String areaCode;

    public CityReqModel(String areaCode) {
        this.areaCode = areaCode;
    }
}
