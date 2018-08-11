package com.example.admin.fastpay.model.response;

import com.zxz.www.base.model.BaseModel;

/**
 * Created by sun on 2017/5/27.
 */

public class PayQRCodeResp extends BaseModel{

    public QRCode getData() {
        return data;
    }

    private QRCode data;

    private String msg;

    public static class QRCode extends BaseModel {

        private String code_url;

        public String getCode_url() {
            return code_url;
        }

        public void setCode_url(String code_url) {
            this.code_url = code_url;
        }
    }



    /**
     * code_url : https://openauth.alipaydev.com/oauth2/publicAppAuthorize.htm?app_id=2016072900118621&redirect_uri=https://isv.umxnt.com/callback&scope=auth_base&state=SXD_63_1
     */


}
