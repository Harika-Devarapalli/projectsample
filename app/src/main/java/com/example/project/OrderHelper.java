package com.example.project;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.ServerValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderHelper implements Parcelable {
    private String id;
    private String name;
    private String phone;
    private String address;
    private String hotelName;
    private Map<String, String> date_time;

    private List<CartData> cartData = new ArrayList<>();

    public OrderHelper(String id, String name, String phone, String address, String hotelName, List<CartData> cartData) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.hotelName = hotelName;
        this.date_time = ServerValue.TIMESTAMP;
        this.cartData = cartData;
    }

    public OrderHelper() {
    }

    protected OrderHelper(Parcel in) {
        id = in.readString();
        name = in.readString();
        phone = in.readString();
        address = in.readString();
        hotelName = in.readString();
    }

    public static final Creator<OrderHelper> CREATOR = new Creator<OrderHelper>() {
        @Override
        public OrderHelper createFromParcel(Parcel in) {
            return new OrderHelper(in);
        }

        @Override
        public OrderHelper[] newArray(int size) {
            return new OrderHelper[size];
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

    public Map<String, String> getDate_time() {
        return date_time;
    }

    public void setDate_time(Map<String, String> date_time) {
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
    }
}
