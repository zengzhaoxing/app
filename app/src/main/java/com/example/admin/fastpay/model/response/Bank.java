package com.example.admin.fastpay.model.response;

import com.example.admin.fastpay.model.Name;
import com.zxz.www.base.model.BaseModel;

/**
 * Created by 曾宪梓 on 2018/1/1.
 */

public class Bank extends BaseModel implements Name{

    /**
     * id : 2
     * bank_name : 哈尔滨银行
     * bank_code : 313261000018
     */

    private int id;
    private String bank_name;
    private String bank_code;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_code() {
        return bank_code;
    }

    public void setBank_code(String bank_code) {
        this.bank_code = bank_code;
    }

    @Override
    public String getName() {
        return bank_name;
    }
}
