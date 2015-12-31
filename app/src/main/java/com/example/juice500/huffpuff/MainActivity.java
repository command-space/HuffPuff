package com.example.juice500.huffpuff;

import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private GridView gridView;
    private GridViewAdapter gridViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.gridView);
        gridViewAdapter = new GridViewAdapter(this, R.layout.grid_item, getImageList());
        gridView.setAdapter(gridViewAdapter);
    }

    // Returns every image that can be accessed via Android Default Gallery
    private ArrayList<ImageItem> getImageList() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        Uri uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        Cursor cursor = MainActivity.this.getContentResolver().query(uri, projection, null, null, null);

        int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        int column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            String imagePath = cursor.getString(column_index_data);
            String imageFolderName = cursor.getString(column_index_folder_name);

            if(imageItems.size()<2) {
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                imageItems.add(new ImageItem(bitmap, imagePath, imageFolderName));
            }

            Log.d("ASDF",imagePath);

        }
        return imageItems;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
