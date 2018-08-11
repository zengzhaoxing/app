package com.example.admin.fastpay.model.response;

import com.zxz.www.base.model.BaseModel;

import java.util.List;

/**
 * Created by Administrator on 2017/7/24.
 */

public class CashierResp extends BaseModel{


    private String success;

    private List<Cashier> data = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<Cashier> getData() {
        return data;
    }

    public void setData(List<Cashier> data) {
        this.data = data;
    }


    public static class Cashier extends BaseModel {


        private String name;

        private Integer id;

        private String amount;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

    }
}
