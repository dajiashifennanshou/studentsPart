package com.xdk.df.parentend.data;

import com.xdk.df.parentend.http.HttpHelper;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/18.
 */
public class LeaveDetail implements Serializable,Comparable{
    private String schoolcode,accountid,startdate,starttime,enddate,endtime,leavetype,leaevreason,approved,manager,changedate;
    private int results,changestatus;

    public String getSchoolcode() {
        return schoolcode;
    }

    public void setSchoolcode(String schoolcode) {
        this.schoolcode = schoolcode;
    }

    public String getAccountid() {
        return accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getLeavetype() {
        return leavetype;
    }

    public void setLeavetype(String leavetype) {
        this.leavetype = leavetype;
    }

    public String getLeaevreason() {
        return leaevreason;
    }

    public void setLeaevreason(String leaevreason) {
        this.leaevreason = leaevreason;
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getChangedate() {
        return changedate;
    }

    public void setChangedate(String changedate) {
        this.changedate = changedate;
    }

    public int getResults() {
        return results;
    }

    public void setResults(int results) {
        this.results = results;
    }

    public int getChangestatus() {
        return changestatus;
    }

    public void setChangestatus(int changestatus) {
        this.changestatus = changestatus;
    }

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof LeaveDetail) || o == null) {
            return 1;
        }
        int flag = HttpHelper.compare_date(this.startdate+this.starttime, ((LeaveDetail) o).startdate+((LeaveDetail) o).starttime);
        if (flag == 1) {
            return -1;
        } else if (flag == -1) {
            return 1;
        }
        return 0;
    }
}
