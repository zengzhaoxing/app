package com.example.admin.fastpay.fragment;

import com.example.admin.fastpay.presenter.UserInfoManager;
import com.zxz.www.base.model.BaseModel;

/**
 * Created by 曾宪梓 on 2018/1/11.
 */

public class PayQRCodeReq extends BaseModel {


    private String token = UserInfoManager.getToken();

    private String store_id;

    public PayQRCodeReq(String store_id) {
        this.store_id = store_id;
    }
}
