package com.example.admin.fastpay.model.request;

import com.example.admin.fastpay.presenter.UserInfoManager;
import com.zxz.www.base.model.BaseModel;

/**
 * Created by 曾宪梓 on 2018/1/9.
 */

public class AddDeviceReq extends BaseModel {

    private String token = UserInfoManager.getToken();

    public void setSign(String sign) {
        this.sign = sign;
    }

    public void setMachine_code(String machine_code) {
        this.machine_code = machine_code;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private String sign;

    private String machine_code;

    private String store_name;

    private String store_id;

    private String phone;

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public void setId(int id) {
        this.id = id;
    }

    private int id;

}
