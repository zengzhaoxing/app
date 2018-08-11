package com.example.admin.fastpay.model.response;

import com.example.admin.fastpay.model.CreditCard;
import com.zxz.www.base.model.BaseModel;

import java.util.List;

/**
 * Created by 曾宪梓 on 2018/1/5.
 */

public class CreditCardList extends BaseModel {


    private List<CreditCard> data;

    public List<CreditCard> getData() {
        return data;
    }
}
