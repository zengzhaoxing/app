//package com.example.admin.fastpay.temple;
//
//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;
//
//import java.util.List;
//
///**
// * Created by admin on 2017/8/2.
// */
//
//public class BankCradBean {
//    @SerializedName("success")
//    @Expose
//    private Integer success;
//    @SerializedName("data")
//    @Expose
//    private List<Datum> data = null;
//
//    public Integer getSuccess() {
//        return success;
//    }
//
//    public void setSuccess(Integer success) {
//        this.success = success;
//    }
//
//    public List<Datum> getData() {
//        return data;
//    }
//
//    public void setData(List<Datum> data) {
//        this.data = data;
//    }
//
//    public static class Datum {
//
//        @SerializedName("id")
//        @Expose
//        private int id;
//        @SerializedName("name")
//        @Expose
//        private String name;
//        @SerializedName("no")
//        @Expose
//        private String no;
//        @SerializedName("card_no")
//        @Expose
//        private String cardNo;
//        @SerializedName("bank_name")
//        @Expose
//        private String bankName;
//        @SerializedName("phone")
//        @Expose
//        private String phone;
//        @SerializedName("type")
//        @Expose
//        private Object type;
//        @SerializedName("u_id")
//        @Expose
//        private Object uId;
//        @SerializedName("m_id")
//        @Expose
//        private String mId;
//        @SerializedName("card_type")
//        @Expose
//        private Object cardType;
//        @SerializedName("ck_status")
//        @Expose
//        private Integer ckStatus;
//        @SerializedName("ck_msg")
//        @Expose
//        private String ckMsg;
//        @SerializedName("transaction_id")
//        @Expose
//        private String transactionId;
//        @SerializedName("created_at")
//        @Expose
//        private String createdAt;
//        @SerializedName("updated_at")
//        @Expose
//        private String updatedAt;
//
//        public int getId() {
//            return id;
//        }
//
//        public void setId(int id) {
//            this.id = id;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getNo() {
//            return no;
//        }
//
//        public void setNo(String no) {
//            this.no = no;
//        }
//
//        public String getCardNo() {
//            return cardNo;
//        }
//
//        public void setCardNo(String cardNo) {
//            this.cardNo = cardNo;
//        }
//
//        public String getBankName() {
//            return bankName;
//        }
//
//        public void setBankName(String bankName) {
//            this.bankName = bankName;
//        }
//
//        public String getPhone() {
//            return phone;
//        }
//
//        public void setPhone(String phone) {
//            this.phone = phone;
//        }
//
//        public Object getType() {
//            return type;
//        }
//
//        public void setType(Object type) {
//            this.type = type;
//        }
//
//        public Object getUId() {
//            return uId;
//        }
//
//        public void setUId(Object uId) {
//            this.uId = uId;
//        }
//
//        public String getMId() {
//            return mId;
//        }
//
//        public void setMId(String mId) {
//            this.mId = mId;
//        }
//
//        public Object getCardType() {
//            return cardType;
//        }
//
//        public void setCardType(Object cardType) {
//            this.cardType = cardType;
//        }
//
//        public Integer getCkStatus() {
//            return ckStatus;
//        }
//
//        public void setCkStatus(Integer ckStatus) {
//            this.ckStatus = ckStatus;
//        }
//
//        public String getCkMsg() {
//            return ckMsg;
//        }
//
//        public void setCkMsg(String ckMsg) {
//            this.ckMsg = ckMsg;
//        }
//
//        public String getTransactionId() {
//            return transactionId;
//        }
//
//        public void setTransactionId(String transactionId) {
//            this.transactionId = transactionId;
//        }
//
//        public String getCreatedAt() {
//            return createdAt;
//        }
//
//        public void setCreatedAt(String createdAt) {
//            this.createdAt = createdAt;
//        }
//
//        public String getUpdatedAt() {
//            return updatedAt;
//        }
//
//        public void setUpdatedAt(String updatedAt) {
//            this.updatedAt = updatedAt;
//        }
//
//    }
//}
