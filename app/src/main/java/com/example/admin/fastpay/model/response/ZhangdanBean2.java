package com.example.admin.fastpay.model.response;

import com.zxz.www.base.model.BaseModel;

/**
 * Created by admin on 2017/7/14.
 */

public class ZhangdanBean2 extends BaseModel{

    /**
     * tamount : 213.92
     * damount : 0.09
     * mamount : 7.60
     */

    private String tamount;
    private String damount;
    private String mamount;

    public String getTamount() {
        return tamount;
    }

    public void setTamount(String tamount) {
        this.tamount = tamount;
    }

    public String getDamount() {
        return damount;
    }

    public void setDamount(String damount) {
        this.damount = damount;
    }

    public String getMamount() {
        return mamount;
    }

    public void setMamount(String mamount) {
        this.mamount = mamount;
    }
}
