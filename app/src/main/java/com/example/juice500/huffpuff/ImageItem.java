package com.example.juice500.huffpuff;

import android.os.Parcel;
import android.os.Parcelable;

public class ImageItem implements Parcelable {
    private String name;
    private String path;
    private int isHuff;

    ImageItem(String name, String path, int isHuff) {
        this.name = name;
        this.path = path;
        this.isHuff = isHuff;
    }

    ImageItem(Parcel parcel) {
        this.name = parcel.readString();
        this.path = parcel.readString();
        this.isHuff = parcel.readInt();
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(name);
        parcel.writeString(path);
        parcel.writeInt(isHuff);
    }

    public static final Parcelable.Creator<ImageItem> CREATOR = new Parcelable.Creator<ImageItem>() {
        public ImageItem createFromParcel(Parcel parcel) {
            return new ImageItem(parcel);
        }
        public ImageItem[] newArray(int size) {
            return new ImageItem[size];
        }
    };

    public int describeContents() {
        return 0;
    }

    String getName() {
        return this.name;
    }

    String getPath() {
        return this.path;
    }

    int getIsHuff() {
        return this.isHuff;
    }

    void setName(String name) {
        this.name = name;
    }

    void setPath(String path) {
        this.path = path;
    }

    void setIsHuff(int isHuff) { this.isHuff = isHuff; }

}