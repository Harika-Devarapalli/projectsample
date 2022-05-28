package com.example.project;

public class CartData {

    String itemName,HotelName;
    int price;

    public CartData() {
    }

    public CartData(String itemName, String hotelName, int price) {
        this.itemName = itemName;
        HotelName = hotelName;
        this.price = price;
    }

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
}
