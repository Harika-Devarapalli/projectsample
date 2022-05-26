package com.example.project;

import android.os.Parcel;
import android.os.Parcelable;

public class UserDetails implements Parcelable {
    private String name,phone,regId,gender;

    public UserDetails() {
    }

    public UserDetails(String name, String phone, String regId, String gender) {
        this.name = name;
        this.phone = phone;
        this.regId = regId;
        this.gender = gender;
    }

    protected UserDetails(Parcel in) {
        name = in.readString();
        phone = in.readString();
        regId = in.readString();
        gender = in.readString();
    }

    public static final Creator<UserDetails> CREATOR = new Creator<UserDetails>() {
        @Override
        public UserDetails createFromParcel(Parcel in) {
            return new UserDetails(in);
        }

        @Override
        public UserDetails[] newArray(int size) {
            return new UserDetails[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(phone);
        parcel.writeString(regId);
        parcel.writeString(gender);
    }
}
