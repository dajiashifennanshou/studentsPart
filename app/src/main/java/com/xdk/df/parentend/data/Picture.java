package com.xdk.df.parentend.data;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/12.
 */
public class Picture implements Serializable{
    private int autoid;
    private String picid;
    private String picfile;
    private String picture;

    public int getAutoid() {
        return autoid;
    }

    public void setAutoid(int autoid) {
        this.autoid = autoid;
    }

    public String getPicid() {
        return picid;
    }

    public void setPicid(String picid) {
        this.picid = picid;
    }

    public String getPicfile() {
        return picfile;
    }

    public void setPicfile(String picfile) {
        this.picfile = picfile;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
