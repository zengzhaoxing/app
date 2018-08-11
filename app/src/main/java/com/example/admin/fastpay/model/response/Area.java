package com.example.admin.fastpay.model.response;

import com.example.admin.fastpay.model.Name;
import com.zxz.www.base.model.BaseModel;

/**
 * Created by 曾宪梓 on 2018/1/1.
 */

public class Area extends BaseModel implements Name {
    /**
     * id : 1941
     * areaCode : 440100
     * areaName : 广州市
     * areaParentId : 440000
     * areaType : 3
     * created_at : null
     * updated_at : null
     */

    private int id;
    private String areaCode;
    private String areaName;
    private int areaParentId;
    private int areaType;
    private Object created_at;
    private Object updated_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getAreaParentId() {
        return areaParentId;
    }

    public void setAreaParentId(int areaParentId) {
        this.areaParentId = areaParentId;
    }

    public int getAreaType() {
        return areaType;
    }

    public void setAreaType(int areaType) {
        this.areaType = areaType;
    }

    public Object getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Object created_at) {
        this.created_at = created_at;
    }

    public Object getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Object updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String getName() {
        return areaName;
    }
}
