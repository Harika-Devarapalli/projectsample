package com.example.project;

public class ImageModel {
    String imageUrl;

    public ImageModel(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ImageModel() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
