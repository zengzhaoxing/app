package com.example.admin.fastpay.model.response;

import com.zxz.www.base.model.BaseModel;

/**
 * Created by 曾宪梓 on 2018/1/14.
 */

public class CodePayResp extends BaseModel {

    /**
     * retCode : RC0008
     * retRemark : 交易记录不存在
     * signData : m6MPGI4yTWFpZ60nlFaIve0z92gc1Q75Vmwe5JOz7fegYM9wAM5sJyoRecDgcCNwI5HcgqfqZsb1D3tIdO+6aOP76NHjxQaJN8SiVdyCcsuJaPSHNhk6LK5aQOqCGn1cOtrjMA3TwP/q/5lcAlqj+xxoL78lhlDhPJMtx8DS0Us=
     * transStatus : 2
     * transactionId : ZF20180114202732
     */

    private String retCode;
    private String retRemark;
    private String signData;
    private String transStatus;
    private String transactionId;
    private String code;
    private String message;

    public boolean isSuccess() {
        return "200".equals(code) || "200".equals(retCode) || code == null && retCode == null;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetRemark() {
        return retRemark == null ? message : retRemark;
    }

    public void setRetRemark(String retRemark) {
        this.retRemark = retRemark;
    }

    public String getSignData() {
        return signData;
    }

    public void setSignData(String signData) {
        this.signData = signData;
    }

    public String getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(String transStatus) {
        this.transStatus = transStatus;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
