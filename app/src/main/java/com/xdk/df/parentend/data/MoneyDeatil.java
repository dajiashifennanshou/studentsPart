package com.xdk.df.parentend.data;

import com.xdk.df.parentend.http.HttpHelper;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/12.
 */
public class MoneyDeatil implements Serializable, Comparable {
    private int autoid;
    private String accountid;
    private String schoolcode;
    private String moneytype;
    private double money;
    private double oldmoney;
    private double newmoney;
    private String happendate;
    private String happentime;
    private String happenaddress;
    private String moneystyle;
    private String happenwindow;

    public String getHappenwindow() {
        return happenwindow;
    }

    public void setHappenwindow(String happenwindow) {
        this.happenwindow = happenwindow;
    }

    public String getMoneystyle() {
        return moneystyle;
    }

    public void setMoneystyle(String moneystyle) {
        this.moneystyle = moneystyle;
    }

    public String getSchoolcode() {
        return schoolcode;
    }

    public void setSchoolcode(String schoolcode) {
        this.schoolcode = schoolcode;
    }

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

    public String getMoneytype() {
        return moneytype;
    }

    public void setMoneytype(String moneytype) {
        this.moneytype = moneytype;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getOldmoney() {
        return oldmoney;
    }

    public void setOldmoney(double oldmoney) {
        this.oldmoney = oldmoney;
    }

    public double getNewmoney() {
        return newmoney;
    }

    public void setNewmoney(double newmoney) {
        this.newmoney = newmoney;
    }

    public String getHappendate() {
        return happendate;
    }

    public void setHappendate(String happendate) {
        this.happendate = happendate;
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

    @Override
    public int compareTo(Object o) {

        if (!(o instanceof MoneyDeatil) || o == null) {
            return 1;
        }
        int flag = HttpHelper.compare_date(this.happendate+this.happentime, ((MoneyDeatil) o).happendate+((MoneyDeatil) o).happentime);
        if (flag == 1) {
            return -1;
        } else if (flag == -1) {
            return 1;
        }
        return 0;
    }
}
