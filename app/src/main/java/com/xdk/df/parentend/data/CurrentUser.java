package com.xdk.df.parentend.data;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/8.
 */
public class CurrentUser implements Serializable {
    private int autoid;
    private String schoolcode;
    private String accountid;
    private String userpwd;
    private String validdate;
    private String school;
    private String professional;
    private String room;
    private int grade;
    private int sclass;
    private String name;
    private String studentid;
    private String apertment;
    private double cardmoney;
    private String cardstate;
    private String telephone1;
    private String telephone2;
    private String telephone3;
    private String tea_title;
    private String tea_course;
    private String tea_manager;
    private String tea_indate;
    private int loginnumber;
    private String logintime;
    private String userstate;
    private String userstatus;

    public String getUserstatus() {
        return userstatus;
    }

    public void setUserstatus(String userstatus) {
        this.userstatus = userstatus;
    }

    public String getUserstate() {
        return userstate;
    }

    public void setUserstate(String userstate) {
        this.userstate = userstate;
    }

    public int getLoginnumber() {
        return loginnumber;
    }

    public void setLoginnumber(int loginnumber) {
        this.loginnumber = loginnumber;
    }

    public String getLogintime() {
        return logintime;
    }

    public void setLogintime(String logintime) {
        this.logintime = logintime;
    }

    public int getAutoid() {
        return autoid;
    }

    public void setAutoid(int autoid) {
        this.autoid = autoid;
    }

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

    public String getUserpwd() {
        return userpwd;
    }

    public void setUserpwd(String userpwd) {
        this.userpwd = userpwd;
    }

    public String getValiddate() {
        return validdate;
    }

    public void setValiddate(String validdate) {
        this.validdate = validdate;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getSclass() {
        return sclass;
    }

    public void setSclass(int sclass) {
        this.sclass = sclass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public String getApertment() {
        return apertment;
    }

    public void setApertment(String apertment) {
        this.apertment = apertment;
    }

    public double getCardmoney() {
        return cardmoney;
    }

    public void setCardmoney(double cardmoney) {
        this.cardmoney = cardmoney;
    }

    public String getCardstate() {
        return cardstate;
    }

    public void setCardstate(String cardstate) {
        this.cardstate = cardstate;
    }

    public String getTelephone1() {
        return telephone1;
    }

    public void setTelephone1(String telephone1) {
        this.telephone1 = telephone1;
    }

    public String getTelephone2() {
        return telephone2;
    }

    public void setTelephone2(String telephone2) {
        this.telephone2 = telephone2;
    }

    public String getTelephone3() {
        return telephone3;
    }

    public void setTelephone3(String telephone3) {
        this.telephone3 = telephone3;
    }

    public String getTea_title() {
        return tea_title;
    }

    public void setTea_title(String tea_title) {
        this.tea_title = tea_title;
    }

    public String getTea_course() {
        return tea_course;
    }

    public void setTea_course(String tea_course) {
        this.tea_course = tea_course;
    }

    public String getTea_manager() {
        return tea_manager;
    }

    public void setTea_manager(String tea_manager) {
        this.tea_manager = tea_manager;
    }

    public String getTea_indate() {
        return tea_indate;
    }

    public void setTea_indate(String tea_indate) {
        this.tea_indate = tea_indate;
    }
}
