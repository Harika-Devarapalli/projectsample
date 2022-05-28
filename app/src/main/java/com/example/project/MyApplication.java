package com.example.project;

import android.app.Application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyApplication extends Application {

    static List<CartData> cartData = new ArrayList<>();
    static String hotelName = "";
    static HashMap<String,String>hotels = new HashMap<>();

    public static void addHotelData(){
        hotels.put("BB","BrownieHeaven");
        hotels.put("SS","ShakeAndShake");
        hotels.put("TT","TiffenAndLunch");
    }

    public static void clearData(){
        cartData.clear();
        hotelName = "";
    }

}
