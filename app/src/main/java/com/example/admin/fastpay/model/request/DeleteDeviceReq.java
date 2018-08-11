package com.example.admin.fastpay.model.request;

import com.example.admin.fastpay.presenter.UserInfoManager;
import com.zxz.www.base.model.BaseModel;

/**
 * Created by 曾宪梓 on 2018/1/9.
 */

public class DeleteDeviceReq extends BaseModel {

    public DeleteDeviceReq(int id) {
        this.id = id;
    }

    private int id;

    private String token = UserInfoManager.getToken();

}
