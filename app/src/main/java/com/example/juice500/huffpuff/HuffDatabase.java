package com.example.juice500.huffpuff;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by juice500 on 16. 1. 1.
 * Accesses Database
 * Singleton Pattern
 */

public class HuffDatabase extends SQLiteOpenHelper{
    private static HuffDatabase huffDatabase;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "huff.db";
    private static final String IMAGE_TABLE_NAME = "image_info";
    private static final String DIRECTORY_TABLE_NAME = "directory_info";
    private static final String IMAGE_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + IMAGE_TABLE_NAME
            + "(imageName varchar(255) NOT NULL, "
            + "imagePath varchar text NOT NULL, "
            + "folderName varchar(255) NOT NULL, "
            + "isImageHuff boolean NOT NULL);";
    private static final String DIRECTORY_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + DIRECTORY_TABLE_NAME
            + "(folderName varchar(255) NOT NULL, "
            + "imagePath varchar text NOT NULL, "
            + "huffedNumber integer NOT NULL);";
    private SQLiteDatabase db;

    private HuffDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.db = getWritableDatabase();
    }

    public static synchronized HuffDatabase getInstance (Context context) {
        if (huffDatabase == null) huffDatabase = new HuffDatabase(context);
        return huffDatabase;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            db.execSQL(IMAGE_TABLE_CREATE);
            db.execSQL(DIRECTORY_TABLE_CREATE);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public ArrayList<ImageItem> getFolderList() {
        ArrayList<ImageItem> imageItems = new ArrayList<>();
        Cursor cursor = this.db.rawQuery("SELECT * FROM " + DIRECTORY_TABLE_NAME, null);
        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            do {
                String folderName = cursor.getString(cursor.getColumnIndex("folderName"));
                String imagePath = cursor.getString(cursor.getColumnIndex("imagePath"));
                int huffedNumber = cursor.getInt(cursor.getColumnIndex("huffedNumber"));
                imageItems.add(new ImageItem(folderName, imagePath, huffedNumber));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return imageItems;
    }

    public ArrayList<ImageItem> getImageList(String folderName) {
        ArrayList<ImageItem> imageItems = new ArrayList<>();
        Cursor cursor = this.db.rawQuery("SELECT * FROM " + IMAGE_TABLE_NAME + " WHERE imageFolderName='" + folderName+"'", null);
        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            do {
                String imageName = cursor.getString(cursor.getColumnIndex("imageName"));
                String imagePath = cursor.getString(cursor.getColumnIndex("imagePath"));
                int isImageHuff = cursor.getInt(cursor.getColumnIndex("isImageHuff"));
                imageItems.add(new ImageItem(imageName, imagePath, isImageHuff));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return imageItems;
    }

    public boolean isImageInList(String imagePath) {
        Cursor cursor = this.db.rawQuery("SELECT * FROM " + IMAGE_TABLE_NAME + " WHERE imagePath='" + imagePath+"';", null);
        if(cursor.getCount()>1) Log.d(this.toString(), "Multiple image in "+IMAGE_TABLE_NAME);

        boolean ret = cursor.getCount() != 0;
        cursor.close();
        return ret;
    }

    public boolean isFolderInList(String folderName) {
        Cursor cursor = this.db.rawQuery("SELECT * FROM " + DIRECTORY_TABLE_NAME + " WHERE folderName='" + folderName+"'", null);
        if(cursor.getCount()>1) Log.d(this.toString(), "Multiple folder in "+DIRECTORY_TABLE_NAME);

        boolean ret = cursor.getCount() != 0;
        cursor.close();
        return ret;    }

    public void insertImage(String imageName, String imagePath, String folderName) {
        if(!isImageInList(imagePath)) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("imageName", imageName);
            contentValues.put("imagePath", imagePath);
            contentValues.put("folderName", folderName);
            contentValues.put("isImageHuff", false);
            this.db.insert(IMAGE_TABLE_NAME, null, contentValues);

            if (!isFolderInList(folderName)) {
                contentValues = new ContentValues();
                contentValues.put("folderName", folderName);
                contentValues.put("imagePath", imagePath);
                contentValues.put("huffedNumber", 0);
                this.db.insert(DIRECTORY_TABLE_NAME, null, contentValues);
            }
        }
    }

    public void deleteImage(String deletedImagePath) {
        Cursor cursor = this.db.rawQuery("SELECT * FROM " + IMAGE_TABLE_NAME + " WHERE imagePath='" + deletedImagePath+"'", null);
        if(cursor.getCount() == 0) {
            cursor.close();
            return;
        }
        if(cursor.getCount()>1) Log.d(this.toString(), "Multiple image in "+IMAGE_TABLE_NAME);
        cursor.moveToFirst();
        String folderName = cursor.getString(cursor.getColumnIndex("folderName"));
        boolean isImageHuff  = (cursor.getInt(cursor.getColumnIndex("isImageHuff")) == 1);
        this.db.delete(IMAGE_TABLE_NAME, "imagePath=?", new String[]{deletedImagePath});
        cursor.close();

        cursor = this.db.rawQuery("SELECT * FROM " + DIRECTORY_TABLE_NAME + " WHERE folderName='" + folderName+"'", null);
        if(cursor.getCount()>1) Log.d(this.toString(), "Multiple folder in "+DIRECTORY_TABLE_NAME);
        cursor.moveToFirst();
        String imagePath = cursor.getString(cursor.getColumnIndex("imagePath"));
        int huffedNumber = cursor.getInt(cursor.getColumnIndex("huffedNumber"));
        this.db.delete(DIRECTORY_TABLE_NAME, "folderName=?", new String[]{folderName});
        cursor.close();

        // huffedNumber Update
        if(isImageHuff) huffedNumber--;
        // imagePath Update
        if(imagePath.equals(deletedImagePath)) {
            cursor = this.db.rawQuery("SELECT * FROM " + IMAGE_TABLE_NAME + " WHERE folderName='" + folderName+"'", null);
            if(cursor.getCount() == 0) {
                cursor.close();
                return;
            }
            cursor.moveToFirst();
            imagePath = cursor.getString(cursor.getColumnIndex("imagePath"));
        }
        cursor.close();

        ContentValues contentValues = new ContentValues();
        contentValues.put("folderName", folderName);
        contentValues.put("imagePath", imagePath);
        contentValues.put("huffedNumber", huffedNumber);
        this.db.insert(DIRECTORY_TABLE_NAME, null, contentValues);
    }

}