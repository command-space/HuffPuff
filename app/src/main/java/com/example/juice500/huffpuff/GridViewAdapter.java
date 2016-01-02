package com.example.juice500.huffpuff;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Modified from http://javatechig.com/android/android-gridview-example-building-image-gallery-in-android
 */

public class GridViewAdapter<T> extends ArrayAdapter<T> {
    private Context context;
    private int layoutResourceId;
    private ArrayList<T> data;
    private LayoutInflater inflater;

    public GridViewAdapter(Context context, int layoutResourceId, ArrayList<T> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            row = this.inflater.inflate(this.layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageTitle = (TextView) row.findViewById(R.id.text);
            holder.image = (ImageView) row.findViewById(R.id.image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        T item = data.get(position);

        if(item.getClass() == ImageItem.class) {
            ImageItem imageItem = (ImageItem) item;
            holder.imageTitle.setText(imageItem.getName());
            Glide.with(this.context).load(imageItem.getPath()).into(holder.image);
        }

        return row;
    }

    static class ViewHolder {
        TextView imageTitle;
        ImageView image;
    }
}