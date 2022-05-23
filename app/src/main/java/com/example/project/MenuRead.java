package com.example.project;

import android.os.Parcel;
import android.os.Parcelable;

public class MenuRead implements Parcelable{


    private String item_name,item_price,item_id;


    public MenuRead(String item_name, String item_price, String item_id) {
        this.item_name = item_name;
        this.item_price = item_price;
        this.item_id = item_id;
    }

    public MenuRead() {
    }


    protected MenuRead(Parcel in) {
        item_name = in.readString();
        item_price = in.readString();
        item_id = in.readString();
    }

    public static final Creator<MenuRead> CREATOR = new Creator<MenuRead>() {
        @Override
        public MenuRead createFromParcel(Parcel in) {
            return new MenuRead(in);
        }

        @Override
        public MenuRead[] newArray(int size) {
            return new MenuRead[size];
        }
    };

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_price() {
        return item_price;
    }

    public void setItem_price(String item_price) {
        this.item_price = item_price;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(item_name);
        parcel.writeString(item_price);
        parcel.writeString(item_id);
    }
}
