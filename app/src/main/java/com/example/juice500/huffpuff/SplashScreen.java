package com.example.juice500.huffpuff;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

public class SplashScreen extends AppCompatActivity {
    private final static int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                HuffDatabase huffDatabase = HuffDatabase.getInstance(SplashScreen.this);

                // Update database with new images
                Uri uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                String[] projection = {MediaStore.Images.Media.TITLE, MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
                Cursor cursor = SplashScreen.this.getContentResolver().query(uri, projection, "", null, null);

                int column_imageName = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.TITLE);
                int column_imagePath = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                int column_folderName = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
                while (cursor.moveToNext()) {
                    String imageName = cursor.getString(column_imageName);
                    String imagePath = cursor.getString(column_imagePath);
                    String folderName = cursor.getString(column_folderName);
                    huffDatabase.insertImage(imageName, imagePath, folderName);
                    /*
                    int THUMBSIZE = 64;
                    Glide.with(MainActivity.this).load(imagePath).asBitmap().into(new SimpleTarget<Bitmap>(THUMBSIZE, THUMBSIZE){
                        @Override
                        public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                            Log.d("ASDF", "DONE");
                        }
                    });
                    */
                    // Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(imagePath), THUMBSIZE, THUMBSIZE);
                    // Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                    // imageItems.add(new ImageItem(bitmap, imagePath, imageFolderName));
                }
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                SplashScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
