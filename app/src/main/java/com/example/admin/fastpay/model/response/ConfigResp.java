package com.example.admin.fastpay.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.zxz.www.base.model.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/7/22.
 */

public class ConfigResp extends BaseModel implements Serializable{

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("data")
    @Expose
    private List<ConfigDataBean> data = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<ConfigDataBean> getData() {
        return data;
    }

    public void setData(List<ConfigDataBean> data) {
        this.data = data;
    }

    public static class ConfigDataBean implements Serializable{

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("push_id")
        @Expose
        private String pushId;
        @SerializedName("push_key")
        @Expose
        private String pushKey;
        @SerializedName("push_user_name")
        @Expose
        private String pushUserName;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("merchant_id")
        @Expose
        private String merchantId;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getPushId() {
            return pushId;
        }

        public void setPushId(String pushId) {
            this.pushId = pushId;
        }

        public String getPushKey() {
            return pushKey;
        }

        public void setPushKey(String pushKey) {
            this.pushKey = pushKey;
        }

        public String getPushUserName() {
            return pushUserName;
        }

        public void setPushUserName(String pushUserName) {
            this.pushUserName = pushUserName;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }

    }

}
