package com.xdk.df.parentend.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/9/6.
 */
public class SchoolPart implements Parcelable{
    private int autoid;
    private String schoolcode;
    private String module;
    private String status;

    protected SchoolPart(Parcel in) {
        autoid = in.readInt();
        schoolcode = in.readString();
        module = in.readString();
        status = in.readString();
    }

    public static final Creator<SchoolPart> CREATOR = new Creator<SchoolPart>() {
        @Override
        public SchoolPart createFromParcel(Parcel in) {
            return new SchoolPart(in);
        }

        @Override
        public SchoolPart[] newArray(int size) {
            return new SchoolPart[size];
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

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(autoid);
        parcel.writeString(schoolcode);
        parcel.writeString(module);
        parcel.writeString(status);
    }
}
