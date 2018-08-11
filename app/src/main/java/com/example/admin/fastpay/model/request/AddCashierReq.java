package com.example.admin.fastpay.model.request;

import com.example.admin.fastpay.presenter.UserInfoManager;
import com.zxz.www.base.model.BaseModel;

/**
 * Created by 曾宪梓 on 2018/1/9.
 */

public class AddCashierReq extends BaseModel {

    private String token = UserInfoManager.getToken();


    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String store_id;
    private String name;
    private String phone;
    private String password;

}
