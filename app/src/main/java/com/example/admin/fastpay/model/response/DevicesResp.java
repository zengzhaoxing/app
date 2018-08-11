package com.example.admin.fastpay.model.response;

import com.example.admin.fastpay.model.Device;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.zxz.www.base.model.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/7/21.
 */

public class DevicesResp extends BaseModel implements Serializable {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("data")
    @Expose
    private List<Device> data = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<Device> getData() {
        return data;
    }

    public void setData(List<Device> data) {
        this.data = data;
    }




//    public static  List<Store> getData(String json){
//        return new GsonUtils().toBean(json,DevicesResp.class).getData();
//    }
//
//    public static  Integer getDataString(String success){
//        return new GsonUtils().toBean(success,DevicesResp.class).getSuccess();
//    }

}
