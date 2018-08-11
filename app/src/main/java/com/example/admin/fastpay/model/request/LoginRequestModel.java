package com.example.admin.fastpay.model.request;

import com.zxz.www.base.app.BaseApp;
import com.zxz.www.base.model.BaseModel;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by 10714 on 2017/12/25.
 */

public class LoginRequestModel extends BaseModel{

    public void setEmail(String email) {
        this.phone = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String phone;
    private String password;
    private String imei = JPushInterface.getRegistrationID(BaseApp.getContext());
}
