package com.example.admin.fastpay.model;

import com.example.admin.fastpay.presenter.UserInfoManager;
import com.zxz.www.base.model.BaseModel;

/**
 * Created by 曾宪梓 on 2018/1/11.
 */

public class ConfigModel extends BaseModel {

    public void setPush_id(String push_id) {
        this.push_id = push_id;
    }

    public void setPush_key(String push_key) {
        this.push_key = push_key;
    }

    public void setPush_user_name(String push_user_name) {
        this.push_user_name = push_user_name;
    }

    private String  token = UserInfoManager.getToken();

    public String getPush_id() {
        return push_id;
    }

    public String getPush_key() {
        return push_key;
    }

    public String getPush_user_name() {
        return push_user_name;
    }

    private String  push_id;
    private String  push_key;
    private String  push_user_name;
}
