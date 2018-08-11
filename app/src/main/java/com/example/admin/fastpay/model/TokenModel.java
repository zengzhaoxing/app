package com.example.admin.fastpay.model;

import com.example.admin.fastpay.presenter.UserInfoManager;
import com.zxz.www.base.model.BaseModel;

/**
 * Created by 10714 on 2017/12/25.
 */

public class TokenModel extends BaseModel {

    public TokenModel(String token) {
        this.token = token;
    }

    public TokenModel() {
    }

    public String getToken() {
        return token;
    }

    private String token = UserInfoManager.getToken();
}
