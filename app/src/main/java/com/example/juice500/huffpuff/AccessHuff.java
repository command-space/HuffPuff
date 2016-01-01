package com.example.juice500.huffpuff;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by juice500 on 16. 1. 1.
 * Accesses Database
 */
public class AccessHuff {
    public static final String DATABASE_NAME = "huff.db";
    private static final String IMAGE_TABLE_NAME = "image_info";
    private static final String DIRECTORY_TABLE_NAME = "directory_info";
    private static final String IMAGE_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + IMAGE_TABLE_NAME
            + "(imageName varchar(255) NOT NULL, "
            + "imagePath varchar text NOT NULL, "
            + "imageFolderName varchar(255) NOT NULL, "
            + "isImageHuff boolean NOT NULL);";
    private static final String DIRECTORY_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + DIRECTORY_TABLE_NAME
            + "(folderName varchar(255) NOT NULL, "
            + "imagePath varchar text NOT NULL, "
            + "isImageHuff boolean NOT NULL);";
    private SQLiteDatabase db;

    AccessHuff (SQLiteDatabase db) {
        this.db = db;
        this.db.execSQL(IMAGE_TABLE_CREATE);
        this.db.execSQL(DIRECTORY_TABLE_CREATE);
        // EXAMPLES!
        //this.db.execSQL("insert into directory_info values('BTS', '/storage/BTS/image005.jpg', 'FALSE');");
        //this.db.execSQL("insert into directory_info values('TTS', '/storage/TTS/image005.jpg', 'FALSE');");
    }

    public ArrayList<ImageItem> getFolderList() {
        ArrayList<ImageItem> imageItems = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DIRECTORY_TABLE_NAME, null);
        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            do {
                String folderName = cursor.getString(cursor.getColumnIndex("folderName"));
                String imagePath = cursor.getString(cursor.getColumnIndex("imagePath"));
                boolean isImageHuff = cursor.getInt(cursor.getColumnIndex("isImageHuff"))>0;
                imageItems.add(new ImageItem(folderName, imagePath, isImageHuff));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return imageItems;
    }
}