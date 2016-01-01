package com.example.juice500.huffpuff;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by juice500 on 16. 1. 1.
 * Adapter for List View in MainActivity first fragment
 */

public class ListViewAdapter extends ArrayAdapter {
    private Context context;
    private LayoutInflater inflater;

    private String[] imageUrls;

    public ListViewAdapter(Context context, String[] imageUrls) {
        //super(context, R.layout.listview_item_image, imageUrls);
        super(context,0,imageUrls);

        this.context = context;
        this.imageUrls = imageUrls;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
        //    convertView = inflater.inflate(R.layout.listview_item_image, parent, false);
        }

        Glide
                .with(context)
                .load(imageUrls[position])
                .into((ImageView) convertView);

        return convertView;
    }
}