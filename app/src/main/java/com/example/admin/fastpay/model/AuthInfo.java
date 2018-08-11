package com.example.admin.fastpay.model;

import com.example.admin.fastpay.model.response.Bank;
import com.example.admin.fastpay.model.response.City;
import com.example.admin.fastpay.model.response.Province;
import com.zxz.www.base.model.BaseModel;
import com.zxz.www.base.utils.StringUtil;

/**
 * Created by 曾宪梓 on 2017/12/31.
 */

public class AuthInfo extends BaseModel {

    /**
     * id : 52
     * mobile :
     * id_card : 441522199309125124
     * bank_card :
     * acc_name : 曾宪梓
     * tradetype :
     * status : false
     * type :
     * merchant_id : 381
     * bank_name :
     * created_at : 2017-12-31 23:28:21
     * updated_at : 2017-12-31 23:28:21
     * id_card_pic : null
     * bank_pic : null
     * bank_code :
     * branch_bank_code :
     * branch_bank_name : null
     * province_code :
     * province_name :
     * city_code :
     * fee : null
     * error_reason :
     * city_name :
     * effective_date : ??
     * licence_organ : 2017.01.01 - 2018.08.06
     * code
     */

    private int id;

    public void setCode(String code) {
        this.code = code;
    }

    private String code;
    private String mobile;
    private String id_card;
    private String bank_card;
    private String acc_name;
    private String tradetype;
    private boolean status;
    private String type;
    private int merchant_id;
    private String bank_name;
    private String created_at;
    private String updated_at;

    public void setId_card_pic(String id_card_pic) {
        this.id_card_pic = id_card_pic;
    }

    private String id_card_pic;
    private String bank_pic;
    private String bank_code;
    private String branch_bank_code;
    private String branch_bank_name;
    private String province_code;
    private String province_name;
    private String city_code;
    private String fee;
    private String error_reason;
    private String city_name;
    private String effective_date;
    private String licence_organ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getBank_card() {
        return bank_card;
    }

    public void setBank_card(String bank_card) {
        this.bank_card = bank_card;
    }

    public String getAcc_name() {
        return acc_name;
    }

    public void setAcc_name(String acc_name) {
        this.acc_name = acc_name;
    }

    public String getTradetype() {
        return tradetype;
    }

    public void setTradetype(String tradetype) {
        this.tradetype = tradetype;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(int merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
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

    public String getId_card_pic() {
        return id_card_pic;
    }

    public void setBank_pic(String bank_pic) {
        this.bank_pic = bank_pic;
    }

    public String getBank_pic() {
        return bank_pic;
    }

    public String getBank_code() {
        return bank_code;
    }

    public void setBank_code(String bank_code) {
        this.bank_code = bank_code;
    }

    public String getBranch_bank_code() {
        return branch_bank_code;
    }

    public void setBranch_bank_code(String branch_bank_code) {
        this.branch_bank_code = branch_bank_code;
    }

    public Object getBranch_bank_name() {
        return branch_bank_name;
    }

    public String getProvince_code() {
        return province_code;
    }

    public void setProvince_code(String province_code) {
        this.province_code = province_code;
    }

    public String getProvince_name() {
        if ("null".equals(province_name)) {
            province_name = "";
        }
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public Object getFee() {
        return fee;
    }

    public String getError_reason() {
        return error_reason;
    }

    public void setError_reason(String error_reason) {
        this.error_reason = error_reason;
    }

    public String getCity_name() {
        if ("null".equals(city_name)) {
            city_name = "";
        }
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getEffective_date() {
        return effective_date;
    }

    public void setEffective_date(String effective_date) {
        this.effective_date = effective_date;
    }

    public String getLicence_organ() {
        return licence_organ;
    }

    public void setLicence_organ(String licence_organ) {
        this.licence_organ = licence_organ;
    }

    public Province getProvince() {
        if (StringUtil.isBlank(province_code) || province_code.equals("null")) {
            return null;
        }
        Province province = new Province();
        province.setAreaName(province_name);
        province.setAreaCode(province_code);
        return province;
    }

    public City getCity() {
        if (StringUtil.isBlank(city_code) || city_code.equals("null")) {
            return null;
        }
        City city = new City();
        city.setAreaCode(city_code);
        city.setAreaName(city_name);
        return city;
    }

    public Bank getBank() {
        if (StringUtil.isBlank(bank_code)) {
            return null;
        }
        Bank bank = new Bank();
        bank.setBank_code(bank_code);
        bank.setBank_name(bank_name);
        return bank;
    }


}
