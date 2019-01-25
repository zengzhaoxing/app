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
        if (mContentLength == 0) {
            return 0;
        }
        return (float)mDownLoadLength / (float)mContentLength;
    }

    public long getContentLength() {
        return mContentLength;
    }

    public void setContentLength(long contentLength) {
        mContentLength = contentLength;
    }

    private long mContentLength;

    public long getDownLoadLength() {
        return mDownLoadLength;
    }

    public void setDownLoadLength(long downLoadLength) {
        mDownLoadLength = downLoadLength;
    }

    public boolean isComplete() {
        return getProgress() == 1;
    }


    private long mDownLoadLength;

    private String type;

    private String dir = FileUtil.getInstance().getDownLoadPath();

    private long time = System.currentTimeMillis();

    @Generated(hash = 252725018)
    public FileBean(Long id, String url, String name, long mContentLength,
            long mDownLoadLength, String type, String dir, long time) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.mContentLength = mContentLength;
        this.mDownLoadLength = mDownLoadLength;
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

    public long getMContentLength() {
        return this.mContentLength;
    }

    public void setMContentLength(long mContentLength) {
        this.mContentLength = mContentLength;
    }

    public long getMDownLoadLength() {
        return this.mDownLoadLength;
    }

    public void setMDownLoadLength(long mDownLoadLength) {
        this.mDownLoadLength = mDownLoadLength;
    }

    public void deleteFile() {
        File file = new File(dir + "/" + name);
        if (file.exists()) {
            file.delete();
        }
    }

}
