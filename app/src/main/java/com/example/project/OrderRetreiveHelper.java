package com.example.project;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderRetreiveHelper implements Parcelable {
    private String id;
    private String order_key;
    private String userId;
    private String name;
    private String phone;
    private String address;
    private String hotelName;
    private String orderStatus;
    private long date_time;
    private List<CartData> cartData = new ArrayList<>();


    public OrderRetreiveHelper() {
    }

    public OrderRetreiveHelper(String id, String order_key, String userId, String name, String phone, String address, String hotelName, String orderStatus, long date_time, List<CartData> cartData) {
        this.id = id;
        this.order_key = order_key;
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.hotelName = hotelName;
        this.orderStatus = orderStatus;
        this.date_time = date_time;
        this.cartData = cartData;
    }

    protected OrderRetreiveHelper(Parcel in) {
        id = in.readString();
        order_key = in.readString();
        userId = in.readString();
        name = in.readString();
        phone = in.readString();
        address = in.readString();
        hotelName = in.readString();
        orderStatus = in.readString();
        date_time = in.readLong();
        cartData = in.createTypedArrayList(CartData.CREATOR);
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

    public String getOrder_key() {
        return order_key;
    }

    public void setOrder_key(String order_key) {
        this.order_key = order_key;
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

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
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
        parcel.writeString(order_key);
        parcel.writeString(userId);
        parcel.writeString(name);
        parcel.writeString(phone);
        parcel.writeString(address);
        parcel.writeString(hotelName);
        parcel.writeString(orderStatus);
        parcel.writeLong(date_time);
        parcel.writeTypedList(cartData);
    }
}
