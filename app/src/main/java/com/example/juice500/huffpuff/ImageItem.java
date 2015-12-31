package com.example.juice500.huffpuff;

import android.graphics.Bitmap;

/**
 * http://javatechig.com/android/android-gridview-example-building-image-gallery-in-android
 */
public class ImageItem {
    private Bitmap image;
    private String title;
    private String folderTitle;

    public ImageItem(Bitmap image, String title, String folderTitle) {
        super();
        this.image = image;
        this.title = title;
        this.folderTitle = folderTitle;
    }

    public Bitmap getImage() {
        return this.image;
    }
    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getFolderTitle() {
        return this.folderTitle;
    }
    public void setFolderTitle(String folderTitle) {
        this.folderTitle = folderTitle;
    }
}