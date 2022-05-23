package com.example.project;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class RestaurantModel implements Parcelable {
    private String name;
    private String address;
    private String image;
    private float delivery_charge;
    private Hours hours;
    private List<Menu> menu;
    private String rating;


    protected RestaurantModel(Parcel in) {
        name = in.readString();
        address = in.readString();
        image = in.readString();
        delivery_charge = in.readFloat();
        menu = in.createTypedArrayList(Menu.CREATOR);
        rating = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(image);
        dest.writeFloat(delivery_charge);
        dest.writeTypedList(menu);
        dest.writeString(rating);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RestaurantModel> CREATOR = new Creator<RestaurantModel>() {
        @Override
        public RestaurantModel createFromParcel(Parcel in) {
            return new RestaurantModel(in);
        }

        @Override
        public RestaurantModel[] newArray(int size) {
            return new RestaurantModel[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getDelivery_charge() {
        return delivery_charge;
    }

    public void setDelivery_charge(float delivery_charge) {
        this.delivery_charge = delivery_charge;
    }

    public Hours getHours() {
        return hours;
    }

    public void setHours(Hours hours) {
        this.hours = hours;
    }

    public List<Menu> getMenu() {
        return menu;
    }

    public void setMenu(List<Menu> menu) {
        this.menu = menu;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public RestaurantModel() {
    }

    public RestaurantModel(String name, String address, String image, float delivery_charge, Hours hours, List<Menu> menu, String rating) {
        this.name = name;
        this.address = address;
        this.image = image;
        this.delivery_charge = delivery_charge;
        this.hours = hours;
        this.menu = menu;
        this.rating = rating;
    }
}
