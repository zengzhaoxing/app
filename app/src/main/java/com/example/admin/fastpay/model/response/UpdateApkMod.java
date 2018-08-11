package com.example.admin.fastpay.model.response;

import com.zxz.www.base.model.BaseModel;

/**
 * Created by 曾宪梓 on 2017/12/31.
 */

public class UpdateApkMod extends BaseModel {


    /**
     * data : {"version":1.2,"UpdateUrl":"https://www.miaodaochina.cn/app/mdsk.apk"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * version : 1.2
         * UpdateUrl : https://www.miaodaochina.cn/app/mdsk.apk
         */

        private double version;
        private String UpdateUrl;

        public double getVersion() {
            return version;
        }

        public void setVersion(double version) {
            this.version = version;
        }

        public String getUpdateUrl() {
            return UpdateUrl;
        }

        public void setUpdateUrl(String UpdateUrl) {
            this.UpdateUrl = UpdateUrl;
        }
    }
}
