package com.example.admin.fastpay.model;

import com.zxz.www.base.model.BaseModel;

/**
 * Created by 曾宪梓 on 2018/1/3.
 */

public class ShopInfo extends BaseModel {

    /**
     * id : 103
     * shop_name : shop name 为必填项
     * short_name :
     * province : province 为必填项
     * city : city 为必填项
     * address : null
     * phone : phone 为必填项
     * shop_pic : shop pic 为必填项
     * merchant_id : 381
     * created_at : 2018-01-02 23:33:13
     * updated_at : 2018-01-02 23:33:13
     * status : 0
     * card_no : null
     * tax_id : null
     * fax_id : null
     * transaction_shop_id :
     * email :
     * desc : null
     * rate : 0
     * merchant_info_id : 52
     * store_id : 151490718334782
     * user_id : null
     * fee : null
     * type : null
     */

    private int id;
    private String shop_name;
    private String short_name;
    private String province;
    private String city;

    public void setAddress(String address) {
        this.address = address;
    }

    private String address;
    private String phone;
    private String shop_pic;
    private int merchant_id;
    private String created_at;
    private String updated_at;
    private int status;
    private String card_no;
    private String tax_id;
    private String fax_id;
    private String transaction_shop_id;
    private String email;
    private String desc;
    private int rate;
    private String merchant_info_id;
    private String store_id;
    private String user_id;
    private String fee;

    public void setType(String type) {
        this.type = type;
    }

    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public String getProvince() {
        if (province == null || province.equals("null")) {
            province = "";
        }
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        if (city == null || city.equals("null")) {
            city = "";
        }
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getShop_pic() {
        return shop_pic;
    }

    public void setShop_pic(String shop_pic) {
        this.shop_pic = shop_pic;
    }

    public int getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(int merchant_id) {
        this.merchant_id = merchant_id;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getCard_no() {
        return card_no;
    }

    public Object getTax_id() {
        return tax_id;
    }

    public Object getFax_id() {
        return fax_id;
    }

    public String getTransaction_shop_id() {
        return transaction_shop_id;
    }

    public void setTransaction_shop_id(String transaction_shop_id) {
        this.transaction_shop_id = transaction_shop_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getDesc() {
        return desc;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getMerchant_info_id() {
        return merchant_info_id;
    }

    public void setMerchant_info_id(String merchant_info_id) {
        this.merchant_info_id = merchant_info_id;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getUser_id() {
        return user_id;
    }
    public String getFee() {
        return fee;
    }

    public String getType() {
        return type;
    }
}
