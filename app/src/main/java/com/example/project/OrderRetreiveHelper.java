package com.example.project;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderRetreiveHelper implements Parcelable {
    private String id;
    private String name;
    private String phone;
    private String address;
    private String hotelName;
    private long date_time;

    private List<CartData> cartData = new ArrayList<>();

    public OrderRetreiveHelper(String id, String name, String phone, String address, String hotelName, long date_time, List<CartData> cartData) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.hotelName = hotelName;
        this.date_time = date_time;
        this.cartData = cartData;
    }

    public OrderRetreiveHelper() {
    }


    protected OrderRetreiveHelper(Parcel in) {
        id = in.readString();
        name = in.readString();
        phone = in.readString();
        address = in.readString();
        hotelName = in.readString();
        date_time = in.readLong();
    }

    public static final Creator<OrderRetreiveHelper> CREATOR = new Creator<OrderRetreiveHelper>() {
        @Override
        public OrderRetreiveHelper createFromParcel(Parcel in) {
            return new OrderRetreiveHelper(in);
        }

        @Override
        public OrderRetreiveHelper[] newArray(int size) {
            return new OrderRetreiveHelper[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public long getDate_time() {
        return date_time;
    }

    public void setDate_time(long date_time) {
        this.date_time = date_time;
    }

    public List<CartData> getCartData() {
        return cartData;
    }

    public void setCartData(List<CartData> cartData) {
        this.cartData = cartData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(phone);
        parcel.writeString(address);
        parcel.writeString(hotelName);
        parcel.writeLong(date_time);
    }
}
