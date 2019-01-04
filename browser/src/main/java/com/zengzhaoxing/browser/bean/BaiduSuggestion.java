package com.zengzhaoxing.browser.bean;

import com.zxz.www.base.model.BaseModel;

import java.util.List;

public class BaiduSuggestion extends BaseModel {


    /**
     * q : qq
     * p : false
     * s : ["qq邮箱","qq音乐","qq邮箱登陆登录","qq企业邮箱","qq飞车手游","qq空间关闭","qq游戏"]
     */

    private String q;
    private boolean p;
    private List<String> s;

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public boolean isP() {
        return p;
    }

    public void setP(boolean p) {
        this.p = p;
    }

    public List<String> getS() {
        return s;
    }

    public void setS(List<String> s) {
        this.s = s;
    }
}
