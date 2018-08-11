package com.example.admin.fastpay.model.response;

import com.zxz.www.base.model.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sun on 2017/5/14.
 */

public class BillResp extends BaseModel implements Serializable {

    public String getStatus_code() {
        return status_code;
    }

    private String status_code;

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * out_trade_no : u2017042119425733794   交易单号
         * total_amount : 1.00  交易金额
         * status : null  交易是否成功的状态
         * pay_status : null       交易状态
         * merchant_id : 1
         * type : 401   支付类型
         * created_at : {"date":"2017-04-21 19:42:57.000000","timezone_type":3,"timezone":"Asia/Shanghai"}
         */

        private String out_trade_no;
        private String total_amount;
        private String status;
        private int pay_status;
        private int merchant_id;
        private int type;
        private CreatedAtBean created_at;

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public String getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(String total_amount) {
            this.total_amount = total_amount;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getPay_status() {
            return pay_status;
        }

        public void setPay_status(int pay_status) {
            this.pay_status = pay_status;
        }

        public int getMerchant_id() {
            return merchant_id;
        }

        public void setMerchant_id(int merchant_id) {
            this.merchant_id = merchant_id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public CreatedAtBean getCreated_at() {
            return created_at;
        }

        public void setCreated_at(CreatedAtBean created_at) {
            this.created_at = created_at;
        }

        public static class CreatedAtBean implements Serializable {
            /**
             * date : 2017-04-21 19:42:57.000000   时间
             * timezone_type : 3
             * timezone : Asia/Shanghai   交易时的地方
             */

            private String date;
            private int timezone_type;
            private String timezone;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public int getTimezone_type() {
                return timezone_type;
            }

            public void setTimezone_type(int timezone_type) {
                this.timezone_type = timezone_type;
            }

            public String getTimezone() {
                return timezone;
            }

            public void setTimezone(String timezone) {
                this.timezone = timezone;
            }
        }
    }
}
