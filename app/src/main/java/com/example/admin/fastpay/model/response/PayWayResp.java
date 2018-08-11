package com.example.admin.fastpay.model.response;

import com.zxz.www.base.model.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 曾宪梓 on 2018/1/16.
 */

public class PayWayResp extends BaseModel {

    /**
     * code : 200
     * data : [{"accWay":"1.8w-2w","created":"","extraFee":"3元","payWay":"封顶","payWayId":1,"rate":"35.00元/笔","status":"N","updated":""},{"accWay":"110-20000","created":"","extraFee":"3元","payWay":"航旅","payWayId":2,"rate":"0.39%","status":"Y","updated":""}]
     */

    private String code;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * accWay : 1.8w-2w
         * created :
         * extraFee : 3元
         * payWay : 封顶
         * payWayId : 1
         * rate : 35.00元/笔
         * status : N
         * updated :
         * shareFee
         */

        private String accWay;
        private String created;
        private String extraFee;
        private String payWay;
        private int payWayId;
        private String rate;
        private String status;
        private String updated;
        private float shareFee;


        public String getExchangeHour() {
            return exchangeHour;
        }

        private String exchangeHour;

        public String getRemark() {
            return remark;
        }

        public boolean isInRate(float money) {
            try {
                String[] strings = accWay.split("-");
                int start = Integer.parseInt(strings[0]);
                int end = Integer.parseInt(strings[1]);
                return money >= start && money <= end;
            } catch (Exception e) {
                return false;
            }
           
        }

        private String remark;

        public boolean isOpen() {
            return "Y".equals(status);
        }

        public String getAccWay() {
            return accWay;
        }

        public void setAccWay(String accWay) {
            this.accWay = accWay;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getExtraFee() {
            return extraFee;
        }

        public float getExtraFeeFloat() {
            if (extraFee == null) {
                return 0.0f;
            } else if (!extraFee.contains("元")) {
                return Float.parseFloat(extraFee);
            }
            return Float.parseFloat(extraFee.replace("元",""));
        }

        public float getShareFeeFloat() {
            return shareFee;
        }

        public void setExtraFee(String extraFee) {
            this.extraFee = extraFee;
        }

        public String getPayWay() {
            return payWay;
        }

        public void setPayWay(String payWay) {
            this.payWay = payWay;
        }

        public int getPayWayId() {
            return payWayId;
        }

        public void setPayWayId(int payWayId) {
            this.payWayId = payWayId;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }
    }
}
