package com.example.admin.fastpay.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.zxz.www.base.model.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/8/7.
 */

public class RecordResp extends BaseModel implements Serializable {
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("data")
    @Expose
    private List<Record> data = null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<Record> getData() {
        return data;
    }

    public void setData(List<Record> data) {
        this.data = data;
    }


    public static class Record extends BaseModel implements Serializable {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("total_amount")
        @Expose
        private String totalAmount;
        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("transfer_no")
        @Expose
        private String transferNo;
        @SerializedName("store_id")
        @Expose
        private String storeId;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getTransferNo() {
            return transferNo;
        }

        public void setTransferNo(String transferNo) {
            this.transferNo = transferNo;
        }

        public String getStoreId() {
            return storeId;
        }

        public void setStoreId(String storeId) {
            this.storeId = storeId;
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
    }
}
