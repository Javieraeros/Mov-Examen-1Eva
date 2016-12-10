package es.iesnervion.fjruiz.mov_examen_1eva.controller;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.Vector;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private Vector<Integer> imagenesMostrar;

    public ImageAdapter(Context c,Vector<Integer> vector) {
        mContext = c;
        imagenesMostrar=vector;

    }

    public int getCount() {
        return imagenesMostrar.size();
    }

    public Integer getItem(int position) {
        return imagenesMostrar.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(1, 1, 1, 1);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(imagenesMostrar.get(position));
        return imageView;
    }
}