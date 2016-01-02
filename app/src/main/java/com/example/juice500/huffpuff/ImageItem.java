package com.example.juice500.huffpuff;

public class ImageItem {
    private String name;
    private String path;
    private int isHuff;

    ImageItem(String name, String path, int isHuff) {
        this.name = name;
        this.path = path;
        this.isHuff = isHuff;
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