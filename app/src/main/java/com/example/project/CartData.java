package com.example.project;

import android.os.Parcel;
import android.os.Parcelable;

public class CartData implements Parcelable {

    String itemName,HotelName;
    int price;

    public CartData() {
    }

    public CartData(String itemName, String hotelName, int price) {
        this.itemName = itemName;
        HotelName = hotelName;
        this.price = price;
    }

    protected CartData(Parcel in) {
        itemName = in.readString();
        HotelName = in.readString();
        price = in.readInt();
    }

    public static final Creator<CartData> CREATOR = new Creator<CartData>() {
        @Override
        public CartData createFromParcel(Parcel in) {
            return new CartData(in);
        }

        @Override
        public CartData[] newArray(int size) {
            return new CartData[size];
        }
    };

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getHotelName() {
        return HotelName;
    }

    public void setHotelName(String hotelName) {
        HotelName = hotelName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(itemName);
        parcel.writeString(HotelName);
        parcel.writeInt(price);
    }
}
