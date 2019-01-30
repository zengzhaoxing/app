package com.zengzhaoxing.browser.bean;

import com.zxz.www.base.model.BaseModel;
import com.zxz.www.base.utils.FileUtil;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.io.File;

@Entity
public class FileBean extends BaseModel {


    @Id(autoincrement = true)
    private Long id;

    private String url;

    private String name;

    public float getProgress() {
        if (contentLength == 0) {
            return 0;
        }
        return (float)downLoadLength / (float)contentLength;
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    private long contentLength;


    public long getDownLoadLength() {
        return downLoadLength;
    }

    public void setDownLoadLength(long downLoadLength) {
        this.downLoadLength = downLoadLength;
    }

    public boolean isComplete() {
        return getProgress() == 1;
    }


    private long downLoadLength;


    private String type;

    private String dir = FileUtil.getInstance().getDownLoadPath();

    private long time = System.currentTimeMillis();

    @Generated(hash = 733448638)
    public FileBean(Long id, String url, String name, long contentLength,
            long downLoadLength, String type, String dir, long time) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.contentLength = contentLength;
        this.downLoadLength = downLoadLength;
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

    public String getSuffix() {
        return name.substring(name.lastIndexOf("."),name.length());
    }

    public String getPrefix() {
        return name.substring(0,name.lastIndexOf("."));
    }

    public void appendName(String s) {
        name = getPrefix() + s + getSuffix();
    }

    public void deleteFile() {
        File file = new File(dir + "/" + name);
        if (file.exists()) {
            file.delete();
        }
    }

}
