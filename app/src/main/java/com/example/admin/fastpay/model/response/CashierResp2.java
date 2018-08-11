package com.example.admin.fastpay.model.response;

import com.zxz.www.base.model.BaseModel;

import java.io.Serializable;

/**
 * Created by sun on 2017/5/22.
 */

public class CashierResp2 extends BaseModel implements Serializable {


    /**
     * data : {"id":1,"imei":"18071adc033cd7bdec1","merchant_type":0,"name":"大商户","phone":"18851186776","pid":0,"type":"merchant"}
     * status : success
     */

    private DataBean data;
    private String status;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class DataBean implements Serializable{
        /**
         * id : 1
         * imei : 18071adc033cd7bdec1
         * merchant_type : 0
         * name : 大商户
         * phone : 18851186776
         * pid : 0
         * type : merchant
         */

        private int id;
        private String imei;
        private int merchant_type;
        private String name;
        private String phone;
        private int pid;
        private String type;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImei() {
            return imei;
        }

        public void setImei(String imei) {
            this.imei = imei;
        }

        public int getMerchant_type() {
            return merchant_type;
        }

        public void setMerchant_type(int merchant_type) {
            this.merchant_type = merchant_type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
