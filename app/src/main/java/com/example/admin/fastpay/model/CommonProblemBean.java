package com.example.admin.fastpay.model;

import com.zxz.www.base.model.BaseModel;

import java.util.List;

/**
 * Created by sun on 2017/5/24.
 */

public class CommonProblemBean extends BaseModel{


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * category_id : 0
         * content : 我也不知，你还是自己摸索吧
         * summary : 如何支付呢？
         * title : app如何支付
         * user_id : 6
         */

        private int category_id;
        private String content;
        private String summary;
        private String title;
        private int user_id;

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }
    }
}
