package com.example.admin.fastpay.model.request;

import com.example.admin.fastpay.presenter.UserInfoManager;
import com.zxz.www.base.model.BaseModel;

/**
 * Created by 曾宪梓 on 2018/1/11.
 */

public class ReceiptCodeReq extends BaseModel {

    private String token = UserInfoManager.getToken();

    public ReceiptCodeReq(String store_id, String price) {
        this.store_id = store_id;
        this.price = price;
    }

    private String store_id;

    private String price;

}
