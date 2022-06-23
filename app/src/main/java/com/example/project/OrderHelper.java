package com.example.project;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.ServerValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderHelper implements Parcelable {



    private String order_key;
    private String id;
    private String userId;
    private String name;
    private String phone;
    private String address;
    private String hotelName;
    private Map<String, String> date_time;
    private String orderStatus;
    private List<CartData> cartData = new ArrayList<>();

    public OrderHelper() {
    }

    public OrderHelper(String order_key, String id, String userId, String name, String phone, String address, String hotelName, String orderStatus, List<CartData> cartData) {
        this.order_key = order_key;
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.hotelName = hotelName;
        this.orderStatus = orderStatus;
        this.cartData = cartData;
        this.date_time = ServerValue.TIMESTAMP;
    }

    protected OrderHelper(Parcel in) {
        order_key = in.readString();
        id = in.readString();
        userId = in.readString();
        name = in.readString();
        phone = in.readString();
        address = in.readString();
        hotelName = in.readString();
        orderStatus = in.readString();
        cartData = in.createTypedArrayList(CartData.CREATOR);
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

    public String getOrder_key() {
        return order_key;
    }

    public void setOrder_key(String order_key) {
        this.order_key = order_key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
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
        parcel.writeString(order_key);
        parcel.writeString(id);
        parcel.writeString(userId);
        parcel.writeString(name);
        parcel.writeString(phone);
        parcel.writeString(address);
        parcel.writeString(hotelName);
        parcel.writeString(orderStatus);
        parcel.writeTypedList(cartData);
    }
}
