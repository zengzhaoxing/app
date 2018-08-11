package com.example.admin.fastpay.model.request;

import com.example.admin.fastpay.presenter.UserInfoManager;
import com.example.admin.fastpay.utils.UUIDGenerator;
import com.zxz.www.base.model.BaseModel;

/**
 * Created by 曾宪梓 on 2018/1/20.
 */

public class TradePayReq extends BaseModel {

    private String token = UserInfoManager.getToken();

    public TradePayReq(String code, String total_amount) {
        this.code = code;
        this.total_amount = total_amount;
    }

    private String code;

    private String total_amount;

    private String out_trade_no = UUIDGenerator.generate();


}
