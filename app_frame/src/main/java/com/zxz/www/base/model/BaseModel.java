package com.zxz.www.base.model;

import com.google.gson.Gson;
public abstract class BaseModel{

    public String toJson() {
        return new Gson().toJson(this);
    }

    public String toGetJson() {
        return new Gson().toJson(this).replace("\"","").replace("}","").replace("{","").replace(",","&").replace(":","=");
    }



}
