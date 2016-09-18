package com.xdk.df.parentend.data;

import com.xdk.df.parentend.http.HttpHelper;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/12.
 */
public class ResultMark implements Serializable, Comparable {
    private int autoid;
    private String accoutid;
    private String examination;
    private String exadate;
    private double mark;
    private double average;
    private String rankchanged;
    private int classrank;
    private int graderank;

    public int getAutoid() {
        return autoid;
    }

    public void setAutoid(int autoid) {
        this.autoid = autoid;
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

    public String getExadate() {
        return exadate;
    }

    public void setExadate(String exadate) {
        this.exadate = exadate;
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public String getRankchanged() {
        return rankchanged;
    }

    public void setRankchanged(String rankchanged) {
        this.rankchanged = rankchanged;
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

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof ResultMark) || o == null) {
            return 1;
        }
        int flag = HttpHelper.compare_date(this.exadate, ((ResultMark) o).exadate);
        if (flag == 1) {
            return -1;
        } else if (flag == -1) {
            return 1;
        }
        return 0;
    }
}
