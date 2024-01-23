package com.ariyanjakaria.spacetour;

public class ImageInfo {
    private String imagePath;

    public ImageInfo() {
        // Default constructor needed for Firestore
    }

    public ImageInfo(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }
}
