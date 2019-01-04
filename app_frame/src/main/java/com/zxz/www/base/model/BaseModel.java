package com.zxz.www.base.model;

import com.google.gson.Gson;

import java.io.Serializable;

public abstract class BaseModel implements Serializable{

    public String toJson() {
        return new Gson().toJson(this);
    }

    public String toGetJson() {
        return new Gson().toJson(this).replace("\"","").replace("}","").replace("{","").replace(",","&").replace(":","=");
    }


    @Override
    public String toString() {
        return toJson();
    }
}
