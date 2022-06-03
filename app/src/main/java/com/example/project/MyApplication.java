package com.example.project;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyApplication extends Application {

    static List<CartData> cartData = new ArrayList<>();
    static String hotelName = "";
    static HashMap<String,String>hotels = new HashMap<>();

    static String MenuLastAddedName = "";
    public static void addHotelData(){
        hotels.put("BB","BrownieHeaven");
        hotels.put("SS","ShakeAndShake");
        hotels.put("TT","TiffenAndLunch");
    }

    public static void clearData(Context context){
        cartData.clear();
        hotelName = "";
    }

}
