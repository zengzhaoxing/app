package com.example.admin.fastpay.model.response;

import com.zxz.www.base.model.BaseModel;

import java.util.List;

/**
 * Created by Administrator on 2017/7/24.
 */

public class StoreResp extends BaseModel{

    private String success;

    private List<Store> data = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<Store> getData() {
        return data;
    }

    public void setData(List<Store> data) {
        this.data = data;
    }

    public static class Store extends BaseModel{

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
