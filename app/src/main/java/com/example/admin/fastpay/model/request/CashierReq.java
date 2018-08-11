package com.example.admin.fastpay.model.request;

import com.example.admin.fastpay.presenter.UserInfoManager;
import com.zxz.www.base.model.BaseModel;

/**
 * Created by 曾宪梓 on 2018/1/9.
 */

public class CashierReq extends BaseModel {

    private String token = UserInfoManager.getToken();

    private String store_id;

    public CashierReq(String store_id) {
        this.store_id = store_id;
    }

}
