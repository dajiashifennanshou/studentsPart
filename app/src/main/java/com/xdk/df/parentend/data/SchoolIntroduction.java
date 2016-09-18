package com.xdk.df.parentend.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/9/6.
 */
public class SchoolIntroduction implements Parcelable {
    private int autoid;
    private String schoolcode;
    private String note;

    public SchoolIntroduction() {

    }

    protected SchoolIntroduction(Parcel in) {
        autoid = in.readInt();
        schoolcode = in.readString();
        note = in.readString();
    }

    public static final Creator<SchoolIntroduction> CREATOR = new Creator<SchoolIntroduction>() {
        @Override
        public SchoolIntroduction createFromParcel(Parcel in) {
            return new SchoolIntroduction(in);
        }

        @Override
        public SchoolIntroduction[] newArray(int size) {
            return new SchoolIntroduction[size];
        }
    };

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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(autoid);
        parcel.writeString(schoolcode);
        parcel.writeString(note);
    }
}
