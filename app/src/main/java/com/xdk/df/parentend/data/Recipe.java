package com.xdk.df.parentend.data;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by Administrator on 2016/9/6.
 */
public class Recipe implements Parcelable{
    private int autoid;
    private String schoolCode;
    private String meal;
    private String week1;
    private String week2;
    private String week3;
    private String week4;
    private String week5;
    private String week6;
    private String week7;
    public Recipe(){

    }
    protected Recipe(Parcel in) {
        autoid = in.readInt();
        schoolCode = in.readString();
        meal = in.readString();
        week1 = in.readString();
        week2 = in.readString();
        week3 = in.readString();
        week4 = in.readString();
        week5 = in.readString();
        week6 = in.readString();
        week7 = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public int getAutoid() {
        return autoid;
    }

    public void setAutoid(int autoid) {
        this.autoid = autoid;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public String getWeek1() {
        return week1;
    }

    public void setWeek1(String week1) {
        this.week1 = week1;
    }

    public String getWeek2() {
        return week2;
    }

    public void setWeek2(String week2) {
        this.week2 = week2;
    }

    public String getWeek3() {
        return week3;
    }

    public void setWeek3(String week3) {
        this.week3 = week3;
    }

    public String getWeek4() {
        return week4;
    }

    public void setWeek4(String week4) {
        this.week4 = week4;
    }

    public String getWeek5() {
        return week5;
    }

    public void setWeek5(String week5) {
        this.week5 = week5;
    }

    public String getWeek6() {
        return week6;
    }

    public void setWeek6(String week6) {
        this.week6 = week6;
    }

    public String getWeek7() {
        return week7;
    }

    public void setWeek7(String week7) {
        this.week7 = week7;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(autoid);
        parcel.writeString(schoolCode);
        parcel.writeString(meal);
        parcel.writeString(week1);
        parcel.writeString(week2);
        parcel.writeString(week3);
        parcel.writeString(week4);
        parcel.writeString(week5);
        parcel.writeString(week6);
        parcel.writeString(week7);
    }
}
