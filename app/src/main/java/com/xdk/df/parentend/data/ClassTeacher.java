package com.xdk.df.parentend.data;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/22.
 */
public class ClassTeacher implements Serializable,Comparable{
    private String schoolCode,course,telephone,teachertitle,teacher,teacherid,professional,school;
    private int grade,sClass;

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTeachertitle() {
        return teachertitle;
    }

    public void setTeachertitle(String teachertitle) {
        this.teachertitle = teachertitle;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getTeacherid() {
        return teacherid;
    }

    public void setTeacherid(String teacherid) {
        this.teacherid = teacherid;
    }

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getsClass() {
        return sClass;
    }

    public void setsClass(int sClass) {
        this.sClass = sClass;
    }

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof ClassTeacher) || o == null) {
            return 1;
        }
        if(this.course.equals("班主任")){
            return -1;
        }else if(((ClassTeacher) o).course.equals("班主任")){
            return 1;
        }
        return 0;
    }
}
