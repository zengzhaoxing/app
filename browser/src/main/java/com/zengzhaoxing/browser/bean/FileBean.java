package com.zengzhaoxing.browser.bean;

import com.zxz.www.base.model.BaseModel;
import com.zxz.www.base.utils.FileUtil;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class FileBean extends BaseModel {


    @Id(autoincrement = true)
    private Long id;

    private String url;

    private String name;

    private String type;

    private String dir;

    private long time = System.currentTimeMillis();



    @Generated(hash = 826095479)
    public FileBean(Long id, String url, String name, String type, String dir,
            long time) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.type = type;
        this.dir = dir;
        this.time = time;
    }

    @Generated(hash = 1910776192)
    public FileBean() {
    }



    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDir() {
        return this.dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }


}
