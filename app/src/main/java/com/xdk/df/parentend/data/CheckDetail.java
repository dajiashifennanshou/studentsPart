package com.xdk.df.parentend.data;

import com.xdk.df.parentend.http.HttpHelper;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/12.
 */
public class CheckDetail implements Serializable,Comparable{
    private int autoid;
    private String accountid;
    private String happendate;
    private String checkaspect;
    private String happentime;
    private String happenaddress;
    private String checktype;

    public int getAutoid() {
        return autoid;
    }

    public void setAutoid(int autoid) {
        this.autoid = autoid;
    }

    public String getAccountid() {
        return accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    public String getHappendate() {
        return happendate;
    }

    public void setHappendate(String happendate) {
        this.happendate = happendate;
    }

    public String getCheckaspect() {
        return checkaspect;
    }

    public void setCheckaspect(String checkaspect) {
        this.checkaspect = checkaspect;
    }

    public String getHappentime() {
        return happentime;
    }

    public void setHappentime(String happentime) {
        this.happentime = happentime;
    }

    public String getHappenaddress() {
        return happenaddress;
    }

    public void setHappenaddress(String happenaddress) {
        this.happenaddress = happenaddress;
    }

    public String getChecktype() {
        return checktype;
    }

    public void setChecktype(String checktype) {
        this.checktype = checktype;
    }

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof CheckDetail) || o == null) {
            return 1;
        }
        int flag = HttpHelper.compare_date(this.happendate+this.happentime, ((CheckDetail) o).happendate+ ((CheckDetail) o).happentime);
        if (flag == 1) {
            return -1;
        } else if (flag == -1) {
            return 1;
        }
        return 0;
    }
}
