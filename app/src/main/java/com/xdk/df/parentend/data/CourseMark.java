package com.xdk.df.parentend.data;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/17.
 */
public class CourseMark implements Serializable{
    private int autoid;
    private String schoolcode;
    private String accoutid;
    private String examination;
    private String course;
    private double mark;
    private int classrank;
    private int graderank;

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

    public String getAccoutid() {
        return accoutid;
    }

    public void setAccoutid(String accoutid) {
        this.accoutid = accoutid;
    }

    public String getExamination() {
        return examination;
    }

    public void setExamination(String examination) {
        this.examination = examination;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public int getClassrank() {
        return classrank;
    }

    public void setClassrank(int classrank) {
        this.classrank = classrank;
    }

    public int getGraderank() {
        return graderank;
    }

    public void setGraderank(int graderank) {
        this.graderank = graderank;
    }
}
