package com.example.admin.fastpay.model.response;

import com.zxz.www.base.model.BaseModel;

/**
 * Created by 曾宪梓 on 2018/2/10.
 */

public class CaptchaResp extends BaseModel {

    public String getPic() {
        return pic;
    }

    public String getRand() {
        return rand;
    }

    private String pic;

    private String rand;
}
