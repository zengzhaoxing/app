package com.example.admin.fastpay.model.response;

import com.zxz.www.base.model.BaseModel;

/**
 * Created by 曾宪梓 on 2018/4/11.
 */

public class RemarkResp extends BaseModel {


    /**
     * code : 200
     * remark : 秒到收款成立2017年3月15
     */

    private String code;
    private String remark;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isSuccess() {
        return "200".equals(code);
    }

}
