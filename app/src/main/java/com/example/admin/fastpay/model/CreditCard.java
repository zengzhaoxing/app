package com.example.admin.fastpay.model;

import com.zxz.www.base.model.BaseModel;

import java.io.Serializable;

/**
 * Created by 曾宪梓 on 2018/1/3.
 */

public class CreditCard extends BaseModel implements Serializable{


    /**
     * id : 83
     * bank_name : 光大银行
     * type : null
     * bank_card : 525456987566
     * expiry_date : 370000
     * cnn : 253
     * mobile : 13610107352
     * status : 0
     * bank_id : 2147483647
     * created_at : 2018-01-03 00:21:18
     * updated_at : 2018-01-03 00:21:18
     * desc :
     * id_card : 441522199309125135
     * name : 曾宪梓
     * shop_id : null
     * merchant_id : 381
     */

    private int id;
    private String bank_name;
    private Object type;
    private String bank_card;
    private String expiry_date;
    private String cnn;
    private String mobile;
    private int status;
    private String bank_id;
    private String created_at;
    private String updated_at;
    private String desc;
    private String id_card;
    private String name;
    private Object shop_id;
    private int merchant_id;

    public void setBank_pic(String bank_pic) {
        this.bank_pic = bank_pic;
    }

    private String bank_pic;

    public void setCode(String code) {
        this.code = code;
    }

    private String code;

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

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }

    public String getBank_card() {
        return bank_card;
    }

    public void setBank_card(String bank_card) {
        this.bank_card = bank_card;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public String getExpiry() {
        String s;
        if (expiry_date != null && expiry_date.length() == 4) {
            s = expiry_date.substring(2, 4) + expiry_date.substring(0, 2);
            return s;
        }
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public String getCnn() {
        return cnn;
    }

    public void setCnn(String cnn) {
        this.cnn = cnn;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBank_id() {
        return bank_id;
    }

    public void setBank_id(String bank_id) {
        this.bank_id = bank_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getShop_id() {
        return shop_id;
    }

    public void setShop_id(Object shop_id) {
        this.shop_id = shop_id;
    }

    public int getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(int merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getLastNo(int index) {
        if (bank_card.length() <= index) {
            return bank_card;
        } else {
            return bank_card.substring(bank_card.length() - 4, bank_card.length());
        }
    }
}
