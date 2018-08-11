package com.example.admin.fastpay.model;

import com.zxz.www.base.model.BaseModel;

import java.util.List;

/**
 * Created by 曾宪梓 on 2018/1/7.
 */

public class StoreInfo extends BaseModel {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * store_id : m20170915095438962402
         * store_name : 广州南站
         */

        private String store_id;
        private String store_name;

        public String getStore_id() {
            return store_id;
        }

        public void setStore_id(String store_id) {
            this.store_id = store_id;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }
    }
}
